package sysone.g6e6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sysone.g6e6.util.DBUtil;
import sysone.g6e6.util.FXUtil;

import java.io.IOException;

public class MainApplication extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		// FXUtil.init(stage,".fxml",".css");
	}

	public static void main(String[] args) {
		// DBUtil.init("DB_URL","DB_ID","DB_PW");
		// launch();
	}
}
