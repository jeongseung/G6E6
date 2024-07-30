package sysone.g6e6.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {
	public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(salt.getBytes());
		byte[] hashedPassword = md.digest(password.getBytes());
		return Base64.getEncoder().encodeToString(hashedPassword);
	}

	// 솔트 생성
	public static String generateSalt() {
		SecureRandom sr = new SecureRandom();
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}

}
