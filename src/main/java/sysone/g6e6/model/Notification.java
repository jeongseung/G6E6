package sysone.g6e6.model;

import java.time.LocalDateTime;

public class Notification {
	private Integer id;
	private int userId;
	private String title;
	private String content;
	private LocalDateTime date;

	public Notification(Integer id, int userId, String title, String content) {
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.date = LocalDateTime.now();
	}

	public Notification(Integer id, int userId, String title, String content, LocalDateTime date) {
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public int getUserId() {
		return userId;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Notification [id=" + id +
			", userId=" + userId +
			", title=" + title +
			", content=" + content +
			", date=" + date +
			"]";
	}

}
