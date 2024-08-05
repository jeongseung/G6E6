package sysone.g6e6.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import sysone.g6e6.model.Quiz;
import sysone.g6e6.service.AdminErrorService;
import sysone.g6e6.util.FXUtil;

public class AdminErrorController implements Initializable {
	private AdminErrorService adminErrorService = new AdminErrorService();

	private Map<Integer, Integer> countMap;
	private List<Integer> errorReportIds = new ArrayList<>();
	private List<String> userNicknames = new ArrayList<>();
	private List<String> userEmails = new ArrayList<>();
	private List<LocalDateTime> errorReprotDate = new ArrayList<>();
	private List<String> subjectContents = new ArrayList<>();
	private List<Quiz> errorQuizzes = new ArrayList<>();
	private AnchorPane childBlock;

	@FXML
	AnchorPane errorAnchorPane, quizAnchorPane;
	@FXML
	Label infoLabel, problemLabel, answerLabel;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		Platform.runLater(() -> {
			start();
		});
	}

	private void start() {
		quizAnchorPane.setVisible(false);
		getErrors();
		try {
			for (int i = 0; i < userNicknames.size(); i++) {
				String nickname = userNicknames.get(i);
				String email = userEmails.get(i);
				LocalDateTime date = errorReprotDate.get(i);
				String subjectContent = subjectContents.get(i);
				Quiz quiz = errorQuizzes.get(i);
				Integer id = errorReportIds.get(i);
				FXMLLoader loader = FXUtil.getInstance().getLoader("AdminErrorChild.fxml");
				childBlock = loader.load();
				AdminErrorChildController aecc = loader.getController();
				aecc.init(nickname, email, date, subjectContent, countMap.get(quiz.getId()), quiz, id, this);
				errorAnchorPane.getChildren().add(childBlock);
				childBlock.setLayoutY(40 * i);
				if (40 * (i + 1) > errorAnchorPane.getHeight()) {
					errorAnchorPane.setPrefHeight(40 * i);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getErrors() {
		try {
			HashMap<String, List<?>> errorHashMap = adminErrorService.getAllErrors();
			errorReportIds = (List<Integer>)errorHashMap.get("신고번호");
			userNicknames = (List<String>)errorHashMap.get("닉네임");
			userEmails = (List<String>)errorHashMap.get("이메일");
			errorReprotDate = (List<LocalDateTime>)errorHashMap.get("신고일시");
			subjectContents = (List<String>)errorHashMap.get("유형");
			errorQuizzes = (List<Quiz>)errorHashMap.get("퀴즈");
			countMap = createQuizCountMap(errorQuizzes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Map<Integer, Integer> createQuizCountMap(List<Quiz> quizzes) {
		// 결과를 저장할 HashMap 생성
		Map<Integer, Integer> countMap = new HashMap<>();
		// 배열을 순회하며 각 값의 빈도를 계산
		for (Quiz quiz : quizzes) {
			if (countMap.containsKey(quiz.getId()))
				countMap.put(quiz.getId(), countMap.get(quiz.getId()) + 1);
			else
				countMap.put(quiz.getId(), 1);
		}
		return countMap;
	}

	public void showQuiz(Quiz quiz, String subjectContent) {
		quizAnchorPane.setVisible(true);
		infoLabel.setText("문제 분야 : " + subjectContent);
		problemLabel.setText(quiz.getProblem());
		answerLabel.setText(quiz.getAnswer());
	}

	public void deleteQuiz(Quiz quiz) {
		try {
			adminErrorService.deleteErrorQuiz(quiz.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		reload();
	}

	public void keepQuiz(Integer id) {
		try {
			adminErrorService.revokeErrorQuiz(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		reload();
	}

	@FXML
	public void handleOkButton(ActionEvent e) {
		quizAnchorPane.setVisible(false);
	}

	@FXML
	public void handleReloadButton(ActionEvent e) {
		reload();
	}

	private void reload() {
		errorAnchorPane.getChildren().clear();
		start();
	}

	@FXML
	public void handleReturnButton(ActionEvent e) {
		FXUtil.getInstance().changeScene("AdminMainPage");
	}
}
