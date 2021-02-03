package com.revature.services;

import org.apache.log4j.Logger;

import com.revature.dao.CustomerDao;
import com.revature.dao.CustomerDaoImpl;
import com.revature.models.Customer;
import com.revature.ui.MainMenu;

public class CustomerService {
	private static Logger log = Logger.getLogger(CustomerService.class);
	public CustomerDao customerDao;
	
	public CustomerService() {
		customerDao = new CustomerDaoImpl();
	}
	
	public void createCustomer(Customer cus) {
		int count = customerDao.createCustomer(cus);
		if(count == 0) {
			log.info("\nUser created successfully\n");
		}else {
			log.info("\nFailed to create user. Please try again later\n");
		}
	}
	
}
