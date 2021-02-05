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

	// 1 创建用户
	public void createCustomer(Customer cus) {
		int count = customerDao.createCustomer(cus);
		if (count == 0) {
			log.info("\nUser created successfully\n");
		} else {
			log.info("\nFailed to create user. Please try again later\n");
		}
	}

	// 2 判断用户名和密码是否正确
	public int validatePassword(String username, String password) {

		int result = customerDao.validatePassword(username, password);

		return result;
	}

	// 3 申请支票账户
	public boolean applyCheckingAccount(int id) {
		// account number
		int number = (int) Math.floor(Math.random() * 99999999);
		boolean result = customerDao.applyCheckingAccount(id, number);

		return result;
	}

	// 4 申请贮蓄账户
	public boolean applySavingAccount(int id) {
		int number = (int) Math.floor(Math.random() * 99999999);
		boolean result = customerDao.applySavingAccount(id, number);

		return result;
	}

	// 5 查看余额
	public Customer getBalance(int id) {
		Customer cus = customerDao.getBalance(id);
		return cus;
	}

	// 存钱到支票账户
	public boolean depositToChecking() {
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
				customerDao.withdrawFromChecking(amount, balance);
			}
		}

	}

	public boolean transfer() {
		int customerIDYouWantToTransfer = 0; // store account number customer want to transfer
		String transferType = ""; // store account type customer want to transfer
		int amount = 0; // store amount customer want to transfer
		int balance = 0; // store balance of customer
		// 1. 获取打算存入账户的账号Get account number
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
					flag = true;
				}
			}
		}

		// 2. 请选择对方账户类型Get account type
//		boolean flag3 = true;
//		while (flag3) {
//			log.info("Please Choose What Type Of Account You Want To Transfer To");
//			log.info("Enter '1' To Transfer To Checking Account");
//			log.info("Enter '2' To Transfer To Saving Account");
//			String choice = Sc.sc.nextLine();
//
//			switch (choice) {
//			case "1":
//				transferType = "checking";
//				flag3 = false;
//				break;
//			case "2":
//				transferType = "saving";
//				flag3 = false;
//				break;
//			default:
//				log.info("Please Enter Only 1 or 2 and try again");
//			}
//		}

		// 3. 获取转账金额Get transfer amount
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

			// 4. 获取用户需要转账的账户类型，并判断钱是否足够
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
						// 查看用户金额是否大于等于转账金额
						balance = customerDao.getCheckingBalanceByAccountId();
						if (amount > balance) {
							log.info("you don't have enough money in your checking account, please try less amount");
							break;
						} else {
							log.info("---start to transfer money from your checking account---");
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
						// 查看用户金额是否大于等于转账金额
						balance = customerDao.getSavingBalanceByAccountId();
						if (amount > balance) {
							log.info("you don't have enough money in your checking account, please try less amount");
							break;
						} else {
							log.info("---start to transfer money from your checking account---");
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
