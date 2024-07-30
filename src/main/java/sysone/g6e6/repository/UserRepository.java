package sysone.g6e6.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sysone.g6e6.model.User;
import sysone.g6e6.util.DBUtil;

public class UserRepository {
	private Connection conn = DBUtil.getInstance().getConn();

	public boolean isUserExist(String email) throws SQLException {
		String query = "SELECT EMAIL FROM USERS WHERE EMAIL=?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, email);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		}
	}

	// 이메일 일치하는 유저 찾기
	public User findByEmail(String email) throws SQLException {
		User user = null;
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM USERS WHERE EMAIL=?");
		ps.setString(1, email);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			int id = rs.getInt("USER_ID");
			String password = rs.getString("PASSWORD");
			String nickname = rs.getString("NICKNAME");
			String role = rs.getString("ROLE");
			String salt = rs.getString("SALT");

			user = new User(id, email, password, nickname, role, salt);
		}
		return user;
	}

	public User saveUser(User user) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(
			"INSERT INTO USERS (EMAIL, PASSWORD, NICKNAME, ROLE, SALT) values(?,?,?,?,?)");
		ps.setString(1, user.getEmail());
		ps.setString(2, user.getPassword());
		ps.setString(3, user.getNickname());
		ps.setString(4, user.getRole());
		ps.setString(5, user.getSalt());
		ps.executeUpdate();
		return user;
	}
}
