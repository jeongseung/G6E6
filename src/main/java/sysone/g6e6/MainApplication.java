package sysone.g6e6;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sysone.g6e6.util.DBUtil;
import sysone.g6e6.util.FXUtil;

public class MainApplication extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		System.setProperty("prism.lcdtext", "false");
		stage.setTitle("G6E6");
		stage.setResizable(false);
		Image icon = new Image(getClass().getResource("/sysone/g6e6/image/icon.png").toString());
		stage.getIcons().add(icon);
		FXUtil.init(stage, "LoginPage.fxml", "app.css");
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
