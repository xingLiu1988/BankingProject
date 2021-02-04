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
			log.info("                         1. Apply For Checking Account");
			log.info("                         2. Apply For Saving Account\n");
			log.info("                         3. Check Account Balance\n");
			log.info("                         4. Deposit To Checking Account");
			log.info("                         5. Deposit To Saving Account");
			log.info("                         6. Withdraw From Checking Account");
			log.info("                         7. Withdraw From Saving Account");
			log.info("                         8. Transfor Money");
			log.info("                         9. Exit");
			log.info("\n                         PLEASE CHOOSE FROM <0-9>: ");
			
			String choice = Sc.sc.nextLine();
			
			switch (choice) {
			case "9":
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
				boolean result2 = customerService.applySavingAccount(CustomerLoginView.id);
				if(result2) {
					log.info("…Í«Îsavingg’Àªß≥…π¶");
				}else {
					log.info("…Í«Îsaving’Àªß ß∞‹");
				}
				break;
			case "3":
				customer = customerService.getBalance(CustomerLoginView.id);
				displayBalance(customer);
				break;
			case "4":
				customerService.depositToChecking();
				break;
			case "5":
				
				break;
			case "6":
				
				break;
			case "7":
				
				break;
			case "8":
				
				break;

			default:
				break;
			}
		}
		
		
	}

	private void displayBalance(Customer customer2) {
		
		log.info("\n--------------------------------------------------------------------------------");
		log.info("\nCustomer Name: " + customer2.getFirstName() + " " + customer2.getLastName());
		log.info("Account Number: " + customer2.getAccount().getAccountNumber());
		if(customer2.getAccount().getAccountTypeChecking() != null) {
			log.info("Checking Balance: " + customer2.getAccount().getBalanceChecking() + "\t\tCreated Date: " + customer2.getAccount().getDateChecking());
		}
		if(customer2.getAccount().getAccountTypeSaving() != null) {
			log.info("Saving Balance: " + customer2.getAccount().getBalanceSaving() + "\t\tCreated Date: " + customer2.getAccount().getDateSaving());
		}
	}

	

}
