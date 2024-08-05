module sysone.g6e6 {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires java.desktop;
	requires com.oracle.database.jdbc;

	opens sysone.g6e6 to javafx.fxml;
	exports sysone.g6e6;
	exports sysone.g6e6.controller;
	opens sysone.g6e6.controller to javafx.fxml;
}
