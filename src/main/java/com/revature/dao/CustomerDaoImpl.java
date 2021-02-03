package com.revature.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.Customer;
import com.revature.util.ConnectionUtil;

public class CustomerDaoImpl implements CustomerDao {

	@Override
	public int createCustomer(Customer cus) {
		int count = 0;
		try (Connection connection = ConnectionUtil.getConnection()) {
			
			//Step 1: insert username password into login table
			String sql = "INSERT INTO banking.login(username, password) VALUES(?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, cus.getLogin().getUsername());
			preparedStatement.setString(2, cus.getLogin().getPassword());
			preparedStatement.executeUpdate();
			
			//Step 2: find login table where username equal to username and return login_id
			String sql2 = "SELECT login_id FROM banking.login WHERE username=?";
			PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
			preparedStatement2.setString(1, cus.getLogin().getUsername());
			ResultSet resultSet = preparedStatement2.executeQuery();
			if(resultSet.next()) {
				int result = resultSet.getInt("login_id");
				cus.getLogin().setLoginID(result);
			}

			//Step 3: insert first name, last name, login id to customer table
			String sql3 = "INSERT INTO banking.customer(first_name, last_name, login_id) VALUES(?, ?, ?)";
			PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
			preparedStatement3.setString(1, cus.getFirstName());
			preparedStatement3.setString(2, cus.getLastName());
			preparedStatement3.setInt(3, cus.getLogin().getLoginID());
			preparedStatement3.executeUpdate();
			
			//Step 4: we are finished creating a customer
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} 
		
		return count;
	}

}
