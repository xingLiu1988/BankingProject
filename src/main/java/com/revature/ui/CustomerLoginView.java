package com.revature.ui;

import org.apache.log4j.Logger;

import com.revature.services.CustomerService;
import com.revature.util.Sc;

public class CustomerLoginView implements Menu {
	public static int id;// used to save login_id
	public static int cusID;// used to save customer_id
	public static int checkingID;// used to save checking_id
	public static int savingID;// used to save saving_id
	private static Logger log = Logger.getLogger(CustomerLoginView.class);
	CustomerPanel customerPanel;

	public CustomerLoginView() {
		customerPanel = new CustomerPanel();
	}

	@Override
	public void display() {
		boolean isFlag = true;
		while (isFlag) {
		  log.info("\n================================================");
			log.info("=     CUSTOMER LOG IN                          =");
			log.info("=       1). Log In To Customer Panel           =");
			log.info("=       2). Back To Main Menu                  =");
			log.info("=     CHOOSE FROM <1-2>:                       =");
			log.info("================================================\n");
			
			String choice = Sc.sc.nextLine();

			switch (choice) {
			case "1":
				int result = getPassword();
				if (result == -1) {
					log.info("Login Failed, Please Try Again");
				} else {
					// AFTER CUSTOMER LOGGED IN, WE WILL STORE cusID, checkingID, savingID TO STATIC VARIABLES FOR FUTURE
					id = result;
					log.info("\nYou have successfully logged into the system");
					cusID = getCusID();
					checkingID = getCheckingIDByLoginID();
					savingID = getSavingIDByLoginID();
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

	// USED TO GET CUSTOMER ID
	private int getCusID() {
		CustomerService customerService = new CustomerService();
		int cusID = customerService.getCusIdByLoginId();
		return cusID;
	}

	// USED TO GET CUSTOMER SAVING ACCOUNT NUMBER
	private int getSavingIDByLoginID() {
		CustomerService customerService = new CustomerService();
		int savingID = customerService.getSavingIDByLoginID();
		return savingID;
	}

	// USED TO GET CUSTOMER CHECKING ACCOUNT NUMBER
	private int getCheckingIDByLoginID() {
		CustomerService customerService = new CustomerService();
		int checkingID = customerService.getCheckingIDByLoginID();
		return checkingID;
	}

	// USED TO GET USERNAME AND PASSWORD FROM CUSTOMER
	private int getPassword() {
		
		log.info("1. Please enter your username");
		String username = Sc.sc.nextLine();

		log.info("\n2. Please enter your password");
		String password = Sc.sc.nextLine();

		// VALIDATE CUSTOMER USERNAME AND PASSWORD
		CustomerService customerService = new CustomerService();
		int result = customerService.validatePassword(username, password);

		return result;
	}

}
