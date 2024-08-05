package sysone.g6e6.model;

public class ErrorReport {
	private Integer id;
	private int quizId;
	private short resolved;

	public ErrorReport(Integer id, int quizId, short resolved) {
		this.id = id;
		this.quizId = quizId;
		this.resolved = resolved;
	}

	@Override
	public String toString() {
		return "ErrorReport{" +
			"id=" + id +
			", quizId=" + quizId +
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

	public short getResolved() {
		return resolved;
	}

	public void setResolved(short resolved) {
		this.resolved = resolved;
	}
}
