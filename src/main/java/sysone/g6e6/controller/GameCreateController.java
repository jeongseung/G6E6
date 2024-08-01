package sysone.g6e6.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import sysone.g6e6.service.GameCreateService;
import sysone.g6e6.util.FXUtil;

public class GameCreateController implements Initializable {
	private GameCreateService gameCreateService = new GameCreateService();
	@FXML
	private ChoiceBox<String> gameCreateChoiceBox1, gameCreateChoiceBox2;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		List<String> subjectContentList;
		gameCreateChoiceBox1.setValue("----분야 선택----");
		gameCreateChoiceBox2.setValue("----난이도 선택----");
		gameCreateChoiceBox2.getItems().addAll("쉬움", "보통", "어려움");
		try {
			subjectContentList = gameCreateService.getAllSubjects();
			gameCreateChoiceBox1.getItems().addAll(subjectContentList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void startGame(ActionEvent e) {
		try {
			Exception ex = new Exception("didn't choose difficulty");
			String subject_name = gameCreateChoiceBox1.getValue();
			String diff = gameCreateChoiceBox2.getValue();
			if (diff == "---난이도 선택---" || subject_name == "----분야 선택----") {
				System.out.println("제대로 골라주세요");
				throw ex;
			}
			FXMLLoader loader = FXUtil.getInstance().getLoader("GameScreen.fxml");
			Parent root = loader.load();
			GameScreenController gsc = loader.getController();
			gsc.init(subject_name, diff);
			FXUtil.getInstance().changeScene(root);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	public void handleReturnButton(ActionEvent e) {
		FXUtil.getInstance().changeScene("MainPage");
	}
}
