package com.revature.models;

import java.util.Date;

public class Account {
	
	private int accountNumberChecking;
	private int accountNumberSaving;
	private String accountType;
	private double balanceChecking;
	private double balanceSaving;
	private String dateChecking;
	private String dateSaving;
	
	public Account() {
		super();
	}



	public int getAccountNumberChecking() {
		return accountNumberChecking;
	}



	public void setAccountNumberChecking(int accountNumberChecking) {
		this.accountNumberChecking = accountNumberChecking;
	}



	public int getAccountNumberSaving() {
		return accountNumberSaving;
	}



	public void setAccountNumberSaving(int accountNumberSaving) {
		this.accountNumberSaving = accountNumberSaving;
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



	public String getAccountType() {
		return accountType;
	}



	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}



	@Override
	public String toString() {
		return "Account [accountNumberChecking=" + accountNumberChecking + ", accountNumberSaving="
				+ accountNumberSaving + ", accountType=" + accountType + ", balanceChecking=" + balanceChecking
				+ ", balanceSaving=" + balanceSaving + ", dateChecking=" + dateChecking + ", dateSaving=" + dateSaving
				+ "]";
	}


	
	
	
	
	
	
}
