package sysone.g6e6.service;

import java.sql.SQLException;
import java.util.List;

import sysone.g6e6.model.Notification;
import sysone.g6e6.repository.NotificationRepository;

public class NotificationService {
	private NotificationRepository notificationRepository;

	public NotificationService() {
		this.notificationRepository = new NotificationRepository();
	}

	public Notification createNotification(int notificationID, int userId, String notificationTitle,
		String notificationContent) throws
		SQLException {
		Notification notification = new Notification(null, userId, notificationTitle, notificationContent);
		return notificationRepository.save(notification);
	}

	public List<Notification> getLatestNotifications(int cnt) throws SQLException {
		return notificationRepository.getLatestNotifications(cnt);
	}
}
