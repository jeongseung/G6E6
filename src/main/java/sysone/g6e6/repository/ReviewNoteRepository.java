package sysone.g6e6.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import sysone.g6e6.model.Quiz;
import sysone.g6e6.util.DBUtil;

public class ReviewNoteRepository {
	private Connection conn = DBUtil.getInstance().getConn();

	public List<Quiz> findAllQuizByUserId(int id) throws Exception {
		List<Quiz> quizzes = new ArrayList<>();
		String sql = "select * "
			+ "from quizzes "
			+ "where quiz_id in (select quiz_id "
			+ "                  from mistakes "
			+ "                  where user_id=?)"
			+ "and quiz_id not in (select quiz_id "
			+ "                    from errorreports "
			+ "                    where error_id in (select error_id "
			+ "                                       from usererrorreports "
			+ "                                       where user_id=?))";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setInt(1, id);
		pstmt.setInt(2, id);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			quizzes.add(new Quiz(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4)));
		}
		return quizzes;
	}
}
