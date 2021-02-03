package com.revature.models;

public class Customer {
	private String firstName;
	private String lastName;
	private Account account;
	private Login login;
	
	public Customer(String firstName, String lastName, Login login) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.login = login;
	}

	public Customer(String firstName, String lastName, Account account, Login login) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.account = account;
		this.login = login;
	}

	public Customer() {
		super();
	}

	public Customer(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Login getLogin() {
		return login;
	}

	@Override
	public String toString() {
		return "Customer [firstName=" + firstName + ", lastName=" + lastName + ", account=" + account + ", login="
				+ login + "]";
	}

	public void setLogin(Login login) {
		this.login = login;
	}
	
	
	
}