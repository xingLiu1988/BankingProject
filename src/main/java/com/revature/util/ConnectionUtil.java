package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.Driver;

public class ConnectionUtil {
	private ConnectionUtil() {
		super();
	}

	// USED TO GET CONNECTION TO DATABASE
	public static Connection getConnection() {

		Connection connection = null;

		try {
			DriverManager.registerDriver(new Driver());

			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "password");

		} catch (SQLException e) {
			e.getMessage();
		}

		return connection;
	}
}
