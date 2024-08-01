package sysone.g6e6.model;

import java.time.LocalDateTime;

public class UserErrorReport {
	private Integer id;
	private int userId;
	private int errorId;
	private LocalDateTime reportDate;

	public UserErrorReport(Integer id, int userId, int errorId, LocalDateTime reportDate) {
		this.id = id;
		this.userId = userId;
		this.errorId = errorId;
		this.reportDate = reportDate;
	}

	@Override
	public String toString() {
		return "UserErrorReport{" +
			"id=" + id +
			", userId=" + userId +
			", errorId=" + errorId +
			", reportDate=" + reportDate +
			'}';
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getErrorId() {
		return errorId;
	}

	public void setErrorId(int errorId) {
		this.errorId = errorId;
	}

	public LocalDateTime getReportDate() {
		return reportDate;
	}

	public void setReportDate(LocalDateTime reportDate) {
		this.reportDate = reportDate;
	}
}
