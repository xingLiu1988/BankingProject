package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.revature.util.ConnectionUtil;


public class TransactionDaoImpl implements TransactionDao {
	private static Logger log = Logger.getLogger(TransactionDaoImpl.class);

	@Override
	public boolean sendTransaction(int cusID, String transType, String transAccountType, int transAmount) {

		try(Connection connection = ConnectionUtil.getConnection()) {
			connection.setAutoCommit(false);
			
			String sql = "INSERT INTO banking.transaction(customer_id, trans_type, trans_account_type, trans_amount) VALUES(?,?,?,?)";
			
			PreparedStatement p = connection.prepareStatement(sql);
			p.setInt(1, cusID);
			p.setString(2, transType);
			p.setString(3, transAccountType);
			p.setInt(4, transAmount);
			
			int result = p.executeUpdate();
			
			if(result == 1) {
				connection.setAutoCommit(true);
				log.info("One Transaction Established");
			}else {
				log.info("Transaction Insert Failed");
			}
		} catch (SQLException e) {
			log.info("Insert Transaction Failed");
		}

		return false;
	}

}
