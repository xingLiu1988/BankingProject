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
	
	//1 创建用户
	public void createCustomer(Customer cus) {
		int count = customerDao.createCustomer(cus);
		if(count == 0) {
			log.info("\nUser created successfully\n");
		}else {
			log.info("\nFailed to create user. Please try again later\n");
		}
	}
	
	//2 判断用户名和密码是否正确
	public int validatePassword(String username, String password) {
		
		int result = customerDao.validatePassword(username, password);
		
		return result;
	}

	//3 申请支票账户
	public boolean applyCheckingAccount(int id) {
		//account number
		int number = (int) Math.floor(Math.random()*99999999);
		boolean result = customerDao.applyCheckingAccount(id, number);
		
		
		
		return result;
	}

	public boolean applySavingAccount(int id) {
		int number = (int) Math.floor(Math.random()*99999999);
		boolean result = customerDao.applySavingAccount(id, number);
		
		return result;
	}

	public Customer getBalance(int id) {
		Customer cus = customerDao.getBalance(id);
		return cus;
	}
	
}
