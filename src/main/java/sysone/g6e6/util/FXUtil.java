package sysone.g6e6.util;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class FXUtil extends Application {
	// 싱글톤을 위한 인스턴스
	private static FXUtil instance;
	// 폴더트리에 맞는 리소스폴더 내부 경로
	private String resourceDir = "/sysone/g6e6/";
	// 프로젝트 절대경로
	private String currentDir;
	// FXML파일의 절대경로
	private String fxmlURL;
	// CSS파일의 절대경로
	private String cssURL;
	// 현재 스테이지
	private Stage stage;

	// 기본 생성자
	private FXUtil(Stage primaryStage, String fxmlName, String cssName) {
		this.currentDir = System.getProperty("user.dir") + "/src/main/resources";
		setFXMLURL(fxmlName);
		setCSSURL(cssName);
		start(primaryStage);
	}

	// 싱글톤 객체 반환 매서드
	public static FXUtil getInstance() {
		if (instance == null) {
			throw new IllegalStateException("FXManager is not initialized.");
		}
		return instance;
	}

	// 싱글톤 초기화 매서드
	public static FXUtil init(Stage stage, String fxmlName, String cssName) {
		if (instance == null) {
			instance = new FXUtil(stage, fxmlName, cssName);
		} else {
			throw new IllegalStateException("Already have instance");
		}
		return instance;
	}

	// 절대 경로를 이용한 파일 유무 확인 매서드
	private boolean isFileExist(String url) {
		url = currentDir + url;
		File f = new File(url);
		if (!f.exists()) {
			System.out.println(url + " not found!");
		}
		return f.exists();
	}

	// CSS파일 경로 수정 매서드
	private void setCSSURL(String cssName) {
		String old = cssURL;
		if (cssName != null && !cssName.startsWith(resourceDir)) {
			cssURL = resourceDir + "css/" + cssName;
		} else {
			cssURL = cssName;
		}
		if (!isFileExist(cssURL)) {
			cssURL = old;
		}
	}

	// FXML파일 경로 수정 매서드
	private void setFXMLURL(String fxmlName) {
		String old = fxmlURL;
		if (fxmlName != null && !fxmlName.startsWith(resourceDir)) {
			fxmlURL = resourceDir + "fxml/" + fxmlName;
		} else {
			fxmlURL = fxmlName;
		}
		if (!isFileExist(fxmlURL)) {
			fxmlURL = old;
		}
	}

	public void exit() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Exit");
		alert.setHeaderText("You are going to exit program!");
		alert.setContentText("Do you want to exit program?");
		if (alert.showAndWait().get() == ButtonType.OK) {
			stage.close();
		}
	}

	// 스테이지 구성 및 초기 화면 설정을 위한 start 매서드 오버라이딩
	@Override
	public void start(Stage primarystage) {
		stage = primarystage;
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlURL));
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);
			if (cssURL != null) {
				String cssSource = getClass().getResource(cssURL).toExternalForm();
				scene.getStylesheets().add(cssSource);
			}
			stage.setScene(scene);
			stage.show();

			stage.setOnCloseRequest(event -> {
				event.consume();
				exit();
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// loader 반환 매서드

	// input
	// 새로 전환될 장면 정보를 담고있는 fxml파일명, 또는 경로를 받는다.

	// output
	// FXMLLoader타입의 loader 객체

	// logic
	// 경로를 정의해서 해당 경로의 값을 읽어온다.
	// 해당 경로의 FXML파일내 리소스를 가져와 FXMLLoader 객체를 생성해 반환한다.
	public FXMLLoader getLoader(String fxmlName) {
		setFXMLURL(fxmlName);
		FXMLLoader loader = null;
		if (fxmlURL != null) {
			loader = new FXMLLoader(getClass().getResource(fxmlURL));
		}
		return loader;
	}

	// 장면 전환 매서드

	// input
	// 이벤트 리스너가 받은 이벤트, -- 수정완료, 이제는 필요없다.
	// 앞서 받은 loader에서 loader.load()를 통해 받은 Parent root 객체,
	// css가 존재한다면, css의 위치, 또는 .css파일명을 받는다.

	// output
	// void

	// logic
	// 로더에서 얻은 다음 장면 정보에 대해서 추가적인 작업이 있다면(다른 컨트롤러에 접근 등)
	// 해당 작업을 수행후, 로드된 정보를 root로 만들고, 이를 토대로 새로운 장면을 만든다.
	// 이후에, 현재 보여주고있는 스테이지(실행창)에서 새로운 장면을 보여준다.
	public void changeScene(Parent root) {
		changeScene(root, cssURL);
	}

	// css를 추가적으로 받는 오버로딩
	public void changeScene(Parent root, String cssName) {
		setCSSURL(cssName);
		Scene scene = new Scene(root);
		if (cssURL != null) {
			String cssSource = this.getClass().getResource(cssURL).toExternalForm();
			scene.getStylesheets().add(cssSource);
		}
		// 이번 프로젝트에서 stage는 변하지 않기에 추가적인 정의 없이 이용가능하다.
		// 만약 여러개의 스테이지가 존재하는 프로젝트에서는 아래의 주석코드와, 매개변수로 ActionEvent를 받아오면 된다.
		// Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	public void changeScene(String fxmlName) {
		FXUtil fx = FXUtil.getInstance();
		FXMLLoader loader = fx.getLoader(fxmlName + ".fxml");
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
