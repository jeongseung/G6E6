package sysone.g6e6.service;

import java.sql.SQLException;
import java.util.List;

import sysone.g6e6.model.Notification;
import sysone.g6e6.repository.NotificationRepository;

public class AdminMainService {
	private NotificationRepository notificationRepository;

	public AdminMainService() {
		this.notificationRepository = new NotificationRepository();
	}

	public List<Notification> getLatestNotifications(int cnt) throws SQLException {
		return notificationRepository.getLatestNotifications(cnt);
	}
}
