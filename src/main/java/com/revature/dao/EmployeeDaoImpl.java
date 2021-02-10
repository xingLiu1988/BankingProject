package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.models.Account;
import com.revature.models.Customer;
import com.revature.models.Transaction;
import com.revature.util.ConnectionUtil;

public class EmployeeDaoImpl implements EmployeeDao {
	private static Logger log = Logger.getLogger(EmployeeDaoImpl.class);

	@Override
	public List<Customer> viewAllCustomersAccount() {
		List<Customer> list = new ArrayList<>();
		Customer customer;
		Account account;
		String type = "";

		try (Connection connection = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM  banking.customer LEFT JOIN banking.account ON customer.account_id_checking = account.account_id OR customer.account_id_saving = account.account_id";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				customer = new Customer();
				account = new Account();
				type = resultSet.getString("account_type");
				// see what type of account is
				if (type != null) {
					if (type.equals("checking")) {
						account.setAccountNumberChecking(resultSet.getInt("account_number"));
						account.setBalanceChecking(resultSet.getDouble("balance"));
						account.setDateChecking(resultSet.getString("account_created_date"));
						account.setAccountType("checking");
					} else {
						account.setAccountNumberSaving(resultSet.getInt("account_number"));
						account.setBalanceSaving(resultSet.getDouble("balance"));
						account.setDateSaving(resultSet.getString("account_created_date"));
						account.setAccountType("saving");
					}
					customer.setAccount(account);
					customer.setFirstName(resultSet.getString("first_name"));
					customer.setLastName(resultSet.getString("last_name"));
					customer.setCustomerID(resultSet.getInt("customer_id"));
				} else {
					customer.setFirstName(resultSet.getString("first_name"));
					customer.setLastName(resultSet.getString("last_name"));
					customer.setCustomerID(resultSet.getInt("customer_id"));
				}
				list.add(customer);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Customer> viewSingleCustomerAccount(int customerID) {
		List<Customer> list = new ArrayList<>();
		Customer customer = null;
		Account account;
		String type = "";
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM  banking.customer LEFT JOIN banking.account ON customer.account_id_checking = account.account_id OR customer.account_id_saving = account.account_id WHERE customer_id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, customerID);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				customer = new Customer();
				account = new Account();
				type = resultSet.getString("account_type");

				if (type != null) {
					if (type.equals("checking")) {
						account.setAccountNumberChecking(resultSet.getInt("account_number"));
						account.setBalanceChecking(resultSet.getDouble("balance"));
						account.setDateChecking(resultSet.getString("account_created_date"));
						account.setAccountType("checking");
					} else {
						account.setAccountNumberSaving(resultSet.getInt("account_number"));
						account.setBalanceSaving(resultSet.getDouble("balance"));
						account.setDateSaving(resultSet.getString("account_created_date"));
						account.setAccountType("saving");
					}
					customer.setAccount(account);
					customer.setFirstName(resultSet.getString("first_name"));
					customer.setLastName(resultSet.getString("last_name"));
					customer.setCustomerID(resultSet.getInt("customer_id"));
				} else {
					customer.setFirstName(resultSet.getString("first_name"));
					customer.setLastName(resultSet.getString("last_name"));
					customer.setCustomerID(resultSet.getInt("customer_id"));
				}

				list.add(customer);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 删除指定用户
	@Override
	public int deleteCustomerByAccountNumber(int accountNumber) {
		int result = 0;
		int accountID = 0;
		String accountType = "";
		try (Connection connection = ConnectionUtil.getConnection()) {

			// 1. 获取当前账户的account_id
			String sql = "SELECT account_id, account_type FROM banking.account WHERE account_number = ?";
			PreparedStatement p = connection.prepareStatement(sql);
			p.setInt(1, accountNumber);
			ResultSet r = p.executeQuery();

			if (r.next()) {
				accountID = r.getInt("account_id");
				accountType = r.getString("account_type");
			} else {
				return result;
			}

			// 2. 更新checking id
			if (accountType.equals("checking")) {
				String sql2 = "UPDATE banking.customer SET account_id_checking = NULL WHERE account_id_checking = ?";
				PreparedStatement p2 = connection.prepareStatement(sql2);
				p2.setInt(1, accountID);
				p2.executeUpdate();
			} else {
				String sql3 = "UPDATE banking.customer SET account_id_saving = NULL WHERE account_id_saving = ?";
				PreparedStatement p3 = connection.prepareStatement(sql3);
				p3.setInt(1, accountID);
				p3.executeUpdate();
			}

			// 3. 删除account
			String sql4 = "DELETE FROM banking.account WHERE account_id = ?";
			PreparedStatement p4 = connection.prepareStatement(sql4);
			p4.setInt(1, accountID);
			int p4Result = p4.executeUpdate();
			return p4Result;
		} catch (SQLException e) {
			log.info("Delete Account Number Failed");
		}

		return result;
	}

	@Override
	public List<Transaction> viewAllTransactions() {
		List<Transaction> list = new ArrayList<>();
		
		try(Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM banking.transaction";
			PreparedStatement p = connection.prepareStatement(sql);
			ResultSet r = p.executeQuery();
			while(r.next()) {
				Transaction t = new Transaction();
				t.setTransID(r.getInt("trans_id"));
				t.setTransType(r.getString("trans_type"));
				t.setTransAccountType(r.getString("trans_account_type"));
				t.setTransAmount(r.getInt("trans_amount"));
				t.setTransDate(r.getString("trans_Date").substring(0, 19));
				list.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

}
