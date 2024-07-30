package sysone.g6e6.service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import sysone.g6e6.model.User;
import sysone.g6e6.repository.UserRepository;
import sysone.g6e6.util.PasswordUtil;

public class SignUpService {
	private UserRepository userRepository;

	public SignUpService() {
		this.userRepository = new UserRepository();
	}

	public boolean isEmailDuplicate(String email) throws SQLException {
		return userRepository.isUserExist(email);
	}

	public User signUpUser(String email, String password, String nickname, String role) throws SQLException {
		// 솔트 생성
		String salt = PasswordUtil.generateSalt();

		// 비밀번호 해시화
		String hashedPassword = null;
		try {
			hashedPassword = PasswordUtil.hashPassword(password, salt);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to hash password", e);
		}

		// 사용자 객체 생성
		User user = new User(null, email, hashedPassword, nickname, role, salt);

		// 사용자 저장
		return userRepository.saveUser(user);
	}
}
