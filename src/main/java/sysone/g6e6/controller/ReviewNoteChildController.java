package sysone.g6e6.controller;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import sysone.g6e6.model.Quiz;
import sysone.g6e6.service.ReviewNoteChildService;
import java.util.Optional;
import java.util.function.Consumer;

public class ReviewNoteChildController {
    @FXML
    private Label problemLabel, answerLabel;
    @FXML
    private AnchorPane problemPane;
    @FXML
    private HBox answerPane;
    @FXML
    private VBox parentVBox;

    private Quiz quiz;
    private ReviewNoteController parentController;
    private ReviewNoteChildService reviewNoteChildService = new ReviewNoteChildService();

    public void setParentController(ReviewNoteController parentController) {
        this.parentController = parentController;
    }

    public void SetQuizData(Quiz quiz) {
        this.quiz = quiz;
    }

    public void setLabels(String prob, String ans, double parentWidth) {
        problemLabel.setText(prob);
        answerLabel.setText(ans);

        double availableWidth = parentWidth - 40;
        problemLabel.setPrefWidth(availableWidth);
        problemLabel.setMaxWidth(availableWidth);

        // 텍스트가 길어질 경우 높이를 조정
        problemLabel.setWrapText(true);
        problemLabel.applyCss();
        problemLabel.layout();

        // AnchorPane 높이 설정
        double problemLabelHeight = problemLabel.getHeight();
        problemPane.setPrefHeight(problemLabelHeight + 20); // 여백 포함

        parentVBox.setPrefHeight(problemPane.getPrefHeight() + answerPane.getPrefHeight());
    }

    public void getHeight(Consumer<Double> callback) {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.00001));
        pause.setOnFinished(event -> {
            Platform.runLater(() -> {
                parentVBox.applyCss();
                parentVBox.layout();
                double height = parentVBox.getHeight();
                callback.accept(height);
            });
        });
        pause.play();
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
                reviewNoteChildService.addErrorReports(1, quiz.getId());
                parentController.loadQuizzes();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    @FXML
    public void HandleScreenButton() {
        answerLabel.setVisible(!answerLabel.isVisible());
    }
}