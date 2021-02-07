package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import com.revature.models.Account;
import com.revature.models.Customer;
import com.revature.ui.CustomerLoginView;
import com.revature.util.ConnectionUtil;

public class CustomerDaoImpl implements CustomerDao {

	private static Logger log = Logger.getLogger(CustomerDaoImpl.class);
	@Override
	public int createCustomer(Customer cus) {
		int count = 0;
		try (Connection connection = ConnectionUtil.getConnection()) {

			// Step 1: insert username password into login table
			String sql = "INSERT INTO banking.login(username, password) VALUES(?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, cus.getLogin().getUsername());
			preparedStatement.setString(2, cus.getLogin().getPassword());
			preparedStatement.executeUpdate();

			// Step 2: find login table where username equal to username and return login_id
			String sql2 = "SELECT login_id FROM banking.login WHERE username=?";
			PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
			preparedStatement2.setString(1, cus.getLogin().getUsername());
			ResultSet resultSet = preparedStatement2.executeQuery();
			if (resultSet.next()) {
				int result = resultSet.getInt("login_id");
				cus.getLogin().setLoginID(result);
			}

			// Step 3: insert first name, last name, login id to customer table
			String sql3 = "INSERT INTO banking.customer(first_name, last_name, login_id) VALUES(?, ?, ?)";
			PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
			preparedStatement3.setString(1, cus.getFirstName());
			preparedStatement3.setString(2, cus.getLastName());
			preparedStatement3.setInt(3, cus.getLogin().getLoginID());
			preparedStatement3.executeUpdate();

			// Step 4: we are finished creating a customer
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}

		return count;
	}

	@Override
	public int validatePassword(String username, String password) {
		int count = -1;
		// Step 1: 查找login table where username = username and password == password
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT login_id FROM banking.login WHERE username =? AND password = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			// Step 2: if yes return login_id
			if (resultSet.next()) {
				count = resultSet.getInt("login_id");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Step 3: if no return -1
		return count;
	}

	@Override
	public boolean applyCheckingAccount(int id, int number) {
		int accountID = 0;
		try (Connection connection = ConnectionUtil.getConnection()) {
			// 1. 插入数据到account table
			String sql = "INSERT INTO banking.account(account_number, account_type, balance) VALUES(?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, number);
			preparedStatement.setString(2, "checking");
			preparedStatement.setInt(3, 0);
			preparedStatement.executeUpdate();

			// 2. 找出当前创建的account_id
			String sql2 = "SELECT account_id FROM banking.account WHERE account_number = ?";
			PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
			preparedStatement2.setInt(1, number);
			ResultSet resultSet = preparedStatement2.executeQuery();
			if (resultSet.next()) {
				accountID = resultSet.getInt("account_id");
				CustomerLoginView.checkingID = accountID;
			}

			// 3. 修改customer中login_id为id的account_id
			String sql3 = "UPDATE banking.customer SET account_id_checking = ? WHERE login_id = ?";
			PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
			preparedStatement3.setInt(1, accountID);
			preparedStatement3.setInt(2, id);
			preparedStatement3.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 3. 找到log in id为id的用户，并更新account id

		return false;
	}

	@Override
	public boolean applySavingAccount(int id, int number) {
		int accountID = 0;
		try (Connection connection = ConnectionUtil.getConnection()) {
			// 1. 插入数据到account table
			String sql = "INSERT INTO banking.account(account_number, account_type, balance) VALUES(?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, number);
			preparedStatement.setString(2, "saving");
			preparedStatement.setInt(3, 0);
			preparedStatement.executeUpdate();

			// 2. 找出当前创建的account_id
			String sql2 = "SELECT account_id FROM banking.account WHERE account_number = ?";
			PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
			preparedStatement2.setInt(1, number);
			ResultSet resultSet = preparedStatement2.executeQuery();
			if (resultSet.next()) {
				accountID = resultSet.getInt("account_id");
				CustomerLoginView.savingID = accountID;
			}

			// 3. 修改customer中login_id为id的account_id
			String sql3 = "UPDATE banking.customer SET account_id_saving = ? WHERE login_id = ?";
			PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
			preparedStatement3.setInt(1, accountID);
			preparedStatement3.setInt(2, id);
			preparedStatement3.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 3. 找到log in id为id的用户，并更新account id

		return false;
	}

	@Override
	public Customer getBalance(int id) {
		Customer customer = null;
		Account account = null;
		try (Connection connection = ConnectionUtil.getConnection()) {
			// 1. 查找CHECKING资料，并保存
			String sql = "SELECT * FROM banking.account INNER JOIN banking.customer ON customer.account_id_checking = account.account_id WHERE customer.login_id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				account = new Account();
				account.setAccountNumberChecking(resultSet.getInt("account_number"));
				account.setAccountType("checking");
				account.setBalanceChecking(resultSet.getInt("balance"));
				account.setDateChecking(resultSet.getString("account_created_date"));
			}
			
			// 2. 查找SAVING资料，并保存
			String sql2 = "SELECT * FROM banking.account INNER JOIN banking.customer ON customer.account_id_saving = account.account_id WHERE customer.login_id = ?";
			PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
			preparedStatement2.setInt(1, id);
			ResultSet resultSet2 = preparedStatement2.executeQuery();
			if (resultSet2.next()) {
				account.setAccountNumberSaving(resultSet2.getInt("account_number"));
				account.setAccountType("saving");
				account.setBalanceSaving(resultSet2.getInt("balance"));
				account.setDateSaving(resultSet2.getString("account_created_date"));
			}
			
			// 3. 查找姓名资料
			String sql3 = "SELECT * FROM banking.customer WHERE login_id = ?";
			PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
			preparedStatement3.setInt(1, id);
			ResultSet resultSet3 = preparedStatement3.executeQuery();
			if (resultSet3.next()) {
				String firstName = resultSet3.getString("first_name");
				String lastName = resultSet3.getString("last_name");
				customer = new Customer(firstName, lastName, account);
			}
			return customer;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public boolean depositToChecking(int amountInt) {
		int checking_id = 0;
		int balance = 0;
		
		try (Connection connection = ConnectionUtil.getConnection()) {
			// 1. get checking account id
			String sql = "SELECT account_id_checking FROM banking.customer WHERE login_id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, CustomerLoginView.id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				checking_id = resultSet.getInt("account_id_checking");
			} else {
				return false;
			}

			// 3. get balance of checking account
			String sql2 = "SELECT balance FROM banking.account WHERE account_id = ?";
			PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
			preparedStatement2.setInt(1, checking_id);
			ResultSet resultSet2 = preparedStatement2.executeQuery();
			if (resultSet2.next()) {
				balance = resultSet2.getInt("balance");
			}
			balance = balance + amountInt;

			// 2. update customer checking balance
			String sql3 = "UPDATE banking.account SET balance = ? WHERE account_id = ?";
			PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
			preparedStatement3.setInt(1, balance);
			preparedStatement3.setInt(2, checking_id);
			int a = preparedStatement3.executeUpdate();
			if(a == 1) {
				log.info("Successful deposit amount " + amountInt + " dollars in to your checking account");
				log.info("Your checking acount balance now: " + balance + " dollars");
			}
		} catch (SQLException e) {
			log.info("Transaction failed");
		}

		return true;

	}

	@Override
	public boolean depositToSaving(int amountInt) {
		int saving_id = 0;
		int balance = 0;
		
		try (Connection connection = ConnectionUtil.getConnection()) {
			// 1. get saving account id
			String sql = "SELECT account_id_saving FROM banking.customer WHERE login_id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, CustomerLoginView.id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				saving_id = resultSet.getInt("account_id_saving");
			} else {
				return false;
			}

			// 3. get balance of saving account 
			String sql2 = "SELECT balance FROM banking.account WHERE account_id = ?";
			PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
			preparedStatement2.setInt(1, saving_id);
			ResultSet resultSet2 = preparedStatement2.executeQuery();
			if (resultSet2.next()) {
				balance = resultSet2.getInt("balance");
			}
			balance = balance + amountInt;

			// 2. update account id balance
			String sql3 = "UPDATE banking.account SET balance = ? WHERE account_id = ?";
			PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
			preparedStatement3.setInt(1, balance);
			preparedStatement3.setInt(2, saving_id);
			int a = preparedStatement3.executeUpdate();
			
			if(a == 1) {
				log.info("Successful deposit amount " + amountInt + " dollars in to your saving account");
				log.info("Your saving acount balance now: " + balance + " dollars");
			}
		} catch (SQLException e) {
			log.info("Transaction failed");
		}

		return true;
	}

	@Override
	public int getCheckingIDByLoginID() {
		int checking_id = 0;
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT account_id_checking FROM banking.customer WHERE login_id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, CustomerLoginView.id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				checking_id = resultSet.getInt("account_id_checking");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return checking_id;
	}

	@Override
	public int getSavingIDByLoginID() {
		int saving_id = 0;
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT account_id_saving FROM banking.customer WHERE login_id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, CustomerLoginView.id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return saving_id = resultSet.getInt("account_id_saving");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return saving_id;
	}

	@Override
	public void withdrawFromChecking(int amount, int balance) {

		int result = balance - amount;

		try (Connection connection = ConnectionUtil.getConnection()) {
			// Update balance inside account talbe
			String sql = "UPDATE banking.account SET balance = ? WHERE account_id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, result);
			preparedStatement.setInt(2, CustomerLoginView.checkingID);
			int success = preparedStatement.executeUpdate();
			if (success == 1) {
				log.info("Successfully withdraw " + amount + " from your checking account");
				log.info("Your checking account balance => " + result + " dollars");
			} else {
				log.info("Transaction failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public int getCheckingBalanceByAccountId() {
		int balance = 0;
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT balance FROM banking.account WHERE account_id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, CustomerLoginView.checkingID);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return balance = resultSet.getInt("balance");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int getSavingBalanceByAccountId() {
		int balance = 0;
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT balance FROM banking.account WHERE account_id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, CustomerLoginView.savingID);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return balance = resultSet.getInt("balance");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public boolean checkAccountExist(int customerIDYouWantToTransfer) {
		boolean exist = false;

		try (Connection connection = ConnectionUtil.getConnection()) {
			// Step 1: find out account_id of that transfer account number
			String sql = "SELECT account_id From banking.account Where account_number = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, customerIDYouWantToTransfer);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				exist = true;
				return exist;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exist;
	}

	@Override
	public void depositToAccount(int amount, int customerIDYouWantToTransfer) {
		//get balance first
		int balance = 0;
		try(Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT balance FROM banking.account WHERE account_number = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, customerIDYouWantToTransfer);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				balance = resultSet.getInt("balance");
				balance = balance + amount;
				//update balance
				String sql2 = "UPDATE banking.account SET balance = ? WHERE account_number = ?";
				PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
				preparedStatement2.setInt(1, balance);
				preparedStatement2.setInt(2, customerIDYouWantToTransfer);
				int a = preparedStatement2.executeUpdate();
				if(a == 1) {
					log.info("Transfer to account " + customerIDYouWantToTransfer + " successfully.");
					log.info("Transfer amount: " + amount);
					log.info("--------------------------------------------------");
				}else {
					log.info("Transaction Failed");
				}
			}
			
		} catch (SQLException e) {
			
		}
	}

	@Override
	public void withdrawFromSaving(int amount, int balance) {
		int result = balance - amount;

		try (Connection connection = ConnectionUtil.getConnection()) {
			// Update balance inside account talbe
			String sql = "UPDATE banking.account SET balance = ? WHERE account_id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, result);
			preparedStatement.setInt(2, CustomerLoginView.savingID);
			int success = preparedStatement.executeUpdate();
			if (success == 1) {
				log.info("Successfully withdraw " + amount + " from your saving account");
				log.info("Your saving account balance " + result);
			} else {
				log.info("Transaction failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
