package sysone.g6e6.controller;

import java.sql.SQLException;

import javafx.fxml.FXML;
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
		notificationCreateService.createNotification(user.getId(), title, content);
		//공지 저장
	}
}
