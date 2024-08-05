package sysone.g6e6.model;

public class Mistake {
	private Integer id;
	private int quizId;
	private int userId;
	private int subjectId;
	private String mistakeDate;

	public Mistake(Integer id, int quizId, int userId, int subjectId, String mistakeDate) {
		this.id = id;
		this.quizId = quizId;
		this.userId = userId;
		this.subjectId = subjectId;
		this.mistakeDate = mistakeDate;
	}

	@Override
	public String toString() {
		return "Mistake{" +
			"id=" + id +
			", quizId=" + quizId +
			", userId=" + userId +
			", subjectId=" + subjectId +
			", mistakeDate='" + mistakeDate + '\'' +
			'}';
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
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

	public String getMistakeDate() {
		return mistakeDate;
	}

	public void setMistakeDate(String mistakeDate) {
		this.mistakeDate = mistakeDate;
	}

}
