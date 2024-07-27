package sysone.g6e6.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sysone.g6e6.model.Quiz;
import sysone.g6e6.util.DBUtil;

public class QuizRepository {
	public Quiz save(Quiz quiz) throws Exception {
		Connection conn = DBUtil.getInstance().getConn();
		PreparedStatement preparedStatement = conn.prepareStatement(
			"Insert into quizzes (subject_id,problem,answer) values (?, ?, ?)");
		preparedStatement.setInt(1, quiz.getSubjectId());
		preparedStatement.setString(2, quiz.getProblem());
		preparedStatement.setString(3, quiz.getAnswer());
		preparedStatement.executeUpdate();
		return quiz;
	}

	public List<Quiz> findBySubjectId(int subjectId) throws Exception {
		Connection conn = DBUtil.getInstance().getConn();
		PreparedStatement preparedStatement = conn.prepareStatement(
			"Select * From quizzes where subject_id = ?");
		preparedStatement.setInt(1, subjectId);
		ResultSet rs = preparedStatement.executeQuery();
		List<Quiz> quizzes = new ArrayList<>();
		while(rs.next()){
			quizzes.add(new Quiz(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4)));
		}
		return quizzes;
	}
}
