package sysone.g6e6.model;

public class Quiz {
	private Integer id;
	private int subjectId;
	private String problem;
	private String answer;

	public Quiz(Integer id, int subjectId, String problem, String answer) {
		this.id = id;
		this.subjectId = subjectId;
		this.problem = problem;
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "Quiz{" +
			"id=" + id +
			", subjectId=" + subjectId +
			", problem='" + problem + '\'' +
			", answer='" + answer + '\'' +
			'}';
	}

	public Integer getId() {
		return id;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public String getProblem() {
		return problem;
	}

	public String getAnswer() {
		return answer;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
