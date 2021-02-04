package com.revature.ui;

import org.apache.log4j.Logger;

import com.revature.models.Customer;
import com.revature.services.CustomerService;
import com.revature.util.Sc;

public class CustomerPanel implements Menu{
	Customer customer;
	CustomerService customerService;
	private static Logger log = Logger.getLogger(CustomerPanel.class);
	
	//whenever customer log in and we will create an object and store customer login id
	public CustomerPanel() {
		customer = new Customer();
		customerService = new CustomerService();
	}
	
	@Override
	public void display() {
		
		boolean isFlag = true;
		
		while(isFlag) {
			log.info("\n-----------------------WELCOME TO CUSTOMER'S BANKING SYSTEM-----------------------\n");
			log.info("                         1. APPLY FOR CHECKING ACCOUNT");
			log.info("                         2. APPLY FOR SAVING ACCOUNT");
			log.info("                         3. CHECK YOUR CHECKING ACCOUNT BALANCE");
			log.info("                         4. CHECK YOUR SAVING ACCOUNT BALANCE");
			log.info("                         5. DEPOSIT TO CHECKING ACCOUNT");
			log.info("                         6. DEPOSIT TO SAVING ACCOUNT");
			log.info("                         7. WITHDRAW FROM CHECKING ACCOUNT");
			log.info("                         8. WITHDRAW FROM SAVING ACCOUNT");
			log.info("                         9. TRANSFER MONEY");
			log.info("                         0. Exit");
			log.info("\n                         PLEASE CHOOSE FROM <0-9>: ");
			
			String choice = Sc.sc.nextLine();
			
			switch (choice) {
			case "0":
				isFlag = false;
				break;
			case "1":
				boolean result = customerService.applyCheckingAccount(CustomerLoginView.id);
				if(result) {
					log.info("…Í«Îchecking’Àªß≥…π¶");
				}else {
					log.info("…Í«Îchecking’Àªß ß∞‹");
				}
				break;
			case "2":
				
				break;
			case "3":
				
				break;
			case "4":
				
				break;
			case "5":
				
				break;
			case "6":
				
				break;
			case "7":
				
				break;
			case "8":
				
				break;
			case "9":
				
				break;

			default:
				break;
			}
		}
		
		
	}

}
