package sysone.g6e6.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class GameScreenChildController {

	@FXML
	private AnchorPane childAnchorPane;

	private String fontPath = getClass().getResource("/sysone/g6e6/fonts/Pretendard-Regular.ttf").toExternalForm();
	private Font customFont = Font.loadFont(fontPath, 20);

	public void init(int num, String problem, boolean isVertical) {
		String idx = (isVertical ? "세로" : "가로") + num + "번.\n";

		Text idxText = new Text(idx);
		idxText.setFont(customFont);
		Text problemText = new Text(problem);
		problemText.setFont(customFont);

		TextFlow idxTextFlow = new TextFlow(idxText);
		TextFlow probTextFlow = new TextFlow(problemText);

		idxTextFlow.setStyle("-fx-font-size:20px");
		probTextFlow.setStyle("-fx-font-size:20px");
		idxTextFlow.setPrefWidth(550);
		probTextFlow.setPrefWidth(550);

		// Apply CSS and layout updates
		childAnchorPane.getChildren().addAll(idxTextFlow, probTextFlow);
		childAnchorPane.applyCss();
		childAnchorPane.layout();

		// Height calculation
		double idxTextFlowHeight = idxTextFlow.getBoundsInLocal().getHeight();
		double probTextFlowHeight = probTextFlow.getBoundsInLocal().getHeight();
		double totalHeight = idxTextFlowHeight + probTextFlowHeight + 40; // Adding padding

		childAnchorPane.setPrefHeight(totalHeight);

		// Positioning the TextFlows
		AnchorPane.setTopAnchor(idxTextFlow, 20.0);
		AnchorPane.setLeftAnchor(idxTextFlow, 25.0);
		AnchorPane.setTopAnchor(probTextFlow, idxTextFlowHeight + 20);
		AnchorPane.setLeftAnchor(probTextFlow, 25.0);
	}

	// AnchorPane's height getter method
	public double getAnchorPaneHeight() {
		return childAnchorPane.getPrefHeight();
	}
}
