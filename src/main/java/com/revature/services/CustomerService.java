package com.revature.services;

import com.revature.dao.CustomerDao;
import com.revature.dao.CustomerDaoImpl;
import com.revature.ui.Customer;

public class CustomerService {
	public CustomerDao customerDao;
	
	public CustomerService() {
		customerDao = new CustomerDaoImpl();
	}
	
	public void createCustomer(Customer cus) {
		customerDao.createCustomer(cus);
	}
	
}
