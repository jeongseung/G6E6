package sysone.g6e6.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sysone.g6e6.model.User;
import sysone.g6e6.util.DBUtil;

public class UserRepository {
	private Connection conn = DBUtil.getInstance().getConn();

	// 유저 아이디 존재하는지 확인
	public boolean isUserExist(String email) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("SELECT EMAIL FROM USERS WHERE EMAIL=?");
		ps.setString(1, email);

		ResultSet rs = ps.executeQuery();

		return rs.next();
	}

	// 이메일 패스워드 일치하는지 확인
	public User findByEmailAndPassword(String email, String password) throws SQLException {
		User user = null;
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM USERS WHERE EMAIL=? AND PASSWORD=?");
		ps.setString(1, email);
		ps.setString(2, password);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			int id = rs.getInt("ID");
			String nickname = rs.getString("NICKNAME");
			String role = rs.getString("ROLE");

			user = new User(id, email, password, nickname, role);
		}
		return user;
	}
}
