package com.revature.services;


public interface Customer {
	public int validatePassword(String username, String password);
	public boolean applyCheckingAccount(int id);
	public boolean applySavingAccount(int id);
	public boolean depositToChecking();
	public boolean depositToSaving();
	public void withdrawFromChecking();
	public int getCheckingIDByLoginID();
	public int getSavingIDByLoginID();
	public void withdrawFromSaving();
	public boolean transfer();
	public int getCusIdByLoginId();
}
