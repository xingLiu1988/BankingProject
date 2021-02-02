package com.revature.ui;

import com.revature.util.Sc;

public class MainMenu implements Menu {

	@Override
	public void display() {

		boolean isFlag = true;
		
		while (isFlag) {
			System.out.println("\n-----------------------WELCOME TO XING LIU'S BANKING SYSTEM-----------------------\n");
			System.out.println("                         1. Log In As Customer");
			System.out.println("                         2. Log In As Employee");
			System.out.println("                         3. Create an Customer Account");
			System.out.println("                         4. Create an Employee Account");
			System.out.println("                         5. Exit");
			System.out.print("\n                         PLEASE CHOOSE FROM <1-5>: ");

			
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
				System.out.println("You entered wrong choice, plese enter again");
			}
		}

	}

}
