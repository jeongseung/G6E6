package sysone.g6e6.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import sysone.g6e6.model.Subject;
import sysone.g6e6.util.DBUtil;

public class SubjectRepository {
	private Connection conn = DBUtil.getInstance().getConn();

	public List<Subject> findAll() throws Exception {
		List<Subject> subjects = new ArrayList<>();

		PreparedStatement pstmt = conn.prepareStatement("select * from subjects");
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			subjects.add(new Subject(rs.getInt(1), rs.getString(2)));
		}
		return subjects;
	}

	public Subject findByContent(String content) throws Exception {
		Subject subject = null;
		PreparedStatement pstmt = conn.prepareStatement("select * from subjects where subject_content = ?");
		pstmt.setString(1, content);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			subject = new Subject(rs.getInt(1), rs.getString(2));
		}
		return subject;
	}
}
