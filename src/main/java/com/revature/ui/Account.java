package com.revature.ui;

import java.util.Date;

public class Account {
	
	private static int accountNumber = 100000;
	private String accountType;
	private double balance = 0.0;
	private Date accountCreatedDate = new Date();
	
	public Account() {
		super();
		accountNumber++;
	}

	public Account(String accountType) {
		super();
		accountNumber++;
		this.accountType = accountType;
	}

	public static int getAccountNumber() {
		return accountNumber;
	}

	public static void setAccountNumber(int accountNumber) {
		Account.accountNumber = accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Date getAccountCreatedDate() {
		return accountCreatedDate;
	}

	public void setAccountCreatedDate(Date accountCreatedDate) {
		this.accountCreatedDate = accountCreatedDate;
	}
	
}
