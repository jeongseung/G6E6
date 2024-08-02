package sysone.g6e6.controller;

import java.time.LocalDateTime;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sysone.g6e6.model.Quiz;

public class AdminErrorChildController {
	@FXML
	Label nicknameLabel, emailLabel, dateLabel, typeLabel, countLabel;
	@FXML
	Button quizButton, deleteButton, keepButton;

	private Quiz curQuiz;
	private Integer curId;
	private AdminErrorController curParent;

	public void init(String nickname, String email, LocalDateTime date, String subjectContent, int count, Quiz quiz,
		Integer id,
		AdminErrorController parent) {
		Platform.runLater(() -> {
			System.out.println(nickname);
			nicknameLabel.setText(nickname);
			emailLabel.setText(email);
			dateLabel.setText(date.toLocalDate().toString());
			typeLabel.setText(subjectContent);
			countLabel.setText("" + count);
			curQuiz = quiz;
			curId = id;
			curParent = parent;
		});
	}

	@FXML
	public void handleQuizButton(ActionEvent e) {
		curParent.showQuiz(curQuiz, typeLabel.getText());
	}

	@FXML
	public void handleDeleteButton(ActionEvent e) {
		curParent.deleteQuiz(curQuiz);
	}

	@FXML
	public void handleKeepButton(ActionEvent e) {
		curParent.keepQuiz(curId);
	}
}
