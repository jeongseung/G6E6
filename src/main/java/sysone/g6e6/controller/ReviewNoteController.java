package sysone.g6e6.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import sysone.g6e6.model.Quiz;
import sysone.g6e6.model.User;
import sysone.g6e6.service.ReviewNoteService;
import sysone.g6e6.util.FXUtil;
import sysone.g6e6.util.UserSession;

public class ReviewNoteController implements Initializable {

	@FXML
	private ScrollPane scrollPane;
	@FXML
	private VBox contentBox; // VBox로 자식 컴포넌트를 관리
	private double totalHeight = 0;
	private int cnt = 0;
	private ReviewNoteService reviewNoteService = new ReviewNoteService();
	private User user = UserSession.getInstance().getUser();
	int userId = user.getId();

	public List<Quiz> getQuizList(int id) throws Exception {
		return reviewNoteService.getQuizList(id);
	}

	@FXML
	public void initialize(URL url, ResourceBundle resourceBundle) {
		loadQuizzes();
	}

	public void loadQuizzes() {
		contentBox.getChildren().clear();
		contentBox.setSpacing(10); // 컴포넌트 사이에 여백 추가
		Platform.runLater(() -> {
			try {
				//테스트 시 userId말고 번호 입력
				List<Quiz> quizzes = getQuizList(userId);
				for (Quiz quiz : quizzes) {
					FXMLLoader loader = FXUtil.getInstance().getLoader("ReviewNoteChild.fxml");
					AnchorPane childComponent = loader.load();

					ReviewNoteChildController ccc = loader.getController();
					ccc.setParentController(this); // 부모 컨트롤러를 설정
					ccc.SetQuizData(quiz);
					ccc.setLabels(quiz.getProblem(), quiz.getAnswer(), scrollPane.getWidth() - 40);

					ccc.getHeight(height -> {
						Platform.runLater(() -> {
							totalHeight += height + 20; // 컴포넌트 높이와 여백 추가
							contentBox.getChildren().add(childComponent);
							scrollPane.setVvalue(1.0); // 스크롤을 맨 아래로
							cnt++;
						});
					});
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}
}