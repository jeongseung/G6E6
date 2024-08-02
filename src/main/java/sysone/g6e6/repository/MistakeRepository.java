package sysone.g6e6.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;

import sysone.g6e6.model.Mistake;
import sysone.g6e6.util.DBUtil;

public class MistakeRepository {
	private Connection conn = DBUtil.getInstance().getConn();

	public Mistake save(Mistake mistake) throws Exception {
		PreparedStatement pstmt = conn.prepareStatement(
			"insert into mistakes (quiz_id,user_id,subject_id,mistake_date) values (?,?,?,sysdate)");
		pstmt.setInt(1, mistake.getQuizId());
		pstmt.setInt(2, mistake.getUserId());
		pstmt.setInt(3, mistake.getSubjectId());
		pstmt.executeUpdate();
		return mistake;
	}

	public void deleteByQuizId(Integer quizId) throws Exception {
		PreparedStatement pstmt = conn.prepareStatement("delete from mistakes where quiz_id = ?");
		pstmt.setInt(1, quizId);
		pstmt.executeUpdate();
	}
}
