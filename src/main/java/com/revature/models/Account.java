package com.revature.models;

import java.util.Date;

public class Account {
	
	private int accountNumber;
	private String accountTypeChecking;
	private String accountTypeSaving;
	private double balanceChecking;
	private double balanceSaving;
	private String dateChecking;
	private String dateSaving;
	
	public Account() {
		super();
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountTypeChecking() {
		return accountTypeChecking;
	}

	public void setAccountTypeChecking(String accountTypeChecking) {
		this.accountTypeChecking = accountTypeChecking;
	}

	public String getAccountTypeSaving() {
		return accountTypeSaving;
	}

	public void setAccountTypeSaving(String accountTypeSaving) {
		this.accountTypeSaving = accountTypeSaving;
	}

	public double getBalanceChecking() {
		return balanceChecking;
	}

	public void setBalanceChecking(double balanceChecking) {
		this.balanceChecking = balanceChecking;
	}

	public double getBalanceSaving() {
		return balanceSaving;
	}

	public void setBalanceSaving(double balanceSaving) {
		this.balanceSaving = balanceSaving;
	}

	public String getDateChecking() {
		return dateChecking;
	}

	public void setDateChecking(String dateChecking) {
		this.dateChecking = dateChecking;
	}

	public String getDateSaving() {
		return dateSaving;
	}

	public void setDateSaving(String dateSaving) {
		this.dateSaving = dateSaving;
	}

	@Override
	public String toString() {
		return "Account [accountNumber=" + accountNumber + ", accountTypeChecking=" + accountTypeChecking
				+ ", accountTypeSaving=" + accountTypeSaving + ", balanceChecking=" + balanceChecking
				+ ", balanceSaving=" + balanceSaving + ", dateChecking=" + dateChecking + ", dateSaving=" + dateSaving
				+ "]";
	}
	
	
	
	
	
}
