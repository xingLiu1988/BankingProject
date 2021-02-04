package com.revature.ui;

import org.apache.log4j.Logger;

import com.revature.services.CustomerService;
import com.revature.util.Sc;
import com.sun.org.apache.xerces.internal.impl.dv.dtd.IDDatatypeValidator;

public class CustomerLoginView implements Menu {
	static int id;
	private static Logger log = Logger.getLogger(CustomerLoginView.class);

	@Override
	public void display() {
		boolean isFlag = true;
		log.info("\n-----------------------WELCOME TO CUSTOMER'S LOG IN SYSTEM------------------------\n");
		while (isFlag) {
			log.info("\n                   1. Log In To Your Customer Panel");
			log.info("\n                   2. Return Back To Main Menu");
			String choice = Sc.sc.nextLine();
			
			switch (choice) {
			case "1":
				int result = getPassword();
				if(result == -1) {
					log.info("Login Info Not Found, Please Try Again");
				}else {
					id = result;
					CustomerPanel customerPanel = new CustomerPanel();
					customerPanel.display();
				}
				break;
			case "2":
				isFlag = false;
				break;
			default:
				log.info("Your Entered Wrong Choice, Please Enter Again");
				break;
			}
		}

	}

	private int getPassword() {
		// Step 1: get username
		log.info("1. Please enter your username");
		String username = Sc.sc.nextLine();

		// Step 2: get password
		log.info("\n2. Please enter your password");
		String password = Sc.sc.nextLine();

		// Step 3: validate username and password
		CustomerService customerService = new CustomerService();
		int result = customerService.validatePassword(username, password);
		
		// Step 4: if result = -1 not found, else return with login_id
		return result;
	}

}
