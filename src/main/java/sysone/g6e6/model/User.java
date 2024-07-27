package sysone.g6e6.model;

public class User {
	private Integer id;
	private String email;
	private String password;
	private String nickname;
	private String role;

	public User(Integer id, String email, String password, String nickname, String role) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User{" +
			"id=" + id +
			", email='" + email + '\'' +
			", password='" + password + '\'' +
			", nickname='" + nickname + '\'' +
			", role='" + role + '\'' +
			'}';
	}
}
