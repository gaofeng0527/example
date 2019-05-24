package com.spring.utils;

import org.springframework.util.StringUtils;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class RealQuestionImageReplaceUtil {
    String url = "http://www.edu-edu.com/exam-admin/home/my/admin/real/questionbank/question/attaches/";
    String cookie = "JSESSIONID=86DD7223521652B5638ADD22D290BF59; UM_distinctid=16624739e36108e-0452e83d0e143c-551e3f12-13c680-16624739e371ca; mantisId=87595516e5b24e0bb18bd965bdc41f9e; mantis7055=0569135e68124031b11f391d666e47ee@7055; Hm_lvt_c3f0b24e5f48f939831d8961d740e1c3=1550711988,1553063636; CNZZDATA1261015652=1268210592-1538205349-null%7C1553132025; 128_vq=17; Hm_lvt_8edc16894c9c51fbc1cdf95ce7e14a44=1553148917; _tenant=default; service=\"http://www.edu-edu.com/exam-admin/cas_security_check\"; referer=\"http://www.edu-edu.com/cas/web/login?service=http%3A%2F%2Fwww.edu-edu.com%2Fexam-admin%2Fcas_security_check&_tenant=default\"";

    public static void main(String[] args) throws Exception {
        Long times = new Date().getTime();
        RealQuestionImageReplaceUtil replace = new RealQuestionImageReplaceUtil();
        //replace.replaceQuestionImage();
        replace.replaceQuestionChoice();
        Long endTime = new Date().getTime();
        System.out.println((endTime - times) / 1000 + "秒");
    }

    public void replaceQuestionImage() throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT id,title,answer,hint FROM t_real_question WHERE title LIKE '%<img%' OR answer LIKE '%<img%' OR hint LIKE '%<img%'";
        Connection con = ConnectionManager.getInstance().getCon();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Map<String, Object> question = new HashMap<>();
            question.put("id", rs.getLong("id"));
            question.put("title", rs.getString("title"));
            question.put("answer", rs.getString("answer"));
            question.put("hint", rs.getString("hint"));
            list.add(question);
        }
        rs.close();
        ps.close();
        int count = 0;
        for (Map<String, Object> question : list) {
            Long id = (Long) question.get("id");
            String title = (String) question.get("title");
            String answer = (String) question.get("answer");
            String hint = (String) question.get("hint");
            if (StringUtils.hasText(title) && title.indexOf("upload/file") >= 0) {
                title = ImageUrlUtil.replaceImageUrlToBase64(title, url, cookie);
            }

            if (StringUtils.hasText(answer) && answer.indexOf("upload/file") >= 0) {
                answer = ImageUrlUtil.replaceImageUrlToBase64(answer, url, cookie);
            }

            if (StringUtils.hasText(hint) && hint.indexOf("upload/file") >= 0) {
                hint = ImageUrlUtil.replaceImageUrlToBase64(hint, url, cookie);
            }
            this.updateQuestion(id,title,answer,hint);
            count++;
        }
        con.close();
        System.out.println("共更新了"+count+"道题目");
    }

    private void updateQuestion(Long id,String title, String answer, String hint) throws SQLException {
        String sql ="UPDATE t_real_question SET title = ?, answer = ?,hint = ? WHERE id = ?";
        Connection con = ConnectionManager.getInstance().getCon();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,title);
        ps.setString(2,answer);
        ps.setString(3,hint);
        ps.setLong(4,id);
        ps.executeUpdate();
        ps.close();
        con.close();

    }

    public void replaceQuestionChoice() throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT qc_id,content FROM t_real_quest_choice WHERE content LIKE '%upload/file%'";
        Connection con = ConnectionManager.getInstance().getCon();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Map<String, Object> choice = new HashMap<>();
            choice.put("qc_id", rs.getLong("qc_id"));
            choice.put("content", rs.getString("content"));
            list.add(choice);
        }

        rs.close();

        con.setAutoCommit(false);
        Statement up = con.createStatement();
        int i = 0;
        for (Map<String, Object> choice : list) {
            String content = (String) choice.get("content");
            Long qc_id = (Long) choice.get("qc_id");
            content = ImageUrlUtil.replaceImageUrlToBase64(content,url,cookie);
            up.addBatch("update t_real_quest_choice set content = '" + content + "' where qc_id = " + qc_id);

        }
        up.executeBatch();
        con.commit();
        up.close();
        ps.close();
        con.close();
    }
}
