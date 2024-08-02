package sysone.g6e6.controller;

import java.sql.SQLException;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sysone.g6e6.service.SignUpService;
import sysone.g6e6.util.FXUtil;

public class SignUpController {
	@FXML
	private TextField emailField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private PasswordField confirmPasswordField;
	@FXML
	private TextField nicknameField;

	private SignUpService signUpService;
	private boolean isEmailChecked = false;
	private boolean isNicknameChecked = false;

	public SignUpController() {
		this.signUpService = new SignUpService();
	}

	@FXML
	private void initialize() {
		emailField.textProperty().addListener((observable, oldValue, newValue) -> isEmailChecked = false);
		nicknameField.textProperty().addListener((observable, oldValue, newValue) -> isNicknameChecked = false);
	}

	// 이메일 중복 확인
	@FXML
	private void handleDuplicationCheck(ActionEvent event) throws SQLException {
		String email = emailField.getText();
		if (signUpService.isEmailDuplicate(email)) {
			showAlert(Alert.AlertType.ERROR, "중복 확인", "사용 중인 이메일입니다.");
		} else {
			showAlert(Alert.AlertType.INFORMATION, "중복 확인", "사용 가능한 이메일입니다.");
			isEmailChecked = true;
		}
	}

	@FXML
	private void handleNicknameCheck(ActionEvent event) throws SQLException {
		String nickname = nicknameField.getText();
		if (signUpService.isEmailDuplicate(nickname)) {
			showAlert(Alert.AlertType.ERROR, "중복 확인", "사용 중인 닉네임입니다.");
		} else {
			showAlert(Alert.AlertType.INFORMATION, "중복 확인", "사용 가능한 닉네임입니다.");
			isNicknameChecked = true;
		}
	}

	@FXML
	private void handleSignUp(ActionEvent event) {
		String email = emailField.getText();
		String password = passwordField.getText();
		String nickname = nicknameField.getText();
		String confirmPassword = confirmPasswordField.getText();
		String role = "user";

		if (!isEmailChecked) {
			showAlert(Alert.AlertType.ERROR, "회원가입 실패", "이메일 중복 확인 해주세요");
			return;
		}

		if (!isNicknameChecked) {
			showAlert(Alert.AlertType.ERROR, "회원가입 실패", "닉네임 중복 확인 해주세요");
			return;
		}

		if (!isValidEmail(email)) {
			showAlert(Alert.AlertType.ERROR, "회원가입 실패", "올바른 이메일 형식이 아닙니다.");
			return;
		}

		if (!isValidPassword(password)) {
			showAlert(Alert.AlertType.ERROR, "회원가입 실패", "패스워드는 최소 8자 이상이어야 하며, 대소문자, 숫자, 특수문자를 포함해야 합니다.");
			return;
		}

		if (!password.equals(confirmPassword)) {
			showAlert(Alert.AlertType.ERROR, "회원가입 실패", "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
			return;
		}

		try {
			signUpService.signUpUser(email, password, nickname, role);
			showAlert(Alert.AlertType.INFORMATION, "회원가입 성공", "성공적으로 회원가입되었습니다.");
			FXUtil fx = FXUtil.getInstance();
			fx.changeScene("LoginPage");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private boolean isValidEmail(String email) {
		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		return Pattern.matches(emailRegex, email);
	}

	private boolean isValidPassword(String password) {
		// 최소 8자, 대소문자, 숫자 및 특수문자 포함
		String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,}$";
		return Pattern.matches(passwordRegex, password);
	}

	private void showAlert(Alert.AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	@FXML
	public void handleReturnButton(ActionEvent e) {
		FXUtil.getInstance().changeScene("LoginPage");
	}
}
