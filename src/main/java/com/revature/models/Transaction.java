package com.revature.models;

public class Transaction {

	private int transID;
	private String transType;//withdraw, deposit, transfer
	private String transAccountType;//checking, saving
	private int transAmount;//amount
	private String transDate;
	public int getTransID() {
		return transID;
	}
	public void setTransID(int transID) {
		this.transID = transID;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getTransAccountType() {
		return transAccountType;
	}
	public void setTransAccountType(String transAccountType) {
		this.transAccountType = transAccountType;
	}
	public int getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(int transAmount) {
		this.transAmount = transAmount;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	@Override
	public String toString() {
		return "Transaction [transID=" + transID + ", transType=" + transType + ", transAccountType=" + transAccountType
				+ ", transAmount=" + transAmount + ", transDate=" + transDate + "]";
	}
	
	
	
}
