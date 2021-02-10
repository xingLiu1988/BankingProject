package com.revature.util;

import com.revature.dao.TransactionDao;
import com.revature.dao.TransactionDaoImpl;

public class TransactionUtil {

	// USED TO SENT EACH TRANSACTION TO THE DATABASE
	public static void send(int cusId, String transType, String transAccountType, int transAmount) {
		TransactionDao t = new TransactionDaoImpl();
		t.sendTransaction(cusId, transType, transAccountType, transAmount);
	}
	
}
