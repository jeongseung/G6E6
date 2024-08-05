package sysone.g6e6.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import sysone.g6e6.model.Notification;
import sysone.g6e6.util.DBUtil;

public class NotificationRepository {
	private Connection conn = DBUtil.getInstance().getConn();

	public Notification save(Notification notification) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(
			"INSERT INTO NOTIFICATIONS (USER_ID, TITLE, CONTENT, CREATE_DATE) values (?,?,?,?)"
		);
		ps.setInt(1, notification.getUserId());
		ps.setString(2, notification.getTitle());
		ps.setString(3, notification.getContent());
		ps.setTimestamp(4, Timestamp.valueOf(notification.getDate()));
		ps.executeUpdate();
		return notification;
	}

	public List<Notification> getLatestNotifications(int cnt) throws SQLException {
		List<Notification> notifications = new ArrayList<>();
		PreparedStatement ps = conn.prepareStatement(
			"SELECT * FROM(SELECT * FROM NOTIfICATIONS ORDER BY CREATE_DATE DESC) WHERE ROWNUM <= ?");
		ps.setInt(1, cnt);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Notification notification = new Notification(
				rs.getInt("NOTIFICATION_ID"),
				rs.getInt("USER_ID"),
				rs.getString("TITLE"),
				rs.getString("CONTENT"),
				rs.getTimestamp("CREATE_DATE").toLocalDateTime()
			);
			notifications.add(notification);
		}

		return notifications;
	}
}
