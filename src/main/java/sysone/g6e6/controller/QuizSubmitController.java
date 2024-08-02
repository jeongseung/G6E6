package sysone.g6e6.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import sysone.g6e6.model.Quiz;
import sysone.g6e6.model.Subject;
import sysone.g6e6.service.QuizSubmitService;
import sysone.g6e6.util.FXUtil;

public class QuizSubmitController implements Initializable {
	private QuizSubmitService quizSubmitService = new QuizSubmitService();
	@FXML
	private ChoiceBox<String> subjectChoiceBox;
	@FXML
	private TextField answerTextField;
	@FXML
	private TextArea quizTextArea;

	private String fontPath = getClass().getResource("/sysone/g6e6/fonts/Pretendard-Regular.ttf").toExternalForm();
	private Font customFont = Font.loadFont(fontPath, 20);

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		answerTextField.setFont(customFont);
		quizTextArea.setFont(customFont);
		List<String> subjectContentList = new ArrayList<>();
		subjectChoiceBox.setValue("----분야 선택----");
		try {
			subjectContentList = quizSubmitService.getAllSubjects();
			subjectChoiceBox.getItems().addAll(subjectContentList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void submitQuiz(ActionEvent event) {
		try {
			Subject subject = quizSubmitService.getSubjectByContent(subjectChoiceBox.getValue());
			Quiz quiz = quizSubmitService.createQuiz(subject.getId(), quizTextArea.getText(),
				answerTextField.getText());
			System.out.println("1 quiz created.\n" + quiz.toString());
			// 제출 성공 알림 뜨면 좋을 듯
			FXUtil.getInstance().changeScene("MainPage");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void handleReturnButton(ActionEvent e) {
		FXUtil.getInstance().changeScene("MainPage");
	}
}
