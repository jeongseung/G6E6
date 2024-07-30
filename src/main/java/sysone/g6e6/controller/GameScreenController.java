package sysone.g6e6.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import sysone.g6e6.model.Quiz;
import sysone.g6e6.model.Subject;
import sysone.g6e6.repository.SubjectRepository;
import sysone.g6e6.service.GameScreenService;
import sysone.g6e6.util.FXUtil;
import sysone.g6e6.util.UserSession;

public class GameScreenController {
	/*
	TODO:
	오답시  땡 레이블안뜨는거 수정
	다 맞출시 화면 보이고 db넘기는거 추가
	일시정지에서 뜰 화면 고민해보기
	로직수정(가로)_
	음향효과 넣기
	 */
	@FXML
	private GridPane gameGridPane;
	@FXML
	private TextField guessTextField;
	@FXML
	private AnchorPane baseAnchorPane, vQuizAnchorPane, hQuizAnchorPane;
	@FXML
	private Label announcementLabel, timerLabel, endInfoLabel, pauseInfoLabel, clearTimeLabel, accuracyLabel;
	@FXML
	private StackPane startStackPane, endStackPane, pauseStackPane;

	private SubjectRepository subjectRepository = new SubjectRepository();
	private String subject_name, diff;
	private int subject_id, total_num;
	private GameScreenService gameScreenService = new GameScreenService();
	private List<Quiz> vQuiz, hQuiz;
	private List<int[]> vCoord, hCoord;

	// user-interact
	private AnchorPane curChildBlock;
	private int[] curCoord;
	private Quiz curQuiz;
	private boolean curBool;
	private List<Label> correctLabel = new ArrayList<>();
	private int correctCount = 0;
	private int tryCount = 0;
	private List<Quiz> mistakeQuiz = new ArrayList<>();

	public void init(String subject_name, String diff) {
		this.subject_name = subject_name;
		this.diff = diff;
		total_num = (diff == "쉬움" ? 6 : diff == "보통" ? 12 : 25);
		baseAnchorPane.getChildren().removeAll(announcementLabel, startStackPane, endStackPane, pauseStackPane);
		gameGridPane.setStyle("-fx-background-color:Skyblue");
		try {
			Subject subject = subjectRepository.findByContent(subject_name);
			subject_id = subject.getId();
			HashMap<String, List<?>> result = gameScreenService.createGame(subject_id, total_num);
			vQuiz = (List<Quiz>)result.get("세로퀴즈");
			hQuiz = (List<Quiz>)result.get("가로퀴즈");
			vCoord = (List<int[]>)result.get("세로좌표");
			hCoord = (List<int[]>)result.get("가로좌표");
			createMap(vQuiz, vCoord, true);
			createMap(hQuiz, hCoord, false);

			timeline = new Timeline(
				new KeyFrame(Duration.seconds(1), e -> updateTimer())
			);
			timeline.setCycleCount(Timeline.INDEFINITE);
			timerLabel.setText("00:00:00");
			startTimer();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void createMap(List<Quiz> quizzes, List<int[]> coords, boolean isVertical) {
		Label label;
		int[] coord;
		String answer;
		for (int i = 0; i < quizzes.size(); i++) {
			coord = coords.get(i);
			answer = quizzes.get(i).getAnswer();
			for (int j = 0; j < answer.length(); j++) {
				label = getLabelAt(gameGridPane, coord[1] + (isVertical ? 0 : j), coord[0] + (isVertical ? j : 0));
				if (label != null) {
					label.setMaxWidth(Double.MAX_VALUE);
					label.setMaxHeight(Double.MAX_VALUE);
					label.setStyle("-fx-background-color:White;"
						+ "-fx-text-alignment:center;"
						+ "-fx-text-fill : #D3D3D3");
					label.setAlignment(Pos.CENTER);
					// label.setText("");
					label.setText("" + answer.charAt(j));// flag : 실제 문제에서는 위 코드로 변경해야함
				}
			}
			createChild(i + 1, quizzes.get(i).getProblem(), isVertical);
		}
	}

	private double vTotalHeight = 0;
	private double hTotalHeight = 0;

	// showProblem
	private void createChild(int number, String problem, boolean isVertical) {
		try {
			FXMLLoader loader = FXUtil.getInstance().getLoader("GameScreenChild.fxml");
			AnchorPane childBlock = loader.load();
			GameScreenChildController gsc = loader.getController();
			gsc.init(number, problem, isVertical);
			childBlock.setOnMouseClicked(e -> {
				System.out.println("clicked");
				handleBlockOnClick(e, childBlock, number, isVertical);
			});
			double childHeight = gsc.getAnchorPaneHeight();
			if (isVertical) {
				vQuizAnchorPane.getChildren().add(childBlock);
				childBlock.setLayoutY(vTotalHeight + 10);
				vTotalHeight += (childHeight + 10);
				double curH = vQuizAnchorPane.getHeight();
				if (vTotalHeight > curH) {
					vQuizAnchorPane.setPrefHeight(vTotalHeight);
				}

			} else {
				hQuizAnchorPane.getChildren().add(childBlock);
				childBlock.setLayoutY(hTotalHeight + 10);
				hTotalHeight += (childHeight + 10);
				double curH = hQuizAnchorPane.getHeight();
				if (hTotalHeight > curH) {
					hQuizAnchorPane.setPrefHeight(hTotalHeight);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// onClickEvent

	private void handleBlockOnClick(MouseEvent e, AnchorPane childBlock, int number, boolean isVertical) {
		Label label;
		if (curCoord != null) {
			for (int n = 0; n < curQuiz.getAnswer().length(); n++) {
				label = getLabelAt(gameGridPane, curCoord[1] + (curBool ? 0 : n),
					curCoord[0] + (curBool ? n : 0));
				label.setStyle(
					"-fx-background-color : white;" + "-fx-text-fill : #C0C0C0");

			}
		}
		curChildBlock = childBlock;
		curCoord = isVertical ? vCoord.get(number - 1) : hCoord.get(number - 1);
		curQuiz = isVertical ? (vQuiz.get(number - 1)) : (hQuiz.get(number - 1));
		curBool = isVertical;
		setCorrectLabelColor();
		for (int n = 0; n < (isVertical ? (vQuiz.get(number - 1).getAnswer().length()) :
			(hQuiz.get(number - 1).getAnswer().length())); n++) {
			label = getLabelAt(gameGridPane, curCoord[1] + (isVertical ? 0 : n), curCoord[0] + (isVertical ? n : 0));
			label.setStyle("-fx-background-color : #C0C0C0; -fx-text-fill : "
				+ (correctLabel.contains(label) ? "BLACK" : "#C0C0C0"));
		}
	}

	@FXML
	private void handleGuessButton(ActionEvent e) {
		Label label;
		String announcement = "퀴즈를 먼저 선택해주세요";
		int ann_typ = 3;
		if (curQuiz != null) {
			tryCount++;
			if (guessTextField.getText().toUpperCase().equals(curQuiz.getAnswer())) {
				announcement = "정답입니다!";
				for (int n = 0; n < curQuiz.getAnswer().length(); n++) {
					label = getLabelAt(gameGridPane, curCoord[1] + (curBool ? 0 : n),
						curCoord[0] + (curBool ? n : 0));
					correctLabel.add(label);
					label.setText("" + curQuiz.getAnswer().charAt(n));
				}
				setCorrectLabelColor();
				curChildBlock.setStyle("-fx-background-color : #ADFF2F");
				curChildBlock.setOnMouseClicked(event -> {
					showAnnounce("이미 완료한 퀴즈입니다!");
				});
				curChildBlock = null;
				curCoord = null;
				curQuiz = null;
				correctCount++;
				ann_typ = 1;
			} else {
				if (!mistakeQuiz.contains(curQuiz)) {
					mistakeQuiz.add(curQuiz);
				}
				announcement = "땡!";
				ann_typ = 2;
			}
		}
		if (correctCount != total_num) {
			showAnnounce(announcement, ann_typ);
			guessTextField.setText("");
		} else {
			showResult();
		}
	}

	private void setCorrectLabelColor() {
		for (Label label : correctLabel) {
			label.setStyle("-fx-background-color : #ADFF2F");
		}
	}

	private void showAnnounce(String announcement) {
		showAnnounce(announcement, 3);
	}

	private void showAnnounce(String announcement, int ann_typ) {
		if (!baseAnchorPane.getChildren().contains(announcementLabel)) {
			announcementLabel.setText(announcement);
			String css = "-fx-font-size : 30; -fx-alignment:center; -fx-text-fill : "
				+ (ann_typ == 1 ? "Green" : ann_typ == 2 ? "Red" : "Black");
			announcementLabel.setStyle(css);
			announcementLabel.setOpacity(1.0);
			PauseTransition visiblePause = new PauseTransition(Duration.seconds(1));
			FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), announcementLabel);
			fadeOut.setFromValue(1.0);
			fadeOut.setToValue(0.0);
			SequentialTransition sequentialTransition = new SequentialTransition(
				visiblePause,
				fadeOut
			);
			sequentialTransition.setOnFinished(event -> baseAnchorPane.getChildren().remove(announcementLabel));
			baseAnchorPane.getChildren().add(announcementLabel);
			sequentialTransition.play();
		}
	}

	// getLabel
	private Label getLabelAt(GridPane gridPane, int col, int row) {
		for (var node : gridPane.getChildren()) {
			Integer colIndex = GridPane.getColumnIndex(node);
			Integer rowIndex = GridPane.getRowIndex(node);

			// Handle null indices (default to 0)
			if (colIndex == null)
				colIndex = 0;
			if (rowIndex == null)
				rowIndex = 0;

			if (colIndex == col && rowIndex == row && node instanceof Label) {
				return (Label)node;
			}
		}
		return null;
	}

	// 시간
	private Timeline timeline;
	private int elapsedTime = 0;
	private boolean isPaused = false;

	public void startTimer() {
		if (timeline != null && !timeline.getStatus().equals(Timeline.Status.RUNNING)) {
			timeline.play();
		}
	}

	private void updateTimer() {
		elapsedTime++; // 경과 시간 1초 증가

		// 경과 시간 초를 시:분:초 형식으로 변환
		long hours = elapsedTime / 3600;
		long minutes = (elapsedTime % 3600) / 60;
		long seconds = elapsedTime % 60;

		String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
		timerLabel.setText(timeString);
	}

	private void pauseTimer() {
		if (timeline != null && timeline.getStatus().equals(Timeline.Status.RUNNING)) {
			timeline.pause();
			isPaused = true;
		}
	}

	@FXML
	public void resumeTimer() {
		if (timeline != null && isPaused) {
			timeline.play();
			isPaused = false;
		}
	}

	// 일시정지 버튼
	@FXML
	public void handlePauseButton(ActionEvent e) {
		baseAnchorPane.getChildren().add(pauseStackPane);
		pauseInfoLabel.setText("종목 : " + subject_name + "\n" + "난이도 : " + diff + "\n\n게임을 정말 종료하실건가요?");
		pauseInfoLabel.setStyle("-fx-text-align : center; -fx-font-size : 15");
		pauseTimer();
	}

	@FXML
	public void handleResumeButton(ActionEvent e) {
		baseAnchorPane.getChildren().remove(pauseStackPane);
		resumeTimer();
	}

	@FXML
	public void handleExitButton(ActionEvent e) {
		FXUtil.getInstance().changeScene("gameCreatePage");
	}

	private void showResult() {
		baseAnchorPane.getChildren().add(endStackPane);
		endInfoLabel.setText("종목 : " + subject_name + "\n" + "난이도 : " + diff);
		endInfoLabel.setStyle("-fx-text-align : center; -fx-font-size : 15");
		clearTimeLabel.setText("\n" + timerLabel.getText());
		clearTimeLabel.setStyle("-fx-align : center; -fx-font-size : 15");
		accuracyLabel.setText("\n정확도 : " + String.format("%.2f", (((double)correctCount / tryCount) * 100.)) + "%");
		accuracyLabel.setStyle("-fx-align : center; -fx-font-size : 15");

		try {
			// 화면 연결이후에 아래 userId =1 고치기
			if (UserSession.getInstance().getUser() != null) {
				gameScreenService.createMistakes(mistakeQuiz, UserSession.getInstance().getUser().getId(), subject_id);
				gameScreenService.createPlayRecord(UserSession.getInstance().getUser().getId(), subject_id, diff,
					elapsedTime);
			} else {
				gameScreenService.createMistakes(mistakeQuiz, 1, subject_id);
				gameScreenService.createPlayRecord(1, subject_id, diff,
					elapsedTime);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
