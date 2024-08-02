package sysone.g6e6.controller;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import sysone.g6e6.model.Quiz;
import sysone.g6e6.service.ReviewNoteChildService;
import sysone.g6e6.util.UserSession;

public class ReviewNoteChildController {
	@FXML
	private Label problemLabel, answerLabel;
	@FXML
	private HBox answerPane;
	private Quiz quiz;
	@FXML
	private AnchorPane parentPane;
	private ReviewNoteController parentController;
	private ReviewNoteChildService reviewNoteChildService = new ReviewNoteChildService();

	public void setParentController(ReviewNoteController parentController) {
		this.parentController = parentController;
	}

	public void SetQuizData(Quiz quiz) {
		this.quiz = quiz;
	}

	public void setLabels(String prob, String ans, double parentWidth) {

		double availableWidth = parentWidth - 40;

		problemLabel.setPrefWidth(availableWidth);
		problemLabel.setMaxWidth(availableWidth);

		problemLabel.setText(prob);
		answerLabel.setText(ans);
		// 텍스트가 길어질 경우 높이를 조정
		problemLabel.setWrapText(true);
		problemLabel.applyCss();
		problemLabel.layout();

	}

	@FXML
	public void handleErrorReport() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("신고하기");
		alert.setHeaderText(null);
		alert.setContentText("신고하시겠습니까?");

		// 예, 아니오 버튼을 생성
		ButtonType buttonTypeYes = new ButtonType("예");
		ButtonType buttonTypeNo = new ButtonType("아니오");

		// 버튼을 Alert에 추가
		alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

		// 사용자가 버튼을 누르면 결과를 처리
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == buttonTypeYes) {
			try {
				reviewNoteChildService.addErrorReports(UserSession.getInstance().getUser().getId(), quiz.getId());
				parentController.loadQuizzes();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	@FXML
	public void initialize() {
		// CSS 파일을 Scene에 추가
		Scene scene = parentPane.getScene();
		if (scene != null) {
			scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
		}

		// CSS 클래스를 컨트롤에 적용
		problemLabel.getStyleClass().add("label-custom-font");
		answerLabel.getStyleClass().add("answer-label-custom-font");
		answerPane.getStyleClass().add("answer-pane-style");
	}

	@FXML
	public void HandleScreenButton() {
		answerLabel.setVisible(!answerLabel.isVisible());
	}
}