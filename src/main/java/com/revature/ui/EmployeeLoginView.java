package com.revature.ui;

import org.apache.log4j.Logger;

import com.revature.services.EmployeeService;
import com.revature.util.Sc;

public class EmployeeLoginView implements Menu {

	EmployeeService employeeService;
	
	public EmployeeLoginView() {
		employeeService = new EmployeeService();
	}
	private static Logger log = Logger.getLogger(EmployeeLoginView.class);

	@Override
	public void display() {

		boolean isFlag = true;

		while (isFlag) {
			log.info("\n================================================");
			log.info("=              <<EMPLOYEE MENU>>               =");
			log.info("=     1). View All Customers                   =");
			log.info("=     2). View Customers Account By Account ID =");
			log.info("=     3). Reject Customer Account By ID        =");
			log.info("=     4). View All Transaction                 =");
			log.info("=     5). View Single Transaction By ID        =");
			log.info("=     6). Exit                                 =");
			log.info("=            PLEASE CHOOSE FROM <1-5>:         =");
			log.info("================================================\n");
			String choice = Sc.sc.nextLine();
			

			switch (choice) {
			case "1":
				log.debug("customer entered 1");
				employeeService.viewAllCustomersAccount();
				break;
			case "2":
				log.debug("employee entered 2");
				Menu employeeLoginView = new EmployeeLoginView();
				employeeLoginView.display();
				break;
			case "3":
				log.debug("customer entered 3");
				Menu createCustomerView = new CreateCustomerView();
				createCustomerView.display();
				break;
			case "4":
				log.debug("employee entered 4");
				Menu createEmployeeView = new CreateEmployeeView();
				createEmployeeView.display();
				break;
			case "6":
				log.debug("customer entered 6 to exit");
				isFlag = false;
				break;
			default:
				log.info("You entered wrong choice, plese enter again");
			}
		}

	}

}
