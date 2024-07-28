package sysone.g6e6.service;

import java.sql.SQLException;

import sysone.g6e6.model.User;
import sysone.g6e6.repository.UserRepository;
import sysone.g6e6.util.UserSession;

public class LoginService {
	private  UserRepository userRepository;

	public LoginService() { this.userRepository = new UserRepository(); }

	public String checkUser(String email, String password) throws SQLException {
		if (!userRepository.isUserExist(email)) {
			return "User not found";
		}

		User user = userRepository.findByEmailAndPassword(email, password);
		if (user != null) {
			UserSession.getInstance().setUser(user);
			return "Login successful";
		} else {
			return "Wrong password";
		}
	}
}
