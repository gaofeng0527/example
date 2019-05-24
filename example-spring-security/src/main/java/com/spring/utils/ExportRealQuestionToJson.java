package com.spring.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExportRealQuestionToJson {

    /**
     * 存储目标库课程编码==》课程Id
     */
    public static Map<String, Long> courses = new HashMap<>();

    /**
     * 准备数据
     */
    static {
        String sql = "SELECT course_id,course_code FROM t_course";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getInstance().getCon();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                courses.put(rs.getString("course_code"), rs.getLong("course_id"));
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
    }

    public static void main(String[] args) throws SQLException, IOException {
        Long times = new Date().getTime();
        System.out.println("==========开始" + times);
        ExportRealQuestionToJson export = new ExportRealQuestionToJson();
        export.exportRealQuestion();
        Long endTime = new Date().getTime();
        System.out.println((endTime - times) / 1000 + "秒");
    }


    /**
     * 查询某个试卷下的题目
     *
     * @param paperId
     * @return
     * @throws SQLException
     */
    public List<Map<String, Object>> findQuestionByPaperId(Long paperId) throws SQLException {
        List<Map<String, Object>> questionTypes = this.findQuestionType(paperId);
        for (Map<String, Object> questionType : questionTypes) {
            List<Map<String, Object>> questions = this.findQuestionByQuestionType((Long) questionType.get("id"));
            for (Map<String, Object> question : questions) {
                List<Map<String, Object>> subQuestions = new ArrayList<>();
                if ((int) question.get("base_type") == 4) {
                    subQuestions = this.findSubQuestionByParentId((Long) question.get("id"));
                }
                question.put("subquestions", subQuestions);

            }
            questionType.put("questions", questions);
        }
        return questionTypes;
    }

    public void exportRealQuestion() throws SQLException, IOException {
        //存储最终结果
        List<Map<String, Object>> result = new ArrayList<>();
        //查询所有科目
        List<Map<String, Object>> subjects = null;
        try {
            subjects = this.findAllSubject();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Map<String, Object> subject : subjects) {
            List<Map<String, Object>> papers = this.findPaperBySubjectId((Long) subject.get("id"), (String) subject.get("code"));
            for (Map<String, Object> paper : papers) {
                List<Map<String, Object>> questionTypes = this.findQuestionType((Long) paper.get("real_paper_id"));
                for (Map<String, Object> questionType : questionTypes) {
                    //查询题目
                    List<Map<String, Object>> questions = this.findQuestionByQuestionType((Long) questionType.get("id"));
                    for (Map<String, Object> question : questions) {
                        Map<String, Object> oldQuestion = this.findEqualQuestion((int) question.get("base_type"), (String) subject.get("code"), (String) question.get("answer"), (String) question.get("title"), (Float) question.get("score"));
                        if (null != oldQuestion) {
                            question.putAll(oldQuestion);
                        }
                        List<Map<String, Object>> subQuestions = new ArrayList<>();
                        if ((int) question.get("base_type") == 4) {
                            subQuestions = this.findSubQuestionByParentId((Long) question.get("id"));
                        }
                        question.put("subquestions", subQuestions);

                    }
                    questionType.put("quesitons", questions);
                }
                paper.put("questionType", questionTypes);
            }
            subject.put("paper", papers);
            String json = JSON.toJSONString(subject);
            wirterJson(json, (String) subject.get("code"));
            //System.out.println(json);
        }
    }

    private void wirterJson(String content, String courseCode) throws IOException {
        String path = "F:\\a\\questionJson\\" + courseCode + ".json";
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter writer = new FileWriter(path);
        BufferedWriter bw = new BufferedWriter(writer);
        bw.write(content);
        bw.flush();
        bw.close();

    }

    /**
     * 查询该科目下的试卷
     *
     * @param subjectId
     * @param code
     * @return
     * @throws SQLException
     */
    private List<Map<String, Object>> findPaperBySubjectId(Long subjectId, String code) throws SQLException {
        List<Map<String, Object>> result = new ArrayList<>();
        String sql = "SELECT real_paper_id,title,subject_id,CODE,full_score,pass_score,suit_num,add_user,is_create FROM t_real_paper WHERE subject_id = ?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConnectionManager.getInstance().getCon();
            ps = con.prepareStatement(sql);
            ps.setLong(1, subjectId);
            rs = ps.executeQuery();
            Map<String, Object> paper = null;
            while (rs.next()) {
                paper = new HashMap<>();
                paper.put("real_paper_id", rs.getLong("real_paper_id"));
                paper.put("title", rs.getString("title"));
                paper.put("course_code", code);
                paper.put("full_score", rs.getInt("full_score"));
                paper.put("pass_score", rs.getInt("pass_score"));
                paper.put("suit_num", rs.getInt("suit_num"));
                paper.put("add_user", rs.getString("add_user"));
                paper.put("is_create", rs.getInt("is_create"));
                List<Map<String, Object>> suits = this.findSuit(rs.getLong("real_paper_id"));
                paper.put("suit", suits);
                result.add(paper);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.close(con, ps, rs);
        }
        return result;
    }

    /**
     * 根据试卷ID查询该试卷下所有的大题题型
     *
     * @param paper_id
     * @return
     */
    private List<Map<String, Object>> findQuestionType(Long paper_id) throws SQLException {
        List<Map<String, Object>> result = new ArrayList<>();
        String sql = "SELECT id,paper_id,seq_num,base_type,title,description FROM t_real_paper_quest_type WHERE paper_id = ?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getInstance().getCon();
            ps = con.prepareStatement(sql);
            ps.setLong(1, paper_id);
            rs = ps.executeQuery();
            Map<String, Object> questionType = null;
            while (rs.next()) {
                questionType = new HashMap<>();
                questionType.put("id", rs.getLong("id"));
                questionType.put("paper_id", rs.getLong("paper_id"));
                questionType.put("seq_num", rs.getInt("seq_num"));
                questionType.put("base_type", rs.getInt("base_type"));
                questionType.put("title", rs.getString("title"));
                questionType.put("description", rs.getString("description"));
                result.add(questionType);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con, ps, rs);
        }
        return result;
    }

    /**
     * 根据问题类型ID查询题目
     *
     * @param questiontType_id
     * @return
     */
    private List<Map<String, Object>> findQuestionByQuestionType(Long questiontType_id) {
        String sql = "SELECT rq.id,rq.subject_id,rq.base_type,rq.title,rq.code,rq.answer,rq.score,rq.hint,rq.add_user,rq.parent_id FROM t_real_paper_suit_question AS rpsq JOIN t_real_question AS rq ON rpsq.question_id = rq.id WHERE rpsq.question_type_id = ? AND (rq.parent_id IS NULL OR rq.parent_id = 0)";
        List<Map<String, Object>> result = this.queryQuestion(sql, questiontType_id);
        return result;
    }

    private List<Map<String, Object>> queryQuestion(String sql, Long id) {
        List<Map<String, Object>> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getInstance().getCon();
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            Map<String, Object> question = null;
            while (rs.next()) {
                question = new HashMap<>();
                question.put("id", rs.getLong("id"));
                question.put("subject_id", rs.getLong("subject_id"));
                question.put("base_type", rs.getInt("base_type"));
                question.put("title", rs.getString("title"));
                question.put("code", rs.getString("code"));
                question.put("answer", rs.getString("answer"));
                question.put("score", rs.getFloat("score"));
                question.put("hint", rs.getString("hint"));
                question.put("add_user", rs.getString("add_user"));
                question.put("parent_id", rs.getLong("parent_id"));
                question.put("choice", findChoice(rs.getLong("id")));
                result.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(con, ps, rs);
        }
        return result;
    }

    private List<Map<String, Object>> findChoice(Long id) {
        List<Map<String, Object>> choices = new ArrayList<>();
        String sql = "SELECT qc_order,content FROM t_real_quest_choice where question_id = ?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getInstance().getCon();
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            Map<String, Object> choice = null;
            while (rs.next()) {
                choice = new HashMap<>();
                choice.put("qc_order", rs.getString("qc_order"));
                choice.put("content", rs.getString("content"));
                choices.add(choice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(con, ps, rs);
        }
        return choices;
    }

    /**
     * 根据父ID查询小题
     *
     * @param parentId
     * @return
     */
    private List<Map<String, Object>> findSubQuestionByParentId(Long parentId) {
        String sql = "SELECT id,subject_id,base_type,title,CODE,answer,score,hint,add_user,parent_id FROM t_real_question WHERE parent_id  = ?";
        List<Map<String, Object>> result = this.queryQuestion(sql, parentId);
        return result;

    }

    /**
     * 查询目标题库，是否已经存在该题目，根据基本题型，答案，所属课程，题干完全相同即表示同一个题目
     *
     * @param baseType
     * @param courseCode
     * @param answer
     * @param title
     * @return
     */
    private Map<String, Object> findEqualQuestion(int baseType, String courseCode, String answer, String title, float score) {

        Long course_id = courses.get(courseCode);
        if (null == course_id) {
            return null;
        }
        String staticTitle = null;
        title = this.replaceP(title);
        if (StringUtils.hasText(title) && title.length() > 20) {
            staticTitle = title.substring(0, 20);
        }

        String staticAnswer = null;
        answer = this.replaceP(answer);
        if (StringUtils.hasText(answer) && answer.length() > 20) {
            staticAnswer = answer.substring(0, 20);
        }


        String sql = "SELECT id,base_type,title,answer,score,hint FROM t_question WHERE course_id = ? AND base_type = ? and score = ?";
        if (StringUtils.hasText(title) && title.length() > 20) {
            sql += " AND title like ?";
            staticTitle = "%" + staticTitle + "%";
        } else {
            sql += " AND title = ?";
            staticTitle = title;
        }

        if (StringUtils.hasText(answer) && answer.length() > 20) {
            sql += " AND answer like ?";
            staticAnswer = "%" + staticAnswer + "%";
        } else if (StringUtils.hasText(answer)) {
            sql += " AND answer = ?";
            staticAnswer = answer;
        }

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, Object> question = null;
        try {
            con = ConnectionManager.getInstance().getCon();
            ps = con.prepareStatement(sql);
            ps.setLong(1, course_id);
            ps.setInt(2, baseType);
            ps.setFloat(3, score);
            ps.setString(4, staticTitle);
            if (StringUtils.hasText(staticAnswer)) {
                ps.setString(5, staticAnswer);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                question = new HashMap<>();
                question.put("oldid", rs.getLong("id"));
                question.put("oldbase_type", rs.getInt("base_type"));
                question.put("oldtitle", rs.getString("title"));
                question.put("oldanswer", rs.getString("answer"));
                question.put("oldhint", rs.getString("hint"));
                question.put("oldscore", rs.getFloat("score"));

            }

        } catch (SQLException e) {
            System.out.println(sql);
            e.printStackTrace();
        } finally {
            try {
                close(con, ps, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return question;
    }


    private String replaceP(String content) {
        if (StringUtils.hasText(content) && content.indexOf("<p>") > 4) {
            content = content.replace("<p>", "");
        }
        return content;
    }

    /**
     * 查询试卷下的套数
     *
     * @param paper_id
     * @return
     * @throws SQLException
     */
    private List<Map<String, Object>> findSuit(Long paper_id) throws SQLException {
        List<Map<String, Object>> result = new ArrayList<>();
        String sql = "SELECT id,paper_id,num FROM t_real_paper_suit WHERE paper_id = ?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getInstance().getCon();
            ps = con.prepareStatement(sql);
            ps.setLong(1, paper_id);
            rs = ps.executeQuery();
            Map<String, Object> suit = null;
            while (rs.next()) {
                suit = new HashMap<>();
                suit.put("id", rs.getLong("id"));
                suit.put("paper_id", rs.getLong("paper_id"));
                suit.put("num", rs.getInt("num"));
                result.add(suit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.close(con, ps, rs);
        }
        return result;
    }

    /**
     * 查询所有科目
     *
     * @return
     */
    private List<Map<String, Object>> findAllSubject() throws SQLException {
        List<Map<String, Object>> result = new ArrayList<>();
        String sql = "SELECT id,NAME,CODE,paper_count FROM t_real_subject";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getInstance().getCon();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            Map<String, Object> subject = null;
            while (rs.next()) {
                subject = new HashMap<>();
                subject.put("id", rs.getLong("id"));
                subject.put("name", rs.getString("name"));
                subject.put("code", rs.getString("code"));
                subject.put("paper_count", rs.getInt("paper_count"));
                result.add(subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.close(con, ps, rs);
        }
        return result;
    }

    public static void close(Connection con, Statement s, ResultSet rs) throws SQLException {
        if (null != con) {
            con.close();
        }

        if (null != s) {
            s.close();
        }

        if (null != rs) {
            rs.close();
        }
    }

}
