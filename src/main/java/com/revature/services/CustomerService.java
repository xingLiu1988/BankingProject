package com.revature.services;

import org.apache.log4j.Logger;
import com.revature.dao.CustomerDao;
import com.revature.dao.CustomerDaoImpl;
import com.revature.models.Customer;
import com.revature.ui.CustomerLoginView;
import com.revature.util.Sc;

public class CustomerService {
	private static Logger log = Logger.getLogger(CustomerService.class);
	public CustomerDao customerDao;

	public CustomerService() {
		customerDao = new CustomerDaoImpl();
	}

	// 1 �����û�
	public void createCustomer(Customer cus) {
		int count = customerDao.createCustomer(cus);
		if (count == 0) {
			log.info("\nUser created successfully\n");
		} else {
			log.info("\nFailed to create user. Please try again later\n");
		}
	}

	// 2 �ж��û����������Ƿ���ȷ
	public int validatePassword(String username, String password) {

		int result = customerDao.validatePassword(username, password);

		return result;
	}

	// 3 ����֧Ʊ�˻�
	public boolean applyCheckingAccount(int id) {
		// account number
		int number = (int) Math.floor(Math.random() * 88888888 + 11111110);
		boolean result = customerDao.applyCheckingAccount(id, number);

		return result;
	}

	// 4 ���������˻�
	public boolean applySavingAccount(int id) {
		int number = (int) Math.floor(Math.random() * 88888888 + 11111110);
		boolean result = customerDao.applySavingAccount(id, number);

		return result;
	}

	// 5 �鿴���
	public Customer getBalance(int id) {
		Customer cus = customerDao.getBalance(id);
		return cus;
	}

	// ��Ǯ��֧Ʊ�˻�
	public boolean depositToChecking() {
		if(CustomerLoginView.checkingID == 0) {
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

	public boolean depositToSaving() {
		if(CustomerLoginView.savingID == 0) {
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

	public int getCheckingIDByLoginID() {
		int checkingID = customerDao.getCheckingIDByLoginID();
		return checkingID;
	}

	public int getSavingIDByLoginID() {
		int savingID = customerDao.getSavingIDByLoginID();
		return savingID;
	}

	public void withdrawFromSaving() {
		log.info("How much you want to withdraw from your saving account?");
		int amount = Integer.parseInt(Sc.sc.nextLine());
		if (amount <= 0) {
			log.info("incorrect withdraw amount, please try later");
		} else {
			int balance = customerDao.getSavingBalanceByAccountId();
			if (amount > balance) {
				log.info("So sorry guys, you don't have enough money inside you chekcing accout, please try later");
			} else {
				customerDao.withdrawFromSaving(amount, balance);
			}
		}

	}

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
						flag4 = false;
					}
				} catch (NumberFormatException e) {
					log.info("you entered incorrect amount, please try again");
					flag4 = true;// if customer enter incorrect number, and they need to enter again
				}
			} while (flag4);

			// 4. set transfer from which account, checking or saving and check balance if enough to complete transaction
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
							customerDao.depositToAccount(amount,customerIDYouWantToTransfer);
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
							customerDao.depositToAccount(amount,customerIDYouWantToTransfer);
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

}
