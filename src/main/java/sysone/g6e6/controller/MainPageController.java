package sysone.g6e6.controller;

import java.util.List;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;

import javafx.scene.Group;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sysone.g6e6.util.FXUtil;

public class MainPageController {

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

	private NoticeService noticeService;

	public MainPageController() {
		this.noticeService = new NoticeService();

	}

	public void initialize() {
		List<Notice> latestNotices = noticeService.getLatestNotices(3);

		if (latestNotices.size() > 0) {
			noticeButton1.setText(latestNotices.get(0).getTitle());
			noticeButton1.setVisible(true);
		}

		if (latestNotices.size() > 1) {
			noticeButton2.setText(latestNotices.get(1).getTitle());
			noticeButton2.setVisible(true);
		}

		if (latestNotices.size() > 2) {
			noticeButton3.setText(latestNotices.get(2).getTitle());
			noticeButton3.setVisible(true);
		}
	}

	@FXML
	public void handleNotice1(ActionEvent event) {
		if (latestNotices.size > 0) {
			noticeLabel1.setText(latestNotices.get(0).getContent());
			noticeGroup1.setVisible(true);
		}
	}

	@FXML
	public void handleNotice2(ActionEvent event) {
		if (latestNotices.size > 1) {
			noticeLabel2.setText(latestNotices.get(1).getContent());
			noticeGroup2.setVisible(true);
		}
	}

	@FXML
	public void handleNotice3(ActionEvent event) {
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
	@FXML
	public void handleGoToSelectMode(ActionEvent event) {
		FXUtil fx = FXUtil.getInstance();
		fx.changeScene("게임생성Page");
	}

	// 문제 생성페이지로 이동
	@FXML
	public void handleGoToQuizSubmit(ActionEvent event) {
		FXUtil fx = FXUtil.getInstance();
		fx.changeScene("문제생성Page");
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

}
