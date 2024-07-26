package sysone.g6e6.model;

public class Subject {
	private Integer id;
	private String content;

	public Subject(Integer id, String content) {
		this.id = id;
		this.content = content;
	}

	@Override
	public String toString() {
		return "Subject{" +
			"id=" + id +
			", content='" + content + '\'' +
			'}';
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
