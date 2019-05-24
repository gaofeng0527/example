package com.spring.utils;

import com.alibaba.fastjson.JSON;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 把智题库导出的json文件，解析后，导入到E平台题库中
 * 1.首先在题库中创建一个新的题库，当前导入的数据均默认属于该题库
 * 2.同步课程，成功
 * 3.同步试题，和选项
 * 4.更新试卷对应关系表的题目ID，错题库中的题目ID，收藏本中的题目ID，做题记录中的题目ID
 */
public class ImportRealQuestionToQuestion {

    public static final Long BANKID = 30L;
    public static final String TENANTCODE = "default";
    public static final String LEARNDOMAIN = "ld_zk";
    public static IdWorker idWorker = new IdWorker(0);

    public static void main(String[] args) throws Exception {
        ImportRealQuestionToQuestion imports = new ImportRealQuestionToQuestion();

        //imports.syncCourse();//同步课程
        imports.syncQuestion();//同步问题
    }


    /**
     * 同步智题库课程到E平台题库系统
     * 1.把所有课程导入到t_course表中，题库ID默认为BANKID，租户代码为TENANTCODE
     * 2.更新试卷所属科目ID
     */
    public void syncCourse() throws SQLException {
        Connection con = null;
        try {
            con = ConnectionManager.getInstance().getCon();
            con.setAutoCommit(false);
            List<Map<String, Object>> courses = this.findAllCourse();
            Map<Long, Long> courseIds = this.addAllCourse(courses, con);
            String sql = "UPDATE t_real_paper SET subject_id = ? WHERE subject_id = ?";
            String updateQuestion = "UPDATE t_real_question SET subject_id = ? WHERE subject_id = ?";
            for (Long oldId : courseIds.keySet()) {
                this.updateRealPaperOrQuestion(sql, oldId, courseIds.get(oldId), con);
                this.updateRealPaperOrQuestion(updateQuestion, oldId, courseIds.get(oldId), con);
            }

            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
            con.rollback();
        } finally {
            con.close();
        }

    }

    /**
     * 更新试卷对应的科目ID
     * 更新题目对应的科目ID
     *
     * @param oldId
     * @param newId
     */
    private void updateRealPaperOrQuestion(String sql, Long oldId, Long newId, Connection con) throws SQLException {

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setLong(1, newId);
        ps.setLong(2, oldId);
        ps.executeUpdate();
        ConnectionManager.close(null, ps, null);
    }

    /**
     * 添加科目
     *
     * @param courses
     * @return
     */
    private Map<Long, Long> addAllCourse(List<Map<String, Object>> courses, Connection con) throws Exception {
        Map<Long, Long> map = new HashMap<>();
        String name = null;
        String code = null;
        int type = 0;

        for (Map<String, Object> course : courses) {
            name = (String) course.get("name");
            code = (String) course.get("code");
            type = (int) course.get("type");
            Long courseId = this.addCourse(name, code, type, con);
            map.put((Long) course.get("id"), courseId);
        }
        return map;
    }

    /**
     * 像题库中添加一个课程
     *
     * @param courseName
     * @param courseCode
     * @return
     */
    private Long addCourse(String courseName, String courseCode, int type, Connection con) throws Exception {
        String sql = "INSERT INTO t_course(NAME,bank_id,course_code,tenant_code,created_time,type,learning_domain) VALUE(?,?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, courseName);
        ps.setLong(2, ImportRealQuestionToQuestion.BANKID);
        ps.setString(3, courseCode);
        ps.setString(4, ImportRealQuestionToQuestion.TENANTCODE);
        ps.setDate(5, new Date(new java.util.Date().getTime()));
        ps.setInt(6, type);
        ps.setString(7, ImportRealQuestionToQuestion.LEARNDOMAIN);

        ps.executeUpdate();
        Long courseId = null;
        ResultSet rs = ps.getGeneratedKeys();
        while (rs.next()) {
            courseId = rs.getLong(1);
        }
        ConnectionManager.close(null, ps, null);
        return courseId;

    }


    /**
     * 查询所有课程
     *
     * @return
     */
    private List<Map<String, Object>> findAllCourse() {
        String sql = "SELECT id,NAME,CODE,TYPE FROM t_real_subject";
        List<Map<String, Object>> list = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getInstance().getCon();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            Map<String, Object> map = null;
            while (rs.next()) {
                map = new HashMap<>();
                map.put("name", rs.getString("name"));
                map.put("code", rs.getString("code"));
                map.put("type", rs.getInt("type"));
                map.put("id", rs.getLong("id"));
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(con, ps, rs);

        }
        return list;
    }

    /**
     * 查询所有试卷ID
     *
     * @return
     */
    private List<Long> findAllPaperIds() {
        String sql = "SELECT real_paper_id FROM t_real_paper where sync = 0 ORDER BY real_paper_id";
        List<Long> list = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getInstance().getCon();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            Map<String, Object> map = null;
            while (rs.next()) {

                list.add(rs.getLong("real_paper_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(con, ps, rs);

        }
        return list;
    }

    public void syncQuestion() {
        ExportRealQuestionToJson ex = new ExportRealQuestionToJson();
        Connection con = null;
        try {
            con = ConnectionManager.getInstance().getCon();
            con.setAutoCommit(false);
            List<Long> paperIds = this.findAllPaperIds();
            List<Map<String, Object>> questionTypes = null;
            List<Map<String, Object>> questions = null;
            Long oldId = null;
            List<Map<String, Object>> subQuestions = null;
            List<Map<String, Object>> choices = null;
            for (Long id : paperIds) {
                System.out.println("试卷ID：" + id);
                questionTypes = ex.findQuestionByPaperId(id);
                for (Map<String, Object> questionType : questionTypes) {
                    questions = (List<Map<String, Object>>) questionType.get("questions");
                    for (Map<String, Object> question : questions) {
                        int baseType = (int) question.get("base_type");
                        oldId = (Long) question.get("id");
                        if (baseType == 4) {
                            Long parentId = this.addQuestion(question, null, con);
                            this.updateQuestionId(oldId, parentId, con);
                            subQuestions = (List<Map<String, Object>>) question.get("subquestions");
                            for (Map<String, Object> subQuestion : subQuestions) {
                                Long questionId = this.addQuestion(subQuestion, parentId, con);
                                this.updateQuestionId(oldId, questionId, con);
                                choices = (List<Map<String, Object>>) subQuestion.get("choice");
                                for (Map<String, Object> choice : choices) {
                                    this.addChoice(choice, questionId, con);
                                }
                            }
                        } else {
                            Long questionId = this.addQuestion(question, null, con);
                            this.updateQuestionId(oldId, questionId, con);
                            choices = (List<Map<String, Object>>) question.get("choice");
                            for (Map<String, Object> choice : choices) {
                                this.addChoice(choice, questionId, con);
                            }
                        }

                    }
                }
                //更新一次试卷状态，并且提交数据库
                updateRealPaper(id, con);
                con.commit();

            }
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            ConnectionManager.close(con, null, null);
        }

    }

    private void updateRealPaper(Long id, Connection con) throws SQLException {
        String sql = "UPDATE t_real_paper SET sync = 1 WHERE real_paper_id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setLong(1, id);
        ps.executeUpdate();
        ps.close();
    }


    /**
     * 更新错题本，收藏题，试卷套数，题目对应表
     *
     * @param oldId
     * @param newId
     */
    private void updateQuestionId(Long oldId, Long newId, Connection con) throws Exception {
        String updateError = "UPDATE t_real_error_questions SET question_id = ? WHERE question_id = ?";
        this.updateRealPaperOrQuestion(updateError, oldId, newId, con);

        String updateFavorite = "UPDATE t_real_favorite_questions SET question_id = ? WHERE question_id = ?";
        this.updateRealPaperOrQuestion(updateFavorite, oldId, newId, con);

        String updatePaperSuit = "UPDATE t_real_paper_suit_question SET question_id = ? WHERE question_id = ?";
        this.updateRealPaperOrQuestion(updatePaperSuit, oldId, newId, con);

        String updateExamResultDetail = "UPDATE t_real_exam_result_detail SET question_id = ? WHERE question_id = ?";
        this.updateRealPaperOrQuestion(updateExamResultDetail, oldId, newId, con);

    }

    /**
     * 添加题目
     *
     * @param question
     */
    private Long addQuestion(Map<String, Object> question, Long parentId, Connection con) throws Exception {
        String sql = "INSERT INTO t_question (id,bank_id,course_id,base_type,title,answer,score,difficulty,hint,level_type,parent_id,add_user,STATUS,create_time) " +
                "VALUE(?,?,?,?,?,?,?,1,?,1,?,?,0,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        Long questionId = idWorker.nextId();
        ps.setLong(1, questionId);
        ps.setLong(2, ImportRealQuestionToQuestion.BANKID);
        ps.setLong(3, (Long) question.get("subject_id"));
        ps.setInt(4, (Integer) question.get("base_type"));
        ps.setString(5, (String) question.get("title"));
        String answer = null == question.get("answer") ? null : (String) question.get("answer");
        if (null == answer) {
            ps.setNull(6, Types.VARCHAR);
        } else {
            ps.setString(6, answer);
        }
        Float score = null == question.get("score") ? 0 : (Float) question.get("score");
        if (null == score) {
            ps.setNull(7, Types.FLOAT);
        } else {
            ps.setFloat(7, score);
        }

        String hint = null == question.get("hint") ? null : (String) question.get("hint");
        if (null == hint) {
            ps.setNull(8, Types.VARCHAR);
        } else {
            ps.setString(8, hint);
        }

        if (null == parentId) {
            ps.setNull(9, Types.BIGINT);
        } else {
            ps.setLong(9, parentId);
        }

        String addUser = null == question.get("add_user") ? null : (String) question.get("add_user");
        if (null == addUser) {
            ps.setNull(10, Types.VARCHAR);
        } else {
            ps.setString(10, addUser);
        }
        ps.setDate(11, new Date((new java.util.Date().getTime())));

        ps.executeUpdate();
        ConnectionManager.close(null, ps, null);
        return questionId;
    }

    private void addChoice(Map<String, Object> choice, Long questionId, Connection con) throws SQLException {
        String sql = "INSERT INTO t_quest_choice (question_id,qc_order,content) VALUE(?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setLong(1, questionId);
        ps.setString(2, (String) choice.get("qc_order"));
        ps.setString(3, (String) choice.get("content"));
        ps.executeUpdate();
        ConnectionManager.close(null, ps, null);

    }


}
