package sysone.g6e6.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

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
		pstmt.setDouble(4, playRecord.getSolveTime());
		pstmt.executeUpdate();
		return playRecord;
	}

	public List<PlayRecord> findByIdAndDate(int userId, int year, int month) throws Exception {
		List<PlayRecord> myMonthlyRecords = new ArrayList<>();
		LocalDate startDate = LocalDate.of(year, month, 1);
		LocalDateTime startDateTime = startDate.atStartOfDay();
		LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
		LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

		PreparedStatement pstmt = conn.prepareStatement(
			"SELECT * FROM playrecords " +
				"WHERE user_id = ? "
				+ "AND play_date BETWEEN ? AND ?"
		);
		pstmt.setInt(1, userId);
		pstmt.setTimestamp(2, Timestamp.valueOf(startDateTime));
		pstmt.setTimestamp(3, Timestamp.valueOf(endDateTime));
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			myMonthlyRecords.add(new PlayRecord(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5),
				new Timestamp(rs.getDate(6).getTime()).toLocalDateTime()));
		}
		return myMonthlyRecords;
	}

	public boolean findCounts(int userId, int curYear, int curMonth) throws Exception {
		YearMonth yearMonth = YearMonth.of(curYear, curMonth);
		LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();
		LocalDateTime endDateTime = lastDayOfMonth.atTime(23, 59, 59);
		PreparedStatement pstmt = conn.prepareStatement("select 1 from playrecords where "
			+ "user_id = ? and "
			+ "play_date < ? and rownum = 1"
		);
		pstmt.setInt(1, userId);
		pstmt.setTimestamp(2, Timestamp.valueOf(endDateTime));
		ResultSet rs = pstmt.executeQuery();
		return rs.next();
	}
}
