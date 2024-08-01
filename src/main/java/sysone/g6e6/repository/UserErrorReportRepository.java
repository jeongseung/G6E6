package sysone.g6e6.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import sysone.g6e6.model.UserErrorReport;
import sysone.g6e6.util.DBUtil;

public class UserErrorReportRepository {
	Connection conn = DBUtil.getInstance().getConn();

	public UserErrorReport findByErrorId(int errorId) throws Exception {
		PreparedStatement pstmt = conn.prepareStatement("select * from userErrorReports where error_id = ?");
		pstmt.setInt(1, errorId);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		UserErrorReport userErrorReport = new UserErrorReport(rs.getInt(1), rs.getInt(2), rs.getInt(3),
			rs.getTimestamp(4).toLocalDateTime());
		return userErrorReport;
	}

	public void deleteUserErrorReport(Integer id) throws Exception {
		PreparedStatement pstmt = conn.prepareStatement("delete from userErrorReports where error_id = ?");
		pstmt.setInt(1, id);
		pstmt.executeUpdate();
	}

}
