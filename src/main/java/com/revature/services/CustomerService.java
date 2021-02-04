package com.revature.services;

import java.nio.charset.MalformedInputException;
import java.util.InputMismatchException;

import org.apache.log4j.Logger;

import com.revature.dao.CustomerDao;
import com.revature.dao.CustomerDaoImpl;
import com.revature.models.Customer;
import com.revature.ui.MainMenu;
import com.revature.util.Sc;

public class CustomerService {
	private static Logger log = Logger.getLogger(CustomerService.class);
	public CustomerDao customerDao;
	
	public CustomerService() {
		customerDao = new CustomerDaoImpl();
	}
	
	//1 创建用户
	public void createCustomer(Customer cus) {
		int count = customerDao.createCustomer(cus);
		if(count == 0) {
			log.info("\nUser created successfully\n");
		}else {
			log.info("\nFailed to create user. Please try again later\n");
		}
	}
	
	//2 判断用户名和密码是否正确
	public int validatePassword(String username, String password) {
		
		int result = customerDao.validatePassword(username, password);
		
		return result;
	}

	//3 申请支票账户
	public boolean applyCheckingAccount(int id) {
		//account number
		int number = (int) Math.floor(Math.random()*99999999);
		boolean result = customerDao.applyCheckingAccount(id, number);
		
		
		
		return result;
	}
	
	//4 申请贮蓄账户
	public boolean applySavingAccount(int id) {
		int number = (int) Math.floor(Math.random()*99999999);
		boolean result = customerDao.applySavingAccount(id, number);
		
		return result;
	}
	
	//5 查看余额
	public Customer getBalance(int id) {
		Customer cus = customerDao.getBalance(id);
		return cus;
	}

	//存钱到支票账户
	public boolean depositToChecking() {
		log.info("\nWelcome to the deposit system");
		log.info("Please enter integer amount you want to deposit to your checking account");
		
		String amount = Sc.sc.nextLine();
		//line here can add a regEx in future to prevent invalid input
		int amountInt = Integer.parseInt(amount);
		if(amountInt <= 0) {
			log.info("amount can't be 0 or less");
			return false;
		}else {
			boolean result = customerDao.depositToChecking(amountInt);
			return result;
		}
	}

	public boolean depositToSaving() {
		
		log.info("\nWelcome to the deposit system");
		log.info("Please enter integer amount you want to deposit to your saving account");
		
		String amount = Sc.sc.nextLine();
		//line here can add a regEx in future to prevent invalid input
		int amountInt = Integer.parseInt(amount);
		if(amountInt <= 0) {
			log.info("amount can't be 0 or less");
			return false;
		}else {
			boolean result = customerDao.depositToSaving(amountInt);
			return result;
		}
	}

	public void withdrawFromChecking() {
		
		log.info("How much you want to withdraw from your checking account?");
		int amount = Integer.parseInt(Sc.sc.nextLine());
		if(amount <= 0) {
			log.info("incorrect withdraw amount, please try later");
		}else {
			int balance = customerDao.getCheckingBalanceByAccountId();
			if(amount > balance) {
				log.info("So sorry guys, you don't have enough money inside you chekcing accout, please try later");
			}else {
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
		if(amount <= 0) {
			log.info("incorrect withdraw amount, please try later");
		}else {
			int balance = customerDao.getSavingBalanceByAccountId();
			if(amount > balance) {
				log.info("So sorry guys, you don't have enough money inside you chekcing accout, please try later");
			}else {
				customerDao.withdrawFromChecking(amount, balance);				
			}
		}
		
	}
	
}
