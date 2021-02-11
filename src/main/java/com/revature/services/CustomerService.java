package com.revature.services;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import org.apache.log4j.Logger;
import com.revature.dao.CustomerDao;
import com.revature.dao.CustomerDaoImpl;
import com.revature.models.Customer;
import com.revature.ui.CustomerLoginView;
import com.revature.util.Sc;

public class CustomerService implements com.revature.services.Customer{
	private static Logger log = Logger.getLogger(CustomerService.class);
	public CustomerDao customerDao;

	public CustomerService() {
		customerDao = new CustomerDaoImpl();
	}

	// CREATE AN CUSTOMER
	public void createCustomer(Customer cus) {
		int count = customerDao.createCustomer(cus);
		if (count == 0) {
			log.info("\nUser created successfully\n");
		} else {
			log.info("\nFailed to create user. Please try again later\n");
		}
	}

	// VALIDATE USERNAME AND PASSWORD
	public int validatePassword(String username, String password) {

		int result = customerDao.validatePassword(username, password);

		return result;
	}

	// APPLY FOR CHECKING ACCOUNT
	public boolean applyCheckingAccount(int id) {
		// check if customer already have a checking account, if yes, will reject
		int checkingID = customerDao.getCheckingIDByLoginID();
		if (checkingID != 0) {
			log.info("You have checking account alrady");
			return false;
		}

		// get initial deposit amout
		boolean flag3 = false;
		int depostAmount = 0;
		do {
			flag3 = false;
			try {
				log.info("Please Enter Initial Amount To Checking Account");
				depostAmount = Integer.parseInt(Sc.sc.nextLine());
			} catch (NumberFormatException e) {
				log.info("incorrect number, please try again");
				flag3 = true;
			}
		} while (flag3);

		// randomly create an account number
		int number = (int) Math.floor(Math.random() * 88888888 + 11111110);

		// get date of birth
		log.info("\nPlease enter your date of birth (MM/DD/YYYY)");
		String dob = Sc.sc.nextLine();
		LocalDate dobString = LocalDate.parse(dob, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
		Period period = Period.between(dobString, LocalDate.now());
		int age = period.getYears();

		// only prove if over 18 years old
		if (age >= 18) {
			log.info("Age Over 18, Apply Checking Account Approved");
			boolean result = customerDao.applyCheckingAccount(id, number, dob, depostAmount);
			return result;
		} else {
			log.info("Age Less Than 18, Apply Checking Account Rejected");
			return false;
		}

	}

	// APPLY FOR SAVING ACCOUNT
	public boolean applySavingAccount(int id) {
		// check if customer already have a checking account, if yes, will reject
		int savingID = customerDao.getSavingIDByLoginID();
		if (savingID != 0) {
			log.info("You have saving account alrady");
			return false;
		}

		// get initial deposit amout
		boolean flag3 = false;
		int depostAmount = 0;
		do {
			flag3 = false;
			try {
				log.info("Please Enter Initial Amount To Saving Account");
				depostAmount = Integer.parseInt(Sc.sc.nextLine());
			} catch (NumberFormatException e) {
				log.info("incorrect number, please try again");
				flag3 = true;
			}
		} while (flag3);

		int number = (int) Math.floor(Math.random() * 88888888 + 11111110);

		// get date of birth
		log.info("\nPlease enter your date of birth (MM/DD/YYYY)");
		String dob = Sc.sc.nextLine();
		LocalDate dobString = LocalDate.parse(dob, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
		Period period = Period.between(dobString, LocalDate.now());
		int age = period.getYears();

		// only prove if over 18 years old
		if (age >= 18) {
			log.info("Age Over 18, Apply Saving Account Approved");
			boolean result = customerDao.applySavingAccount(id, number, dob, depostAmount);
			return result;
		} else {
			log.info("Age Less Than 18, Apply Saving Account Rejected");
			return false;
		}
	}

	// CHECK BALANCE
	public Customer getBalance(int id) {
		Customer cus = customerDao.getBalance(id);
		return cus;
	}

	// DEPOSIT MONEY TO CHECKING ACCOUNT
	public boolean depositToChecking() {
		if (CustomerLoginView.checkingID == 0) {
			log.info("You don't have a checking account, please apply first");
			return false;
		}
		log.info("\nWelcome to the deposit system");
		log.info("Please enter integer amount you want to deposit to your checking account");

		String amount = Sc.sc.nextLine();
		// line here can add a regEx in future to prevent invalid input
		int amountInt = Integer.parseInt(amount);
		if (amountInt <= 0) {
			log.info("amount can't be 0 or less");
			return false;
		} else {
			boolean result = customerDao.depositToChecking(amountInt);
			return result;
		}
	}

	// DEPOSIT MONEY TO SAVING ACCOUNT
	public boolean depositToSaving() {
		if (CustomerLoginView.savingID == 0) {
			log.info("You don't have a saving account, please apply first");
			return false;
		}
		log.info("\nWelcome to the deposit system");
		log.info("Please enter integer amount you want to deposit to your saving account");

		String amount = Sc.sc.nextLine();
		// line here can add a regEx in future to prevent invalid input
		int amountInt = Integer.parseInt(amount);
		if (amountInt <= 0) {
			log.info("amount can't be 0 or less");
			return false;
		} else {
			boolean result = customerDao.depositToSaving(amountInt);
			return result;
		}
	}

	// WITHDRAW MONEY FROM CHECKING ACCOUNT
	public void withdrawFromChecking() {

		log.info("How much you want to withdraw from your checking account?");
		int amount = Integer.parseInt(Sc.sc.nextLine());
		if (amount <= 0) {
			log.info("incorrect withdraw amount, please try later");
		} else {
			int balance = customerDao.getCheckingBalanceByAccountId();
			if (amount > balance) {
				log.info("So sorry guys, you don't have enough money inside you chekcing accout, please try later");
			} else {
				customerDao.withdrawFromChecking(amount, balance);
			}
		}

	}

	// USING LOGIN ID TO GET CHECKING ACCOUNT NUMBER
	public int getCheckingIDByLoginID() {
		int checkingID = customerDao.getCheckingIDByLoginID();
		return checkingID;
	}

	// USING LOGIN ID TO GET SAVING ACCOUNT NUMBER
	public int getSavingIDByLoginID() {
		int savingID = customerDao.getSavingIDByLoginID();
		return savingID;
	}

	// WITHDRAW MONEY FROM SAVING ACCOUNT
	public void withdrawFromSaving() {
		log.info("How much you want to withdraw from your saving account?");
		int amount = Integer.parseInt(Sc.sc.nextLine());
		if (amount <= 0) {
			log.info("incorrect withdraw amount, please try later");
		} else {
			int balance = customerDao.getSavingBalanceByAccountId();
			if (amount > balance) {
				log.info("So sorry guys, you don't have enough money inside you saving accout, please try later");
			} else {
				customerDao.withdrawFromSaving(amount, balance);
			}
		}

	}

	// TRANSFER MONEY
	public boolean transfer() {
		int customerIDYouWantToTransfer = 0; // store account number customer want to transfer
		String transferType = ""; // store account type customer want to transfer
		int amount = 0; // store amount customer want to transfer
		int balance = 0; // store balance of customer
		// 1. Get account number
		boolean flag = true;
		while (flag) {
			boolean flag2 = false;
			do {
				flag2 = false;
				log.info("Please Enter Customer Account Number You Want To Transfer To Or Enter -1 To Quit");
				try {
					customerIDYouWantToTransfer = Integer.parseInt(Sc.sc.nextLine());
				} catch (NumberFormatException e) {
					log.info("You entered incorrect format, please try again");
					flag2 = true;
				}
			} while (flag2);

			if (customerIDYouWantToTransfer == -1) {
				return false;
			} else {
				boolean isExist = customerDao.checkAccountExist(customerIDYouWantToTransfer);
				if (isExist) {
					flag = false;
				} else {
					log.info("Can't find the account number, please try again");
					flag = true;
				}
			}
		}

		// 3. Get transfer amount
		boolean flag6 = true;
		while (flag6) {
			boolean flag4 = false;
			do {
				flag4 = false;
				log.info("Please Enter Amount You Want To Transfer <ONLY INTEGER LIKE: 500 DOLLARS>");
				try {
					amount = Integer.parseInt(Sc.sc.nextLine());
					// if customer enter less than or equal 0 amount, and they need to enter again
					if (amount <= 0) {
						log.info("Transfer Amount Can't Be 0 Or Less Than 0");
						flag4 = true;
					}
				} catch (NumberFormatException e) {
					log.info("you entered incorrect amount, please try again");
					flag4 = true;// if customer enter incorrect number, and they need to enter again
				}
			} while (flag4);

			// 4. set transfer from which account, checking or saving and check balance if
			// enough to complete transaction
			boolean flag5 = true;
			while (flag5) {
				log.info("Please Choose What Type Of Account You Want To Use");
				log.info("Enter '1' To Transfer From Your Checking Account");
				log.info("Enter '2' To Transfer From Your Saving Account");
				String choice = Sc.sc.nextLine();

				switch (choice) {
				// transfer from checcking account
				case "1":
					if (CustomerLoginView.checkingID != 0) {
						// if balance >= transfer amount
						balance = customerDao.getCheckingBalanceByAccountId();
						if (amount > balance) {
							log.info("you don't have enough money in your checking account, please try less amount");
							break;
						} else {
							log.info("--------------------------------------------------");
							log.info("start to transfer money from your checking account");
							// 1. withdraw from checking account
							customerDao.withdrawFromChecking(amount, balance);
							// 2. deposit to customer account
							customerDao.depositToAccount(amount, customerIDYouWantToTransfer);
							flag5 = false;
						}
					} else {
						log.info("You don't have a checking account, please try next time");
					}
					break;
				// transfer from saving account
				case "2":
					if (CustomerLoginView.savingID != 0) {
						// if balance >= transfer amount
						balance = customerDao.getSavingBalanceByAccountId();
						if (amount > balance) {
							log.info("you don't have enough money in your checking account, please try less amount");
							break;
						} else {
							log.info("--------------------------------------------------");
							log.info("start to transfer money from your saving account");
							// 1. withdraw from saving account
							customerDao.withdrawFromSaving(amount, balance);
							// 2. deposit to customer account
							customerDao.depositToAccount(amount, customerIDYouWantToTransfer);
							flag5 = false;
						}
					} else {
						log.info("You don't have a saving account, please try next time");
					}
					break;
				// go and reenter again
				default:
					log.info("Please Enter Only 1 or 2 and try again");
				}
			}
			flag6 = false;
		}

		// end
		return false;
	}

	// USING LOGIN ID TO GET CUSTOMER ID
	public int getCusIdByLoginId() {
		int cusId = customerDao.getCusIdByLoginId();
		return cusId;
	}



}
