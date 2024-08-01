package sysone.g6e6.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import sysone.g6e6.model.Notification;
import sysone.g6e6.model.User;
import sysone.g6e6.service.MainPageService;
import sysone.g6e6.util.FXUtil;
import sysone.g6e6.util.UserSession;

public class MainPageController implements Initializable {

	@FXML
	private Text noNotiText;
	@FXML
	private Label welcomeLabel;
	@FXML
	private Label noticeLabel;
	@FXML
	private Button noticeButton1;
	@FXML
	private Button noticeButton2;
	@FXML
	private Button noticeButton3;
	@FXML
	private Group noticeGroup;

	private MainPageService mainPageService;
	private List<Notification> latestNotifications;

	public MainPageController() {
		this.mainPageService = new MainPageService();

	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		User user = UserSession.getInstance().getUser();
		String nickname = user.getNickname();
		welcomeLabel.setText("환영합니다 " + nickname + "님");

		try {
			latestNotifications = mainPageService.getLatestNotifications(3);
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

	// 게임생성페이지로 이동
	@FXML
	public void handleGoToSelectMode(ActionEvent event) {
		FXUtil fx = FXUtil.getInstance();
		fx.changeScene("GameCreatePage");
	}

	// 문제 생성페이지로 이동
	@FXML
	public void handleGoToQuizSubmit(ActionEvent event) {
		FXUtil fx = FXUtil.getInstance();
		fx.changeScene("QuizSubmitPage");
	}

	// 마이페이지로 이동
	@FXML
	public void handleGoToMyPage(ActionEvent event) {
		FXUtil fx = FXUtil.getInstance();
		fx.changeScene("MyPage");
	}

	// 오답노트 페이지로 이동
	@FXML
	public void handleGoToReviewNote(ActionEvent event) {
		FXUtil fx = FXUtil.getInstance();
		fx.changeScene("ReviewNotePage");
	}

	// 랭킹페이지로 이동
	@FXML
	public void handleGoToRanking(ActionEvent event) {
		FXUtil fx = FXUtil.getInstance();
		fx.changeScene("RankingPage");
	}

	// 공지 상세 조회 닫기
	@FXML
	public void handleCloseButton(ActionEvent event) {
		noticeGroup.setVisible(false);
	}

}
