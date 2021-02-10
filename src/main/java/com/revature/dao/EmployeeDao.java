package com.revature.dao;

import java.util.List;

import com.revature.models.Customer;
import com.revature.models.Transaction;

public interface EmployeeDao {

	//1. employee can view all customer list
	public List<Customer> viewAllCustomersAccount();
	
	//2. employee can view single customer
	public List<Customer> viewSingleCustomerAccount(int customerID);
	
	//3. employee can delete single customer
	public int deleteCustomerByAccountNumber(int accountNumber);

	public List<Transaction> viewAllTransactions();

	public boolean checkCustomerIdExist(int customerID);

	public List<Transaction> viewSingleTransactionById(int customerID);
	
	//4. employee can view single customer log
	
	//5. employee can view all logs
}
