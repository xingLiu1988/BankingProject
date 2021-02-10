package com.revature.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.dao.CustomerDao;
import com.revature.dao.CustomerDaoImpl;
import com.revature.dao.EmployeeDao;
import com.revature.dao.EmployeeDaoImpl;
import com.revature.models.Customer;
import com.revature.models.Transaction;
import com.revature.util.Sc;

public class EmployeeService {
	private static Logger log = Logger.getLogger(EmployeeService.class);
	EmployeeDao employeeDao;

	public EmployeeService() {
		employeeDao = new EmployeeDaoImpl();
	}

	// view all customers account
	public void viewAllCustomersAccount() {

		List<Customer> list = employeeDao.viewAllCustomersAccount();
		if (list.size() == 0) {
			log.info("Empty List");
		} else {
			log.info(
					"\n----------------------------------------------------------------------------------------------------------------------------");
			log.info("CustomerID\tFirstName\tLastName\tAccountType\tAccountNumber\tBalance\t\tOpenDate");
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getAccount() == null) {
					log.info(list.get(i).getCustomerID() + "\t\t" + list.get(i).getFirstName() + "\t\t"
							+ list.get(i).getLastName() + "\t\tNo Banking Account ");
				} else {
					if (list.get(i).getAccount().getAccountType() == "checking") {
						log.info(list.get(i).getCustomerID() + "\t\t" + list.get(i).getFirstName() + "\t\t"
								+ list.get(i).getLastName() + "\t\t" + "checking" + "\t"
								+ list.get(i).getAccount().getAccountNumberChecking() + "\t"
								+ list.get(i).getAccount().getBalanceChecking() + "\t\t"
								+ list.get(i).getAccount().getDateChecking());
					} else {
						log.info(list.get(i).getCustomerID() + "\t\t" + list.get(i).getFirstName() + "\t\t"
								+ list.get(i).getLastName() + "\t\t" + "saving" + "\t\t"
								+ list.get(i).getAccount().getAccountNumberSaving() + "  \t"
								+ +list.get(i).getAccount().getBalanceSaving() + "\t\t"
								+ list.get(i).getAccount().getDateSaving());
					}
				}

			}

			log.info(
					"----------------------------------------------------------------------------------------------------------------------------");
		}
	}

	// view customer account
	public void viewSingleCustomerAccount() {
		int customerID = 0;
		boolean flag1 = false;

		do {
			flag1 = false;
			try {
				log.info("Please Enter Customer ID To Search");
				customerID = Integer.parseInt(Sc.sc.nextLine());
			} catch (NumberFormatException e) {
				log.info("incorrect number, please try again");
				flag1 = true;
			}
		} while (flag1);

		List<Customer> list = new ArrayList<>();
		list = employeeDao.viewSingleCustomerAccount(customerID);

		if (list.size() == 0) {
			log.info("No Record");
		} else {
			log.info(
					"----------------------------------------------------------------------------------------------------------------");
			for (int i = 0; i < list.size(); i++) {
				log.info("\nCustomer Name: " + list.get(i).getFirstName() + " " + list.get(i).getLastName());

				if (list.get(i).getAccount() == null) {
					log.info(list.get(i).getCustomerID() + "\t\t" + list.get(i).getFirstName() + "\t\t"
							+ list.get(i).getLastName() + "\t\tNo Banking Account ");
				} else {
					// If customer have checking account and display
					if (list.get(i).getAccount().getAccountNumberChecking() != 0) {
						log.info("\nChecking Account Number: " + list.get(i).getAccount().getAccountNumberChecking()
								+ "\tChecking Balance: " + list.get(i).getAccount().getBalanceChecking()
								+ "\t\tCreated Date: " + list.get(i).getAccount().getDateChecking());
					}
					// If customer have saving account and display
					if (list.get(i).getAccount().getAccountNumberSaving() != 0) {
						log.info("Saving  Account  Number: " + list.get(i).getAccount().getAccountNumberSaving()
								+ "\tSaving   Balance: " + list.get(i).getAccount().getBalanceSaving()
								+ "\t\tCreated Date: " + list.get(i).getAccount().getDateSaving());
					}
				}

			}
			log.info(
					"\n----------------------------------------------------------------------------------------------------------------");
		}
	}

	// validate customer login username and password
	public int validatePassword() {
		int result = -1;
		CustomerDao customerDao = new CustomerDaoImpl();
		customerDao.validatePassword(null, null);
		return result;
	}

	// delete customer account number
	public void deleteCustomerByAccountNumber() {
		int accountNumber = 0;
		boolean flag1 = false;

		do {
			flag1 = false;
			try {
				log.info("Please Enter Account Number To delete");
				accountNumber = Integer.parseInt(Sc.sc.nextLine());
			} catch (NumberFormatException e) {
				log.info("incorrect number, please try again");
				flag1 = true;
			}
		} while (flag1);

		int result = employeeDao.deleteCustomerByAccountNumber(accountNumber);
		if (result == 0) {
			log.info("\ndeletion failed or no customer account found, please try again\n");
		} else {
			log.info("\ndeletion success, thanks\n");
		}
	}

	public void viewAllTransactions() {
		List<Transaction> list = employeeDao.viewAllTransactions();
		Iterator<Transaction> it = list.iterator();
		log.info("TransactionID\tTransactionType\t\tTransactionAccount\tAmount\tDate");
		while(it.hasNext()) {
			Transaction s=it.next();
//			Transaction [transID=1, transType=deposit, transAccountType=checking, transAmount=100, transDate=2021-02-09 14:45:22]
			log.info("\t" + s.getTransID() + "\t" + s.getTransType() + " \t\t" + s.getTransAccountType() + " \t\t" + s.getTransAmount() + "\t" + s.getTransDate());
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
