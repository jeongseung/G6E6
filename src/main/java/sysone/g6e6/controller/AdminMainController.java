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
	private Label noticeLabel;
	@FXML
	private Button noticeButton1, noticeButton2, noticeButton3, noticeButton4, noticeButton5, noticeButton6, noticeButton7, noticeButton8;
	@FXML
	private Group noticeGroup;

	private AdminMainService adminMainService;
	private List<Notification> latestNotifications;

	public AdminMainController() {
		this.adminMainService = new AdminMainService();
	}

	public void initialize() {
		User user = UserSession.getInstance().getUser();
		String nickname = user.getNickname();
		welcomeLabel.setText("환영합니다 " + nickname + "님");

		try {
			latestNotifications = adminMainService.getLatestNotifications(8);
		} catch (SQLException e) {
			e.printStackTrace();
		}

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

		if (latestNotifications.size() > 3) {
			noticeButton4.setText(latestNotifications.get(3).getTitle());
			noticeButton4.setVisible(true);
		}

		if (latestNotifications.size() > 4) {
			noticeButton5.setText(latestNotifications.get(4).getTitle());
			noticeButton5.setVisible(true);
		}

		if (latestNotifications.size() > 5) {
			noticeButton6.setText(latestNotifications.get(5).getTitle());
			noticeButton6.setVisible(true);
		}

		if (latestNotifications.size() > 6) {
			noticeButton7.setText(latestNotifications.get(6).getTitle());
			noticeButton7.setVisible(true);
		}

		if (latestNotifications.size() > 7) {
			noticeButton8.setText(latestNotifications.get(7).getTitle());
			noticeButton8.setVisible(true);
		}
	}

	FXUtil fx = FXUtil.getInstance();

	@FXML
	public void handleGoToCreateNotification(ActionEvent event) {
		fx.changeScene("NotificationCreate");
	}

	@FXML
	public void handleGoToErrorReports(ActionEvent event) {
		fx.changeScene("AdminErrorPage");
	}

	@FXML
	public void handleGoToManageSubject(ActionEvent event) {
		fx.changeScene("SubjectManagePage");
	}

	@FXML
	public void handleNotice1(ActionEvent event) {
		if (latestNotifications.size() > 0) {
			noticeLabel.setText(latestNotifications.get(0).getContent());
			noticeGroup.setVisible(true);
		}
	}

	@FXML
	public void handleNotice2(ActionEvent event) {
		if (latestNotifications.size() > 1) {
			noticeLabel.setText(latestNotifications.get(1).getContent());
			noticeGroup.setVisible(true);
		}
	}

	@FXML
	public void handleNotice3(ActionEvent event) {
		if (latestNotifications.size() > 2) {
			noticeLabel.setText(latestNotifications.get(2).getContent());
			noticeGroup.setVisible(true);
		}
	}

	@FXML
	public void handleNotice4(ActionEvent event) {
		if (latestNotifications.size() > 3) {
			noticeLabel.setText(latestNotifications.get(3).getContent());
			noticeGroup.setVisible(true);
		}
	}

	@FXML
	public void handleNotice5(ActionEvent event) {
		if (latestNotifications.size() > 4) {
			noticeLabel.setText(latestNotifications.get(4).getContent());
			noticeGroup.setVisible(true);
		}
	}

	@FXML
	public void handleNotice6(ActionEvent event) {
		if (latestNotifications.size() > 5) {
			noticeLabel.setText(latestNotifications.get(5).getContent());
			noticeGroup.setVisible(true);
		}
	}

	@FXML
	public void handleNotice7(ActionEvent event) {
		if (latestNotifications.size() > 6) {
			noticeLabel.setText(latestNotifications.get(6).getContent());
			noticeGroup.setVisible(true);
		}
	}

	@FXML
	public void handleNotice8(ActionEvent event) {
		if (latestNotifications.size() > 7) {
			noticeLabel.setText(latestNotifications.get(7).getContent());
			noticeGroup.setVisible(true);
		}
	}

	// 공지 상세 조회 닫기
	@FXML
	public void handleCloseButton(ActionEvent event) {
		noticeGroup.setVisible(false);
	}

}
