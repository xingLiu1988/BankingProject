package com.revature.services;

import com.revature.dao.CustomerDao;
import com.revature.dao.CustomerDaoImpl;
import com.revature.models.Customer;

public class CustomerService {
	public CustomerDao customerDao;
	
	public CustomerService() {
		customerDao = new CustomerDaoImpl();
	}
	
	public void createCustomer(Customer cus) {
		System.out.println("customer service class");
		customerDao.createCustomer(cus);
	}
	
}
