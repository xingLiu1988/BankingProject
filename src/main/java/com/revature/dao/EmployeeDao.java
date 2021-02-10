package com.revature.dao;

import java.util.List;
import com.revature.models.Customer;
import com.revature.models.Transaction;

public interface EmployeeDao {

	public List<Customer> viewAllCustomersAccount();

	public List<Customer> viewSingleCustomerAccount(int customerID);

	public int deleteCustomerByAccountNumber(int accountNumber);

	public List<Transaction> viewAllTransactions();

	public boolean checkCustomerIdExist(int customerID);

	public List<Transaction> viewSingleTransactionById(int customerID);

}
