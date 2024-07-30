package sysone.g6e6.service;

import java.sql.SQLException;

import sysone.g6e6.model.Notification;
import sysone.g6e6.repository.NotificationRepository;

public class NotificationCreateService {
	private NotificationRepository notificationRepository;

	public NotificationCreateService() {
		this.notificationRepository = new NotificationRepository();
	}

	public void createNotification(int userId, String notificationTitle, String notificationContent) throws
		SQLException {
		Notification notification = new Notification(null, userId, notificationTitle, notificationContent);
		notificationRepository.save(notification);
	}
}
