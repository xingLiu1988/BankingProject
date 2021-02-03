package com.revature.ui;


import org.apache.log4j.Logger;

import com.revature.models.Customer;
import com.revature.models.Login;
import com.revature.services.CustomerService;
import com.revature.util.Sc;

public class CreateCustomerView implements Menu{

	private static Logger log = Logger.getLogger(CreateCustomerView.class);
	
	@Override
	public void display() {
		log.info("\n-----------------------WELCOME TO CREATE CUSTOMER ACCOUNT--------------------------");
		
		log.info("Please Enter Your First Name");
		String firstName = Sc.sc.nextLine();
		
		log.info("Please Enter Your Last Name");
		String lastName = Sc.sc.nextLine();
		
		log.info("Please Enter Your Username");
		String username = Sc.sc.nextLine();
		
		log.info("Please Enter Your Password");
		String password = Sc.sc.nextLine();
		
		//创建LOGIN
		Login login = new Login(username, password);
		//创建用户
		Customer customer = new Customer(firstName, lastName, login);
		
		CustomerService customerService = new CustomerService();
		customerService.createCustomer(customer);
	}
	
}
