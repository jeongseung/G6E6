package sysone.g6e6.repository;

import sysone.g6e6.model.Quiz;
import sysone.g6e6.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReviewNoteRepository {
    private Connection conn = DBUtil.getInstance().getConn();

    public List<Quiz> findAllQuizByUserId(int id) throws Exception{
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM quizzes " +
                "WHERE quiz_id IN ( " +
                "    SELECT quiz_id " +
                "    FROM mistakes " +
                "    WHERE user_id = ? " +
                ") " +
                "AND quiz_id NOT IN ( " +
                "    SELECT quiz_id " +
                "    FROM errorreports " +
                ")";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setInt(1,id);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            quizzes.add(new Quiz(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4)));
        }
        System.out.println("도달");
        return quizzes;
    }

}
