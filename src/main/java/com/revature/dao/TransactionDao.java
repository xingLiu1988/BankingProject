package com.revature.dao;

public interface TransactionDao {
	public boolean sendTransaction(int cusID, String transType, String transAccountType, int transAmount);
}
