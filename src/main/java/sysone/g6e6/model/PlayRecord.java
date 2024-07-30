package sysone.g6e6.model;

import java.time.LocalDateTime;

public class PlayRecord {
	private Integer id;
	private int userId;
	private int subjectId;
	private String difficulty;
	private int solveTime;
	private LocalDateTime playDate;

	public PlayRecord(Integer id, int userId, int subjectId, String difficulty, int solveTime) {
		this.id = id;
		this.userId = userId;
		this.subjectId = subjectId;
		this.difficulty = difficulty;
		this.solveTime = solveTime;
	}

	public PlayRecord(Integer id, int userId, int subjectId, String difficulty, int solveTime,
		LocalDateTime playDate) {
		this.id = id;
		this.userId = userId;
		this.subjectId = subjectId;
		this.difficulty = difficulty;
		this.solveTime = solveTime;
		this.playDate = playDate;
	}

	@Override
	public String toString() {
		return "PlayRecord{" +
			"id=" + id +
			", userId=" + userId +
			", subjectId=" + subjectId +
			", difficulty='" + difficulty + '\'' +
			", solveTime='" + solveTime + '\'' +
			", playDate=" + playDate +
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

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public int getSolveTime() {
		return solveTime;
	}

	public void setSolveTime(int solveTime) {
		this.solveTime = solveTime;
	}

	public LocalDateTime getPlayDate() {
		return playDate;
	}

	public void setPlayDate(LocalDateTime playDate) {
		this.playDate = playDate;
	}
}
