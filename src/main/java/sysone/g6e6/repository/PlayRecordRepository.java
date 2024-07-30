package sysone.g6e6.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;

import sysone.g6e6.model.PlayRecord;
import sysone.g6e6.util.DBUtil;

public class PlayRecordRepository {
	private Connection conn = DBUtil.getInstance().getConn();

	public PlayRecord save(PlayRecord playRecord) throws Exception {
		System.out.println(playRecord.toString());
		PreparedStatement pstmt = conn.prepareStatement(
			"insert into playrecords (user_id,subject_id,difficulty,solve_time,play_date) values (?,?,?,?,sysdate)");
		pstmt.setInt(1, playRecord.getUserId());
		pstmt.setInt(2, playRecord.getSubjectId());
		pstmt.setString(3, playRecord.getDifficulty());
		pstmt.setInt(4, playRecord.getSolveTime());
		pstmt.executeUpdate();
		return playRecord;
	}
}
