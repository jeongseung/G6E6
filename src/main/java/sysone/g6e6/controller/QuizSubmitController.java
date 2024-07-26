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
import sysone.g6e6.model.Quiz;
import sysone.g6e6.model.Subject;
import sysone.g6e6.service.QuizService;
import sysone.g6e6.service.SubjectService;

public class QuizSubmitController implements Initializable {
	private QuizService quizService = new QuizService();
	private SubjectService subjectService = new SubjectService();
	@FXML
	private ChoiceBox<String> subjectChoiceBox;
	@FXML
	private TextField answerTextField;
	@FXML
	private TextArea quizTextArea;

	private List<String> subjectArray = new ArrayList<>();

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		try {
			subjectService.getAllSubjects().forEach(subject -> {
				subjectArray.add(subject.getContent());
			});
			subjectChoiceBox.getItems().addAll(subjectArray);
			subjectChoiceBox.setValue("----분야 선택----");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void submitQuiz(ActionEvent event) {
		try {
			Subject subject = subjectService.getSubjectByContent(subjectChoiceBox.getValue());
			Quiz quiz = quizService.createQuiz(subject.getId(), quizTextArea.getText(), answerTextField.getText());
			System.out.println("1 quiz created.\n" + quiz.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
