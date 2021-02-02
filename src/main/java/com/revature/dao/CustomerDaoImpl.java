package com.revature.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.revature.ui.Customer;
import com.revature.util.ConnectionUtil;

public class CustomerDaoImpl implements CustomerDao {

	@Override
	public void createCustomer(Customer cus) {
		
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO banking.customer(first_name, last_name) VALUES(?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, cus.getFirstName());
			preparedStatement.setString(2, cus.getLastName());
			
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.getMessage();
		} 

	}

}
