package com.spring.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UpdateQuestionAddOppenID {

    public static void main(String[] args) throws SQLException {
        Long times = new Date().getTime();
        UpdateQuestionAddOppenID up = new UpdateQuestionAddOppenID();
        up.updateRealQuestion();
        Long endTime = new Date().getTime();
        System.out.println((endTime - times) / 1000 + "秒");
    }

    public void updateRealQuestion() throws SQLException {

        List<Long> ids = this.findAllQuestionIds();
        IdWorker worker = new IdWorker(0);

        Connection con = ConnectionManager.getInstance().getCon();
        con.setAutoCommit(false);
        Statement s = con.createStatement();
        String sql = "";
        int count = 0;
        for (Long id : ids) {
            sql = "update t_real_question set unique_identification = " + worker.nextId() + " where id = " + id;
            s.addBatch(sql);
            count++;
            if(count % 10000 == 0){
                s.executeBatch();
            }
        }
        s.executeBatch();
        con.commit();
        con.setAutoCommit(true);
        s.close();
        con.close();

    }

    private List<Long> findAllQuestionIds() {
        String sql = "SELECT id FROM t_real_question";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Long> ids = new ArrayList<>();
        try {
            con = ConnectionManager.getInstance().getCon();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ids.add(rs.getLong("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ExportRealQuestionToJson.close(con, ps, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return ids;
    }
}
