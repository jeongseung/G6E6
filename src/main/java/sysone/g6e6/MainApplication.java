package sysone.g6e6;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javafx.application.Application;
import javafx.stage.Stage;
import sysone.g6e6.util.DBUtil;
import sysone.g6e6.util.FXUtil;

public class MainApplication extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		System.setProperty("prism.lcdtext", "false");

		FXUtil.init(stage, "LoginPage.fxml", "");
	}

	public static void main(String[] args) {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("src/main/resources/sysone/g6e6/db.properties"));
			String dbURL = prop.getProperty("dbURL");
			String dbID = prop.getProperty("dbID");
			String dbPW = prop.getProperty("dbPW");
			DBUtil.init(dbURL, dbID, dbPW);
		} catch (IOException e) {
			e.printStackTrace();
		}

		launch();
	}
}
