package sysone.g6e6.controller;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import sysone.g6e6.model.User;
import sysone.g6e6.service.LoginService;
import sysone.g6e6.util.FXUtil;
import sysone.g6e6.util.UserSession;

public class LoginController {

	@FXML
	private TextField emailField;
	@FXML
	private TextField passwordField;

	private LoginService loginService;

	public LoginController() {
		this.loginService = new LoginService();
	}

	// 로그인 기능
	@FXML
	private void handleLogin() {
		String email = emailField.getText();
		String password = passwordField.getText();

		String message = null;
		try {
			message = loginService.checkUser(email, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (message != null && message.equals("Login success")) {
			showAlert(Alert.AlertType.INFORMATION, "로그인 성공", "성공적으로 로그인했습니다!");

			FXUtil fx = FXUtil.getInstance();
			User user = UserSession.getInstance().getUser();
			String role = user.getRole();
			fx.changeScene(role.equals("admin") ? "AdminMainPage" : "MainPage");
		} else {
			showAlert(Alert.AlertType.ERROR, "로그인 실패", message);
		}
	}

	// 회원가입 페이지로 이동
	@FXML
	private void handleGoToSignUp() {
		FXUtil fx = FXUtil.getInstance();
		fx.changeScene("SignUpPage");
	}

	// 알림 보여주기
	private void showAlert(Alert.AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
