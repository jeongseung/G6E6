package sysone.g6e6.controller;

import java.sql.SQLException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import sysone.g6e6.model.Notification;
import sysone.g6e6.model.User;
import sysone.g6e6.service.AdminMainService;
import sysone.g6e6.util.FXUtil;
import sysone.g6e6.util.UserSession;

public class AdminMainController {
	@FXML
	private Text noNotiText;
	@FXML
	private Label welcomeLabel;
	@FXML
	private Label noticeLabel1;
	@FXML
	private Label noticeLabel2;
	@FXML
	private Label noticeLabel3;
	@FXML
	private Button noticeButton1;
	@FXML
	private Button noticeButton2;
	@FXML
	private Button noticeButton3;
	@FXML
	private Group noticeGroup1;
	@FXML
	private Group noticeGroup2;
	@FXML
	private Group noticeGroup3;

	private AdminMainService adminMainService;
	private List<Notification> latestNotifications;

	public AdminMainController() {
		this.adminMainService = new AdminMainService();
	}

	public void initialize() throws SQLException {
		User user = UserSession.getInstance().getUser();
		String nickname = user.getNickname();
		welcomeLabel.setText("환영합니다 " + nickname + "님");

		latestNotifications = adminMainService.getLatestNotifications(3);

		if (latestNotifications.size() > 0) {
			noticeButton1.setText(latestNotifications.get(0).getTitle());
			noNotiText.setVisible(false);
			noticeButton1.setVisible(true);
		}

		if (latestNotifications.size() > 1) {
			noticeButton2.setText(latestNotifications.get(1).getTitle());
			noticeButton2.setVisible(true);
		}

		if (latestNotifications.size() > 2) {
			noticeButton3.setText(latestNotifications.get(2).getTitle());
			noticeButton3.setVisible(true);
		}
	}

	FXUtil fx = FXUtil.getInstance();

	@FXML
	public void handleGoToCreateNotification(ActionEvent event) {
		fx.changeScene("NotificationCreatePage");
	}

	@FXML
	public void handleGoToErrorReports(ActionEvent event) {
		fx.changeScene("ErrorReportPage");
	}

	@FXML
	public void handleGoToManageSubject(ActionEvent event) {
		fx.changeScene("SubjectManagePage");
	}

	@FXML
	public void handleNotice1(ActionEvent event) {
		if (latestNotifications.size() > 0) {
			noticeLabel1.setText(latestNotifications.get(0).getContent());
			noticeGroup1.setVisible(true);
		}
	}

	@FXML
	public void handleNotice2(ActionEvent event) {
		if (latestNotifications.size() > 1) {
			noticeLabel2.setText(latestNotifications.get(1).getContent());
			noticeGroup2.setVisible(true);
		}
	}

	@FXML
	public void handleNotice3(ActionEvent event) {
		if (latestNotifications.size() > 2) {
			noticeLabel3.setText(latestNotifications.get(2).getContent());
			noticeGroup3.setVisible(true);
		}
	}

}
