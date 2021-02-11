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

public class EmployeeService implements Employee{
	private static Logger log = Logger.getLogger(EmployeeService.class);
	EmployeeDao employeeDao;

	public EmployeeService() {
		employeeDao = new EmployeeDaoImpl();
	}

	// VIEW ALL CUSTOMER ACCOUNT
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

	// VIEW SINGLE CUSTOMER ACCOUNT BY CUSTOMER ID
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
					"--------------------------------------------------------------------------------------------------------------------------");
			log.info("Customer Name: " + list.get(0).getFirstName() + " " + list.get(0).getLastName());
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getAccount() == null) {
					log.info(list.get(i).getCustomerID() + "\t\t" + list.get(i).getFirstName() + "\t\t"
							+ list.get(i).getLastName() + "\t\tNo Banking Account ");
				} else {
					// If customer have checking account and display
					if (list.get(i).getAccount().getAccountNumberChecking() != 0) {
						log.info("Checking Account Number: " + list.get(i).getAccount().getAccountNumberChecking()
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
					"--------------------------------------------------------------------------------------------------------------------------");
		}
	}

	// VALIDE CUSTOMER'S USERNAME AND PASSWORD
	public int validatePassword() {
		int result = -1;
		CustomerDao customerDao = new CustomerDaoImpl();
		customerDao.validatePassword(null, null);
		return result;
	}

	// DELETE CUSTOMER ACCOUNT BY USING CUSTOEMR ACCOUNT NUMBER
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

	// VIEW ALL TRANSACTIONS OF CUSTOMERS
	public void viewAllTransactions() {
		List<Transaction> list = employeeDao.viewAllTransactions();
		Iterator<Transaction> it = list.iterator();
		log.info("TransactionID\tTransactionType\t\tTransactionAccount\tAmount\tDate");
		while (it.hasNext()) {
			Transaction s = it.next();
//			Transaction [transID=1, transType=deposit, transAccountType=checking, transAmount=100, transDate=2021-02-09 14:45:22]
			log.info("\t" + s.getTransID() + "\t" + s.getTransType() + " \t\t" + s.getTransAccountType() + " \t\t"
					+ s.getTransAmount() + "\t" + s.getTransDate());
		}
	}

	// VIEW SINGLE TRANSACTION BY USING CUSTOMER ID
	public void viewSingleTransactionById() {
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
//		[Transaction [transID=1, transType=deposit, transAccountType=checking, transAmount=100, transDate=2021-02-09 14:45:22], Transaction [transID=2, transType=deposit, transAccountType=saving, transAmount=155, transDate=2021-02-09 14:59:21], Transaction [transID=3, transType=deposit, transAccountType=toChecking, transAmount=1, transDate=2021-02-09 15:01:02], Transaction [transID=4, transType=withdraw, transAccountType=fromChecking, transAmount=22, transDate=2021-02-09 15:03:01], Transaction [transID=5, transType=withdraw, transAccountType=fromSaving, transAmount=66, transDate=2021-02-09 15:04:39], Transaction [transID=6, transType=withdraw, transAccountType=fromChecking, transAmount=20, transDate=2021-02-09 15:08:38], Transaction [transID=7, transType=transfer, transAccountType=toOtherAccount, transAmount=20, transDate=2021-02-09 15:08:38]]

		boolean isExist = employeeDao.checkCustomerIdExist(customerID);

		if (isExist == true) {
			List<Transaction> transaction = employeeDao.viewSingleTransactionById(customerID);
			Iterator<Transaction> it = transaction.iterator();
			log.info("-------------------------------------------------------------------------------------------");
			log.info("Trans_ID\tTrans_Type\tTrans_Account_Type\tTrans_Amount\tTrans_Date");
			while (it.hasNext()) {
				Transaction t = it.next();
				log.info("\t" + t.getTransID() + "\t " + t.getTransType() + " \t" + t.getTransAccountType() + " \t\t"
						+ t.getTransAmount() + "\t\t" + t.getTransDate());
			}
			log.info("-------------------------------------------------------------------------------------------");
		} else {
			log.info("The ID You Enter is not found");
		}
	}

}
