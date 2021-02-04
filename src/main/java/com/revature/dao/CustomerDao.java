package com.revature.dao;

import com.revature.models.Customer;

public interface CustomerDao {
	public int createCustomer(Customer cus);
	public int validatePassword(String username, String password);
	public boolean applyCheckingAccount(int id, int number);
	public boolean applySavingAccount(int id, int number);
	public Customer getBalance(int id);
	public boolean depositToChecking(int amountInt);
	public boolean depositToSaving(int amountInt);
	public int getCheckingIDByLoginID();
	public int getSavingIDByLoginID();
}
