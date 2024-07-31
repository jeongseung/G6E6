package sysone.g6e6.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import sysone.g6e6.service.RankingService;
import sysone.g6e6.util.FXUtil;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class RankingController implements Initializable {

    @FXML
    private AnchorPane parentPane;
    @FXML
    private Label hideSubjectLabel;
    @FXML
    private Label subjectLabel1;
    @FXML
    private Label subjectLabel2;
    @FXML
    private Label hideDifficultyLabel;
    @FXML
    private Label difficultyLabel1;
    @FXML
    private Label difficultyLabel2;
    @FXML
    private Label difficultyLabel3;

    private Label selectedSubjectLabel;
    private Label selectedDifficultyLabel;

    private String selectedSubjectContent;
    private String selectedDifficulty;

    private RankingService rankingService = new RankingService();
    private List<Map<String, Double>> response;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hideSubjectLabel.setVisible(true);
        hideDifficultyLabel.setVisible(true);

        subjectLabel1.setOnMouseClicked(event -> handleSubjectSelection(subjectLabel1));
        subjectLabel2.setOnMouseClicked(event -> handleSubjectSelection(subjectLabel2));

        difficultyLabel1.setOnMouseClicked(event -> handleDifficultySelection(difficultyLabel1));
        difficultyLabel2.setOnMouseClicked(event -> handleDifficultySelection(difficultyLabel2));
        difficultyLabel3.setOnMouseClicked(event -> handleDifficultySelection(difficultyLabel3));
    }

    private void handleSubjectSelection(Label subjectLabel) {
        if (selectedSubjectLabel != null) {
            selectedSubjectLabel.setStyle("-fx-background-color: transparent;");
        }
        selectedSubjectLabel = subjectLabel;
        selectedSubjectContent = subjectLabel.getText();
        subjectLabel.setStyle("-fx-background-color: rgba(128, 128, 128, 0.5);"); //
        hideSubjectLabel.setVisible(false);
        checkSelections();
    }

    private void handleDifficultySelection(Label difficultyLabel) {
        if (selectedDifficultyLabel != null) {
            selectedDifficultyLabel.setStyle("-fx-background-color: transparent;");
        }
        selectedDifficultyLabel = difficultyLabel;
        selectedDifficulty = difficultyLabel.getText();
        difficultyLabel.setStyle("-fx-background-color: rgba(128, 128, 128, 0.5);"); //
        hideDifficultyLabel.setVisible(false);
        checkSelections();
    }

    private void checkSelections() {
        if (selectedSubjectContent == null) {
            hideSubjectLabel.setVisible(true);
        }
        if (selectedDifficulty == null) {
            hideDifficultyLabel.setVisible(true);
        }

        if (selectedDifficulty != null && selectedSubjectContent != null) {
            try {
                parentPane.getChildren().clear();

                response = rankingService.getNicknameTime(selectedDifficulty, selectedSubjectContent);

                int cnt = 1;
                for (Map<String, Double> map : response) {
                    for (Map.Entry<String, Double> entry : map.entrySet()) {
                        String nickname = entry.getKey();
                        Double solveTime = entry.getValue();
                        FXMLLoader loader = FXUtil.getInstance().getLoader("RankingChild.fxml");
                        AnchorPane childPane = loader.load();
                        RankingChildController rcc = loader.getController();
                        rcc.setRankLabel(cnt, nickname, solveTime);
                        rcc.setRankColor(cnt);
                        parentPane.getChildren().add(childPane);
                        childPane.setLayoutX(20);
                        childPane.setLayoutY(50 * (cnt - 1) + 5);
                        cnt++;
                    }
                }
                parentPane.setPrefHeight(50 * response.size() + 10);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}