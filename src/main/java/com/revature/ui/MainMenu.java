package com.revature.ui;

import org.apache.log4j.Logger;
import com.revature.util.Sc;

public class MainMenu implements Menu {

	private static Logger log = Logger.getLogger(MainMenu.class);
	
	@Override
	public void display() {

		boolean isFlag = true;
		
		while (isFlag) {
			log.info("\n-----------------------WELCOME TO XING LIU'S BANKING SYSTEM-----------------------\n");
			log.info("                         1. Log In As Customer");
			log.info("                         2. Log In As Employee");
			log.info("                         3. Create an Customer Account");
			log.info("                         4. Create an Employee Account");
			log.info("                         5. Exit");
			log.info("\n                         PLEASE CHOOSE FROM <1-5>: ");

			
			String choice = Sc.sc.nextLine();
			
			switch (choice) {
			case "1":
				Menu customerLoginView = new CustomerLoginView();
				customerLoginView.display();
				break;
			case "2":
				Menu employeeLoginView = new EmployeeLoginView();
				employeeLoginView.display();
				break;
			case "3":
				Menu createCustomerView = new CreateCustomerView();
				createCustomerView.display();
				break;
			case "4":
				Menu createEmployeeView = new CreateEmployeeView();
				createEmployeeView.display();
				break;
			case "5":
				isFlag = false;
				break;
			default:
				log.info("You entered wrong choice, plese enter again");
			}
		}

	}

}
