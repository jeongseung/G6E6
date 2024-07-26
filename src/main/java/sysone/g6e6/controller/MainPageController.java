package sysone.g6e6.controller;

import java.awt.*;
import java.awt.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import sysone.g6e6.util.FXUtil;

public class MainPageController {

	@FXML
	private Label welcomeLabel;
	@FXML
	private Button handelNotice1;
	@FXML
	private Button handelNotice2;
	@FXML
	private Button handelNotice3;
	@FXML
	private Group noticeGroup1;
	@FXML
	private Group noticeGroup2;
	@FXML
	private Group noticeGroup3;
	@FXML
	private Button handelGoToSelectionMode;
	@FXML
	private Button handleGoToQuizSubmit;
	@FXML
	private Button handleGoToMyPage;
	@FXML
	private Button handleGoToReviewNote;
	@FXML
	private Button handleGoToRanking;

	private NoticeService noticeService;

	public MainPageController() {
		this.noticeService = new NoticeService();
	}

	public void initailize() {
		List<Notice> latestNotices = noticeService.getLatestNotices(3);

		if (latestNotices.size() > 0) {
			handleNotice1.setText(latestNotices.get(0).getTitle());
			handleNotice1.setVisible(true);
		}

		if (latestNotices.size() > 1) {
			handleNotice2.setText(latestNotices.get(1).getTitle());
			handleNotice2.setVisible(true);
		}

		if (latestNotices.size() > 2) {
			handleNotice3.setText(latestNotices.get(2).getTitle());
			handleNotice3.setVisible(true);
		}
	}

	private void handleNotice1(ActionEvent event) {
		if (latestNotices.size > 0) {
			noticeLabel1.setText(latestNotices.get(0).getContent());
			noticeGroup1.setVisible(true);
		}
	}

	private void handleNotice2(ActionEvent event) {
		if (latestNotices.size > 1) {
			noticeLabel2.setText(latestNotices.get(1).getContent());
			noticeGroup2.setVisible(true);
		}
	}

	private void handleNotice3(ActionEvent event) {
		if (latestNotices.size > 2) {
			noticeLabel3.setText(latestNotices.get(2).getContent());
			noticeGroup3.setVisible(true);
		}
	}

	// 유저 닉네임을 설정하는 메서드
	public void setUserNickname(String nickname) {
		welcomeLabel.setText("환영합니다 " + nickname + "님");
	}

	// 게임생성페이지로 이동
	private void handleGoToSelectMode(ActionEvent event) {
		FXUtil fx = FXUtil.getInstance();
		FXMLLoader loader = fx.getLoader("게임생성Page.fxml");
		Parent root = null;
		try {
			root = loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		fx.changeScene(root);
	}

	// 문제 생성페이지로 이동
	private void handleGoToQuizSubmit(ActionEvent event) {
		FXUtil fx = FXUtil.getInstance();
		FXMLLoader loader = fx.getLoader("QuizSubmitPage.fxml");
		Parent root = null;
		try {
			root = loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		fx.changeScene(root);
	}

	// 마이페이지로 이동
	private void handleGoToMyPage(ActionEvent event) {
		FXUtil fx = FXUtil.getInstance();
		FXMLLoader loader = fx.getLoader("MyPage.fxml");
		Parent root = null;
		try {
			root = loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		fx.changeScene(root);
	}

	// 오답노트 페이지로 이동
	private void handleGoToReviewNote(ActionEvent event) {
		FXUtil fx = FXUtil.getInstance();
		FXMLLoader loader = fx.getLoader("ReviewNotePage.fxml");
		Parent root = null;
		try {
			root = loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		fx.changeScene(root);
	}

	// 랭킹페이지로 이동
	private void handleGoToRanking(ActionEvent event) {
		FXUtil fx = FXUtil.getInstance();
		FXMLLoader loader = fx.getLoader("RankingPage.fxml");
		Parent root = null;
		try {
			root = loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		fx.changeScene(root);
	}

}
