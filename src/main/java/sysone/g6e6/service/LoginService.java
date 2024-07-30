package sysone.g6e6.service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import sysone.g6e6.model.User;
import sysone.g6e6.repository.UserRepository;
import sysone.g6e6.util.PasswordUtil;
import sysone.g6e6.util.UserSession;

public class LoginService {
	private UserRepository userRepository;

	public LoginService() {
		this.userRepository = new UserRepository();
	}

	public String checkUser(String email, String password) throws SQLException {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			return "User not found";
		}

		String hashedPassword = null;
		try {
			hashedPassword = PasswordUtil.hashPassword(password, user.getSalt());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to hash password", e);
		}
		if (hashedPassword.equals(user.getPassword())) {
			UserSession.getInstance().setUser(user);
			return "Login success";
		} else {
			return "Wrong password";
		}
	}
}
