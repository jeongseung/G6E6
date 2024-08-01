package sysone.g6e6.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import sysone.g6e6.model.ErrorReport;
import sysone.g6e6.util.DBUtil;

public class ErrorReportRepository {
	private Connection conn = DBUtil.getInstance().getConn();
	private CallableStatement callableStatement = null;

	public List<ErrorReport> findAllUnresolved() throws Exception {
		List<ErrorReport> errorReports = new ArrayList<>();
		PreparedStatement pstmt = conn.prepareStatement("select * from errorReports where resolved=0");
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			errorReports.add(new ErrorReport(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getByte(4)));
		}
		return errorReports;
	}

	public void saveErrorReports(int userId, int quizId) throws Exception {
		String plSql = "{ ? = call add_error_report(?, ?) }";
		callableStatement = conn.prepareCall(plSql);
		callableStatement.registerOutParameter(1, Types.VARCHAR);
		callableStatement.setInt(2, userId);
		callableStatement.setInt(3, quizId);
		callableStatement.execute();
	}

	public void deleteErrorReport(Integer id) throws Exception {
		PreparedStatement pstmt = conn.prepareStatement("delete from errorReports where error_id = ?");
		pstmt.setInt(1, id);
		pstmt.executeUpdate();
	}
}
