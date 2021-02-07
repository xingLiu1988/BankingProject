package com.revature.models;

public class Login {
	private String username;
	private String password;
	private int loginID;
	
	public Login(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public Login() {
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getLoginID() {
		return loginID;
	}

	public void setLoginID(int loginID) {
		this.loginID = loginID;
	}

	@Override
	public String toString() {
		return "Login [username=" + username + ", password=" + password + ", loginID=" + loginID + "]";
	}
	
	
}
