package com.spring.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {

    private static final String URL = "jdbc:mysql://192.168.10.197:3306/e_exam_admin_db?useUnicode=true&amp&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "Zhanggf_123";
    private static final String DRIVE = "com.mysql.jdbc.Driver";
    private static HikariDataSource ds = null;
    private static ConnectionManager databasePool = null;

    static {
        HikariConfig hc = new HikariConfig();
        hc.setJdbcUrl(URL);
        hc.setDriverClassName(DRIVE);
        hc.setUsername(USER);
        hc.setPassword(PASSWORD);
        ds = new HikariDataSource(hc);
    }

    private ConnectionManager() {
    }

    public static synchronized ConnectionManager getInstance() {
        if (databasePool == null) {
            databasePool = new ConnectionManager();
        }
        return databasePool;
    }


    /**
     * 获取数据库连接
     *
     * @return
     */
    public Connection getCon() throws SQLException {
        return ds.getConnection();
    }

    public static void close(Connection con, Statement s, ResultSet rs) {
        if (null != con) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("close con error:"+e.getMessage());
            }
        }

        if (null != s) {
            try {
                s.close();
            } catch (SQLException e) {
                System.out.println("close ps error:"+e.getMessage());
            }
        }

        if (null != rs) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("close rs error:"+e.getMessage());
            }
        }
    }


}
