package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.Customer;
import com.revature.models.Login;
import com.revature.util.ConnectionUtil;

public class EmployeeDaoImpl implements EmployeeDao{

	@Override
	public List<Customer> viewAllCustomersAccount() {
		List<Customer> list = new ArrayList<>();
		Customer customer;
		Account account;
		String type = "";
		try (Connection connection = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM  banking.customer LEFT JOIN banking.account ON customer.account_id_checking = account.account_id OR customer.account_id_saving = account.account_id";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet =  preparedStatement.executeQuery();
			while(resultSet.next()) {
				customer = new Customer();
				account = new Account();
				type = resultSet.getString("account_type");
				if(type.equals("checking")) {
					account.setAccountNumberChecking(resultSet.getInt("account_number"));
					account.setBalanceChecking(resultSet.getDouble("balance"));
					account.setDateChecking(resultSet.getString("account_created_date"));
					account.setAccountType("checking");
				}else {
					account.setAccountNumberSaving(resultSet.getInt("account_number"));
					account.setBalanceSaving(resultSet.getDouble("balance"));
					account.setDateSaving(resultSet.getString("account_created_date"));
					account.setAccountType("saving");
				}
				customer.setAccount(account);
				customer.setFirstName(resultSet.getString("first_name"));
				customer.setLastName(resultSet.getString("last_name"));
				customer.setCustomerID(resultSet.getInt("customer_id"));
				
				list.add(customer);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List viewSingleCustomerAccount(int customerID) {
		List<Customer> list = new ArrayList<>();
		Customer customer = null;
		Account account;
		String type = "";
		try (Connection connection = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM  banking.customer LEFT JOIN banking.account ON customer.account_id_checking = account.account_id OR customer.account_id_saving = account.account_id WHERE customer_id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, customerID);
			ResultSet resultSet =  preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				customer = new Customer();
				account = new Account();
				type = resultSet.getString("account_type");
				if(type.equals("checking")) {
					account.setAccountNumberChecking(resultSet.getInt("account_number"));
					account.setBalanceChecking(resultSet.getDouble("balance"));
					account.setDateChecking(resultSet.getString("account_created_date"));
					account.setAccountType("checking");
				}else {
					account.setAccountNumberSaving(resultSet.getInt("account_number"));
					account.setBalanceSaving(resultSet.getDouble("balance"));
					account.setDateSaving(resultSet.getString("account_created_date"));
					account.setAccountType("saving");
				}
				customer.setAccount(account);
				customer.setFirstName(resultSet.getString("first_name"));
				customer.setLastName(resultSet.getString("last_name"));
				customer.setCustomerID(resultSet.getInt("customer_id"));
				
				list.add(customer);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean deleteCustomerByCustomerID(int customerID) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
