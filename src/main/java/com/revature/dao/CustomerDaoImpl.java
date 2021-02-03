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
	public void createCustomer(Customer cus) {
		//����1������жϳɹ�����
		System.out.println("going to customer dao impl");
		try (Connection connection = ConnectionUtil.getConnection()) {
			
			//Step 1: �����û��˺����룬�ɹ�������½�˻�
			String sql = "INSERT INTO banking.login(username, password) VALUES(?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, cus.getLogin().getUsername());
			preparedStatement.setString(2, cus.getLogin().getPassword());
			preparedStatement.executeUpdate();
			System.out.println(cus.getLogin().getUsername());
			
			//Step 2: ���ҵ�ǰ�˺Ų����ص�ǰ�˺ŵ�login_id
			String sql2 = "SELECT login_id FROM banking.login WHERE username=?";
			PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
			preparedStatement2.setString(1, cus.getLogin().getUsername());
			ResultSet resultSet = preparedStatement2.executeQuery();
			if(resultSet.next()) {
//				cus.getLogin().setLoginID(resultSet.getInt("login_id"));
				System.out.println("login_id is: " + resultSet.getInt("login_id"));
				int result = resultSet.getInt("login_id");
				cus.getLogin().setLoginID(result);
			}
			System.out.println(cus);
			
			//Step 3: �����û�������login_id�����ݿ�
			String sql3 = "INSERT INTO banking.customer(first_name, last_name, login_id) VALUES(?, ?, ?)";
			PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
			preparedStatement3.setString(1, cus.getFirstName());
			preparedStatement3.setString(2, cus.getLastName());
			preparedStatement3.setInt(3, cus.getLogin().getLoginID());
			preparedStatement3.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 

	}

}
