package sysone.g6e6.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import sysone.g6e6.model.Quiz;
import sysone.g6e6.util.DBUtil;

public class QuizRepository {
	public Quiz save(Quiz quiz) throws Exception {
		System.out.println(quiz.toString());
		Connection conn = DBUtil.getInstance().getConn();
		PreparedStatement preparedStatement = conn.prepareStatement(
			"Insert into quizzes (subject_id,problem,answer) values (?, ?, ?)");
		preparedStatement.setInt(1, quiz.getSubjectId());
		preparedStatement.setString(2, quiz.getProblem());
		preparedStatement.setString(3, quiz.getAnswer());
		preparedStatement.executeUpdate();
		return quiz;
	}
}
