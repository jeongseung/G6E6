package sysone.g6e6.model;

public class ErrorReport {
	private Integer id;
	private int quizId;
	private String errorDescription;
	private short resolved;

	public ErrorReport(Integer id, int quizId, String errorDescription, short resolved) {
		this.id = id;
		this.quizId = quizId;
		this.errorDescription = errorDescription;
		this.resolved = resolved;
	}

	@Override
	public String toString() {
		return "ErrorReport{" +
			"id=" + id +
			", quizId=" + quizId +
			", errorDescription='" + errorDescription + '\'' +
			", resolved=" + resolved +
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

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public short getResolved() {
		return resolved;
	}

	public void setResolved(short resolved) {
		this.resolved = resolved;
	}
}
