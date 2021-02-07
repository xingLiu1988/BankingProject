package com.revature.dao;

import java.util.List;

import com.revature.models.Customer;

public interface EmployeeDao {

	//1. employee can view all customer list
	public List<Customer> viewAllCustomersAccount();
	
	//2. employee can view single customer
	public Customer viewSingleCustomerAccount(int customerID);
	
	//3. employee can delete single customer
	public boolean deleteCustomerByCustomerID(int customerID);
	
	//4. employee can view single customer log
	
	//5. employee can view all logs
}
