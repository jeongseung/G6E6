package sysone.g6e6.controller;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sysone.g6e6.model.User;
import sysone.g6e6.service.NotificationCreateService;
import sysone.g6e6.util.UserSession;

public class NotificationCreateController {
	@FXML
	private TextField titleField;
	@FXML
	private TextArea contentArea;
	private NotificationCreateService notificationCreateService;

	public NotificationCreateController() {
		this.notificationCreateService = new NotificationCreateService();
	}

	public void handleSaveNotice() throws SQLException {
		User user = UserSession.getInstance().getUser();
		String title = titleField.getText();
		String content = contentArea.getText();
		if (title == null) {
			showAlert(Alert.AlertType.ERROR, "공지 등록 실패", "제목을 입력해주세요");
			return;
		}
		if (content == null){
			showAlert(Alert.AlertType.ERROR, "공지 등록 실패", "내용을 입력해주세요");
			return;
		}
		//공지 저장
		notificationCreateService.createNotification(user.getId(), title, content);
		showAlert(Alert.AlertType.INFORMATION,"공지 등록 성공", "성공적으로 등록했습니다.");
	}

	private void showAlert(Alert.AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
