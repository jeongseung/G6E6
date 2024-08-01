package sysone.g6e6.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;



public class RankingChildController {

    @FXML
    private Label rankLabel;
    @FXML
    private AnchorPane childPane;

    public void initialize() {
        // CSS 파일을 Scene에 추가
        Scene scene = childPane.getScene();
        if (scene != null) {
            scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
        }
    }

    public void setRankLabel(int rank, String nickname, double solveTime) {
        String formattedText = String.format("%-15s %-50s %-15s", rank + ".", nickname, solveTime + " 초");
        rankLabel.setText(formattedText);
        rankLabel.getStyleClass().add("label-custom-font");
    }

    public void setRankColor(int rank) {
        switch (rank) {
            case 1:
                setGradientBackground("#E7BF32");
                break;
            case 2:
                setGradientBackground("#B5B5B5");
                break;
            case 3:
                setGradientBackground("#DDA876");
                break;
            default:
                setGradientBackground("#D2DDF8");
                break;
        }
    }

    public void setGradientBackground(String color) {
        LinearGradient gradient = new LinearGradient(
                0, 0, 1, 0,  // startX, startY, endX, endY
                true,  // proportional
                CycleMethod.NO_CYCLE,  // cycleMethod
                new Stop(0, Color.web(color)),  // start color
                new Stop(1, Color.web("#FFFFFF"))  // end color
        );
        CornerRadii cornerRadii = new CornerRadii(10);
        childPane.setBackground(new Background(new BackgroundFill(
                gradient, cornerRadii, Insets.EMPTY)));
    }
}
