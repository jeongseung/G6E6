package sysone.g6e6.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	private static DBUtil instance;
	private Connection conn;
	private String dbURL;
	private String dbID;
	private String dbPW;

	private DBUtil(String dbURL, String dbID, String dbPW) {
		this.dbURL = dbURL;
		this.dbID = dbID;
		this.dbPW = dbPW;
		connectDB();
	}

	public static DBUtil getInstance() {
		if (instance == null) {
			throw new IllegalStateException("DBManager is not initialized.");
		}
		return instance;
	}

	public static DBUtil init(String dbURL, String dbID, String dbPW) {
		if (instance == null) {
			instance = new DBUtil(dbURL, dbID, dbPW);
		} else {
			throw new IllegalStateException("Already have instance");
		}
		return instance;
	}

	private void connectDB() {
		if (conn == null) {
			try {
				conn = DriverManager.getConnection(dbURL, dbID, dbPW);
			} catch (SQLException e) {
				System.out.println(
					"not valid connection.\nyour url: " + dbURL + "\nyour id: " + dbID + "\nyour pw: " + dbPW);
			}
		}
	}

	// 데이터베이스 연결 객체 반환
	public Connection getConn() {
		connectDB();
		return conn;
	}
}
