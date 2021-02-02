package com.revature.ui;


import com.revature.services.CustomerService;
import com.revature.util.Sc;

public class CreateCustomerView implements Menu{

	@Override
	public void display() {
		System.out.println("\n-----------------------WELCOME TO CREATE CUSTOMER ACCOUNT--------------------------");
		
		System.out.println("Please Enter Your First Name");
		String firstName = Sc.sc.nextLine();
		
		System.out.println("Please Enter Your Last Name");
		String lastName = Sc.sc.nextLine();
		
		
		Customer customer = new Customer(firstName, lastName);
		
		CustomerService customerService = new CustomerService();
		customerService.createCustomer(customer);
	}
	
}
