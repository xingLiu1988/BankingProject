package com.revature.dao;

import com.revature.models.Customer;

public interface CustomerDao {
	public int createCustomer(Customer cus);
	public int validatePassword(String username, String password);
	public boolean applyCheckingAccount(int id, int number, String dob);
	public boolean applySavingAccount(int id, int number, String dob);
	public Customer getBalance(int id);
	public boolean depositToChecking(int amountInt);
	public boolean depositToSaving(int amountInt);
	public int getCheckingIDByLoginID();
	public int getSavingIDByLoginID();
	public void withdrawFromChecking(int amount, int balance);
	public int getCheckingBalanceByAccountId();
	public int getSavingBalanceByAccountId();
	public boolean checkAccountExist(int customerIDYouWantToTransfer);
	public void depositToAccount(int amount, int customerIDYouWantToTransfer);
	public void withdrawFromSaving(int amount, int balance);
	public int getCusIdByLoginId();
}
