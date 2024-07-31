package sysone.g6e6.controller;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import sysone.g6e6.model.PlayRecord;
import sysone.g6e6.service.MyPageService;
import sysone.g6e6.util.FXUtil;
import sysone.g6e6.util.UserSession;

public class MyPageController implements Initializable {
	private UserSession userSession = UserSession.getInstance();
	private MyPageService myPageService = new MyPageService();

	@FXML
	ImageView profileImageView;
	@FXML
	GridPane calendarGridPane;
	@FXML
	Label nicknameLabel, emailLabel, calendarTitleLabel, calanderMonthLabel, calendarCountLabel;
	@FXML
	Button previousButton, nextButton;

	private LocalDate curDate = LocalDate.now();
	private int curYear = curDate.getYear();
	private int curMonth = curDate.getMonthValue();
	private DayOfWeek firstDay = curDate.withDayOfMonth(1).getDayOfWeek();
	private boolean isInitiated = false;

	private List<PlayRecord> myMonthlyRecord;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		nextButton.setVisible(false);
		nicknameLabel.setText("닉네임 : " + userSession.getUser().getNickname());
		emailLabel.setText("이메일 : " + userSession.getUser().getEmail());

		// 업데이트 예정
		// Image img = new Image(getClass().getResource("/sysone/g6e6/image/test.jpg").toExternalForm());
		// profileImageView.setImage(img);

		createCalendar();
		isInitiated = true;
	}

	@FXML
	public void handleNextButton(ActionEvent e) {
		curDate = curDate.plus(1, ChronoUnit.MONTHS);
		curYear = curDate.getYear();
		curMonth = curDate.getMonthValue();
		firstDay = curDate.withDayOfMonth(1).getDayOfWeek();
		createCalendar();
	}

	@FXML
	public void handlePreviousButton(ActionEvent e) {
		curDate = curDate.minus(1, ChronoUnit.MONTHS);
		curYear = curDate.getYear();
		curMonth = curDate.getMonthValue();
		firstDay = curDate.withDayOfMonth(1).getDayOfWeek();
		createCalendar();
	}

	private void createCalendar() {
		getMonthlyPlayRecord();
		if (curYear == LocalDate.now().getYear() && curMonth < LocalDate.now().getMonthValue()) {
			nextButton.setVisible(true);
		} else if (curYear == LocalDate.now().getYear() && curMonth == LocalDate.now().getMonthValue()) {
			nextButton.setVisible(false);
		}
		if (!checkPreviousMonth()) {
			previousButton.setVisible(false);
		} else {
			previousButton.setVisible(true);
		}
		calanderMonthLabel.setText(curYear + "년 " + curMonth + "월");
		createMonthlyMap();
	}

	private void getMonthlyPlayRecord() {
		try {
			if (userSession.getUser() != null) {
				myMonthlyRecord = myPageService.createRecordList(userSession.getUser().getId(), curYear, curMonth);
			} else {
				myMonthlyRecord = myPageService.createRecordList(1, curYear, curMonth);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createMonthlyMap() {
		YearMonth yearMonth = YearMonth.of(curYear, curMonth);
		String defaultStyle = "-fx-alignment : center;";
		int daysInMonth = yearMonth.lengthOfMonth();
		int cnt = 0;
		boolean didToday = false;
		for (int i = 0; i < 6 * 7; i++) {
			Label label = getLabelAt(calendarGridPane, i % 7, (i / 7) + 1);
			label.setMaxWidth(Double.MAX_VALUE);
			label.setMaxHeight(Double.MAX_VALUE);
			int today = i + 2 - firstDay.getValue();
			if ((i >= (firstDay.getValue() - 1)) && (i < (firstDay.getValue() - 1 + daysInMonth))) {
				label.setText(today + "");
				label.setStyle(defaultStyle + "-fx-background-color : white;");
				if (checkRecord(today)) {
					label.setStyle(defaultStyle + "-fx-background-color : green");
					cnt++;
				}
			} else {
				label.setText("");
				label.setStyle(defaultStyle + "-fx-background-color : #d3d3d3");
			}
			if (!isInitiated) {
				if ((i + 1) == curDate.getDayOfMonth() && (checkRecord(i + 1))) {
					didToday = true;
				}
				if ((i + 1) < curDate.getDayOfMonth() && (!checkRecord(i + 1))) {
					cnt = 0;
				}
			}
		}
		if (!isInitiated) {
			calendarCountLabel.setText(
				cnt == 0 ? "출석을 위해 게임을 플레이해주세요!" :
					(cnt + "일째 연속 출석 중!" + ((!didToday) && (cnt != 0) ? " 오늘도 게임을 플레이하고 출석을 진행해주세요!" : "")));
		}
	}

	private boolean checkRecord(int curDay) {
		for (PlayRecord record : myMonthlyRecord) {
			if (record.getPlayDate().toLocalDate().equals(LocalDate.of(curYear, curMonth, curDay))) {
				return true;
			}
		}
		return false;
	}

	private boolean checkPreviousMonth() {
		try {
			return myPageService.getPreviousRecords(1, (curMonth - 1) > 0 ? curYear : curYear - 1, curMonth - 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// getLabel
	private Label getLabelAt(GridPane gridPane, int col, int row) {
		for (var node : gridPane.getChildren()) {
			Integer colIndex = GridPane.getColumnIndex(node);
			Integer rowIndex = GridPane.getRowIndex(node);

			// Handle null indices (default to 0)
			if (colIndex == null)
				colIndex = 0;
			if (rowIndex == null)
				rowIndex = 0;

			if (colIndex == col && rowIndex == row && node instanceof Label) {
				return (Label)node;
			}
		}
		return null;
	}

	@FXML
	public void handleReturnButton(ActionEvent e) {
		FXUtil.getInstance().changeScene("MainPage");
	}

}
