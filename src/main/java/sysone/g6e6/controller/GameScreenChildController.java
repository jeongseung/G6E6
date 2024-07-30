package sysone.g6e6.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class GameScreenChildController {

	@FXML
	private AnchorPane childAnchorPane;

	public void init(int num, String problem, boolean isVertical) {
		String idx = (isVertical ? "세로" : "가로") + num + "번.";
		TextFlow idxTextFlow = new TextFlow(new Text(idx));
		TextFlow probTextFlow = new TextFlow(new Text(problem));
		idxTextFlow.setPrefWidth(200);
		probTextFlow.setPrefWidth(200);

		// 임시 AnchorPane에 추가하여 레이아웃 강제 실행
		AnchorPane tempPane = new AnchorPane(idxTextFlow, probTextFlow);
		tempPane.setPrefWidth(210);

		// 실제로 레이아웃을 강제 실행하기 위해 장면에 추가
		childAnchorPane.getChildren().add(tempPane);

		// 레이아웃 강제 실행
		tempPane.layout();

		// 높이 계산
		double idxTextFlowHeight = idxTextFlow.getBoundsInLocal().getHeight();
		double probTextFlowHeight = probTextFlow.getBoundsInLocal().getHeight();

		// 최종 AnchorPane 높이 설정
		childAnchorPane.setPrefHeight(idxTextFlowHeight + probTextFlowHeight + 20); // 여유 공간 추가

		// 기존 자식 제거
		childAnchorPane.getChildren().clear();

		// TextFlow 객체를 AnchorPane에 추가하고 위치 설정
		childAnchorPane.getChildren().addAll(idxTextFlow, probTextFlow);
		AnchorPane.setTopAnchor(idxTextFlow, 10.0); // 상단 여백
		AnchorPane.setTopAnchor(probTextFlow, idxTextFlowHeight + 20); // idxTextFlow 아래에 배치

		// System.out.println("Index TextFlow Height: " + idxTextFlowHeight);
		// System.out.println("Problem TextFlow Height: " + probTextFlowHeight);
		// System.out.println("AnchorPane Height: " + childAnchorPane.getPrefHeight());
	}

	// AnchorPane의 높이를 반환하는 메서드
	public double getAnchorPaneHeight() {
		return childAnchorPane.getPrefHeight();
	}
}
