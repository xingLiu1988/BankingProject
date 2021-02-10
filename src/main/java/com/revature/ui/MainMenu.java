package com.revature.ui;

import org.apache.log4j.Logger;

import com.revature.services.CustomerService;
import com.revature.util.Sc;

public class MainMenu implements Menu {

	private static Logger log = Logger.getLogger(MainMenu.class);
	
	@Override
	public void display() {

		boolean isFlag = true;
		
		while (isFlag) {
			log.info("\n================================================");
			log.info("=     WELCOME TO  BANKING SYSTEM               =");
			log.info("=       1). Log In As Customer                 =");
			log.info("=       2). Log In As Employee                 =");
			log.info("=       3). Create an Customer Account         =");
			log.info("=       4). Exit                               =");
			log.info("=     CHOOSE FROM <1-4>:                       =");
			log.info("================================================\n");
			
			String choice = Sc.sc.nextLine();
			
			switch (choice) {
			
			case "1":
				log.debug("customer entered 1");
				Menu customerLoginView = new CustomerLoginView();
				customerLoginView.display();
				break;
				
			case "2":
				log.debug("employee entered 2");
				int result = getPassword();
				if (result == -1) {
					log.info("Login Failed, Please Try Again");
				} else {
					Menu employeeLoginView = new EmployeeLoginView();
					employeeLoginView.display();
				}
				break;
				
			case "3":
				log.debug("customer entered 3");
				Menu createCustomerView = new CreateCustomerView();
				createCustomerView.display();
				break;
				
			case "4":
				log.debug("customer entered 4 to exit");
				isFlag = false;
				break;
				
			default:
				log.info("You entered wrong choice, plese enter again");
			}
		}

	}
	
	// USED TO GET CUSTOMER USERNAME AND PASSWORD
	private int getPassword() {
		
		log.info("1. Please enter your username");
		String username = Sc.sc.nextLine();

		log.info("\n2. Please enter your password");
		String password = Sc.sc.nextLine();

		// VALIDATE USERNAME AND PASSWORD
		CustomerService customerService = new CustomerService();
		int result = customerService.validatePassword(username, password);
		 
		return result;
	}
}
