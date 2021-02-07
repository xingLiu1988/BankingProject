package com.revature.ui;

import org.apache.log4j.Logger;

import com.revature.models.Customer;
import com.revature.services.CustomerService;
import com.revature.util.Sc;

public class CustomerPanel implements Menu {
	Customer customer;
	CustomerService customerService;
	private static Logger log = Logger.getLogger(CustomerPanel.class);

	public CustomerPanel() {
		customer = new Customer();
		customerService = new CustomerService();
	}

	@Override
	public void display() {

		boolean isFlag = true;

		while (isFlag) {
		  log.info("\n================================================");
			log.info("=     CUSTOMER PANEL                           =");
			log.info("=       1). Apply For Checking Account         =");
			log.info("=       2). Apply For Saving Account           =");
			log.info("=       3). Check Account Balance              =");
			log.info("=       4). Deposit To Checking Account        =");
			log.info("=       5). Deposit To Saving Account          =");
			log.info("=       6). Withdraw From Checking Account     =");
			log.info("=       7). Withdraw From Saving Account       =");
			log.info("=       8). Transfor Money                     =");
			log.info("=       9.  Exit                               =");
			log.info("=     PLEASE CHOOSE FROM <0-9>:                =");
			log.info("================================================");
			String choice = Sc.sc.nextLine();
			
		
			
			switch (choice) {
			case "9":
				isFlag = false;
				break;

			case "1":
				boolean result = customerService.applyCheckingAccount(CustomerLoginView.id);
				if (result) {
					log.info("����checking�˻��ɹ�");
				} else {
					log.info("����checking�˻�ʧ��");
				}
				break;

			case "2":
				boolean result2 = customerService.applySavingAccount(CustomerLoginView.id);
				if (result2) {
					log.info("����savingg�˻��ɹ�");
				} else {
					log.info("����saving�˻�ʧ��");
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
				customerService.depositToSaving();
				break;

			case "6":
				if (CustomerLoginView.checkingID == 0) {
					log.info("You havn't create a checking account yet, please create one first");
				} else {
					customerService.withdrawFromChecking();
				}
				break;

			case "7":
				if (CustomerLoginView.savingID == 0) {
					log.info("You havn't create a saving account yet, please create one first");
				} else {
					customerService.withdrawFromSaving();
				}
				break;

			case "8":
				customerService.transfer();
				break;

			default:
				log.info("You entered incorrect number, please enter again");
				break;
			}
		}

	}

	private void displayBalance(Customer customer2) {
		
		if(customer2.getAccount() == null) {
			
			log.info("\nYou havn't create any account yet, please apply first\n");
		}else {
			log.info("----------------------------------------------------------------------------------------------------------------");
			log.info("\nCustomer Name: " + customer2.getFirstName() + " " + customer2.getLastName());
			//If customer have checking account and display
			if(customer2.getAccount().getAccountNumberChecking() != 0) {
				log.info("\nChecking Account Number: " + customer2.getAccount().getAccountNumberChecking() + "\tChecking Balance: " + customer2.getAccount().getBalanceChecking() + "\t\tCreated Date: " + customer2.getAccount().getDateChecking());
			}
			//If customer have saving account and display
			if(customer2.getAccount().getAccountNumberSaving() != 0) {
				log.info("Saving  Account  Number: " + customer2.getAccount().getAccountNumberSaving()+ "\tSaving   Balance: " + customer2.getAccount().getBalanceSaving() + "\t\tCreated Date: " + customer2.getAccount().getDateSaving());
			}
			log.info("\n----------------------------------------------------------------------------------------------------------------");
		}
		
		
	}

}
