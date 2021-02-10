package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.revature.exceptions.AccountIsNotFoundException;
import com.revature.exceptions.InvalidUserPasswrodException;
import com.revature.models.Account;
import com.revature.models.Customer;
import com.revature.ui.CustomerLoginView;
import com.revature.util.ConnectionUtil;
import com.revature.util.TransactionUtil;

public class CustomerDaoImpl implements CustomerDao {

	private static Logger log = Logger.getLogger(CustomerDaoImpl.class);
	
	// CREATE AN CUSTOMER
	@Override
	public int createCustomer(Customer cus) {
		int count = 0;
		try (Connection connection = ConnectionUtil.getConnection()) {
			connection.setAutoCommit(false);
			
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
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}

		return count;
	}

	// VALIDATE CUSTOMER USERNAME AND PASSWORD
	@Override
	public int validatePassword(String username, String password) {
		int count = -1;
		// Step 1: find login table where username = username and password == password
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT login_id FROM banking.login WHERE username =? AND password = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			// Step 2: if yes return login_id
			if (resultSet.next()) {
				count = resultSet.getInt("login_id");
			}else {
				throw new InvalidUserPasswrodException("Invalide Username Or Password");
			}

		} catch (SQLException | InvalidUserPasswrodException e) {
			log.info(e.getMessage());
			log.debug(e.getMessage());
		}
		// Step 3: if no return -1
		return count;
	}

	// APPLY FOR CHECKING ACCOUNT
	@Override
	public boolean applyCheckingAccount(int id, int number, String dob, int initialDepositAmount) {
		int accountID = 0;
		try (Connection connection = ConnectionUtil.getConnection()) {
			connection.setAutoCommit(false);
			
			// 1. insert data into account table
			String sql = "INSERT INTO banking.account(account_number, account_type, balance) VALUES(?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, number);
			preparedStatement.setString(2, "checking");
			preparedStatement.setInt(3, initialDepositAmount);
			preparedStatement.executeUpdate();
			log.debug("INSERT INTO banking.account(account_number, account_type, balance) VALUES(?, ?, ?)");
			// 2. find current account_id
			String sql2 = "SELECT account_id FROM banking.account WHERE account_number = ?";
			PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
			preparedStatement2.setInt(1, number);
			ResultSet resultSet = preparedStatement2.executeQuery();
			if (resultSet.next()) {
				accountID = resultSet.getInt("account_id");
				CustomerLoginView.checkingID = accountID;
			}
			log.debug("SELECT account_id FROM banking.account WHERE account_number = ?");
			// 3. edit customer login_id为id的account_id_checking and update dob
			String sql3 = "UPDATE banking.customer SET account_id_checking = ?, dob = ? WHERE login_id = ?";
			PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
			preparedStatement3.setInt(1, accountID);
			preparedStatement3.setString(2, dob);
			preparedStatement3.setInt(3, id);
			preparedStatement3.executeUpdate();
			log.debug("UPDATE banking.customer SET account_id_checking = ?, dob = ? WHERE login_id = ?");
			connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			log.info("connection error");
		}
		
		return false;
	}

	// APPLY FOR SAVING ACCOUNT
	@Override
	public boolean applySavingAccount(int id, int number, String dob, int initialDepositAmount) {
		int accountID = 0;
		try (Connection connection = ConnectionUtil.getConnection()) {
			connection.setAutoCommit(false);
			
			// 1. insert into account table
			String sql = "INSERT INTO banking.account(account_number, account_type, balance) VALUES(?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, number);
			preparedStatement.setString(2, "saving");
			preparedStatement.setInt(3, initialDepositAmount);
			preparedStatement.executeUpdate();
			log.debug("INSERT INTO banking.account(account_number, account_type, balance) VALUES(?, ?, ?)");
			
			// 2. get account_id
			String sql2 = "SELECT account_id FROM banking.account WHERE account_number = ?";
			PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
			preparedStatement2.setInt(1, number);
			ResultSet resultSet = preparedStatement2.executeQuery();
			if (resultSet.next()) {
				accountID = resultSet.getInt("account_id");
				CustomerLoginView.savingID = accountID;
			}
			log.debug("SELECT account_id FROM banking.account WHERE account_number = ?");
			
			// 3. edit customer中login_id为id的account_id
			String sql3 = "UPDATE banking.customer SET account_id_saving = ?, dob = ? WHERE login_id = ?";
			PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
			preparedStatement3.setInt(1, accountID);
			preparedStatement3.setString(2, dob);
			preparedStatement3.setInt(3, id);
			preparedStatement3.executeUpdate();
			log.debug("UPDATE banking.customer SET account_id_saving = ?, dob = ? WHERE login_id = ?");
			
			connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			log.info("connection error");
		}

		return false;
	}

	// GET BALANCE FROM ACCOUNT
	@Override
	public Customer getBalance(int id) {
		Customer customer = null;
		Account account = null;
		try (Connection connection = ConnectionUtil.getConnection()) {
			// 1. find all checking account data and save
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
			log.debug("SELECT * FROM banking.account INNER JOIN banking.customer ON customer.account_id_checking = account.account_id WHERE customer.login_id = ?");
			
			// 2. find saving data and save
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
			log.debug("SELECT * FROM banking.account INNER JOIN banking.customer ON customer.account_id_saving = account.account_id WHERE customer.login_id = ?");
			
			
			// 3. get customer
			String sql3 = "SELECT * FROM banking.customer WHERE login_id = ?";
			PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
			preparedStatement3.setInt(1, id);
			ResultSet resultSet3 = preparedStatement3.executeQuery();
			if (resultSet3.next()) {
				String firstName = resultSet3.getString("first_name");
				String lastName = resultSet3.getString("last_name");
				customer = new Customer(firstName, lastName, account);
				log.debug("SELECT * FROM banking.customer WHERE login_id = ?");
			}
			return customer;
		} catch (SQLException e) {
			log.info("connection error");
		}
		return customer;
	}

	// DEPOSIT MONEY INTO CHECKING ACCOUNT
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
				log.debug("get checking account id");
			} else {
				log.debug("doesn't get checking account id");
				return false;
			}
			
			// 3. get balance of checking account
			String sql2 = "SELECT balance FROM banking.account WHERE account_id = ?";
			PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
			preparedStatement2.setInt(1, checking_id);
			ResultSet resultSet2 = preparedStatement2.executeQuery();
			if (resultSet2.next()) {
				balance = resultSet2.getInt("balance");
				log.debug("get balance from checking account");
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
				TransactionUtil.send(CustomerLoginView.cusID, "deposit", "toChecking", amountInt);
				log.debug("update customer checking balance");
			}
		} catch (SQLException e) {
			log.info("Transaction failed");
			log.debug("Transaction failed");
		}

		return true;

	}

	// DEPOSIT MONEY TO SAVING ACCOUNT
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
				log.debug("get saving account id");
			} else {
				log.debug("doesn't get saving account id");
				return false;
			}

			// 3. get balance of saving account 
			String sql2 = "SELECT balance FROM banking.account WHERE account_id = ?";
			PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
			preparedStatement2.setInt(1, saving_id);
			ResultSet resultSet2 = preparedStatement2.executeQuery();
			if (resultSet2.next()) {
				balance = resultSet2.getInt("balance");
				log.debug("get balance from saving");
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
				TransactionUtil.send(CustomerLoginView.cusID, "deposit", "toSaving", amountInt);
				log.debug("update account id balance");
			}
		} catch (SQLException e) {
			log.info("Transaction failed");
			log.debug("Transaction failed");
		}

		return true;
	}

	// USED TO GET CHECKING ACCOUNT NUMBER BY USING LOGIN ID
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
				log.debug("get checking id by login id success");
			}
		} catch (SQLException e) {
			log.debug("SQLException");
		}
		return checking_id;
	}

	// USED TO GET SAVING ACCOUNT NUMBER BY USING LOGIN ID
	@Override
	public int getSavingIDByLoginID() {
		int saving_id = 0;
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT account_id_saving FROM banking.customer WHERE login_id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, CustomerLoginView.id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				log.debug("get saving id by login id success");
				return saving_id = resultSet.getInt("account_id_saving");
			}
		} catch (SQLException e) {
			log.debug("SQLException");
		}
		return saving_id;
	}

	// WITHDRAW MONEY FROM CHECKING MONEY
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
				TransactionUtil.send(CustomerLoginView.cusID, "withdraw", "fromChecking", amount);
				log.debug("withdral balance from account success");
			} else {
				log.info("Transaction failed");
				log.debug("Transaction failed");
			}
		} catch (Exception e) {
			log.debug("Something wrong with withdraw");
		}

	}

	// GET CHECKING BALANCE BY USING ACCOUNT ID
	@Override
	public int getCheckingBalanceByAccountId() {
		int balance = 0;
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT balance FROM banking.account WHERE account_id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, CustomerLoginView.checkingID);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				log.debug("get balance from checking");
				return balance = resultSet.getInt("balance");
			}
		} catch (SQLException e) {
			log.debug("Connection error");
		}
		return 0;
	}

	// GET SAVING BALANCE BY USING ACCOUNT ID
	@Override
	public int getSavingBalanceByAccountId() {
		int balance = 0;
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT balance FROM banking.account WHERE account_id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, CustomerLoginView.savingID);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				log.debug("get balance from saving account");
				return balance = resultSet.getInt("balance");
			}
		} catch (SQLException e) {
			log.debug("Connection error");
		}
		return 0;
	}

	// CHECK IF CUSTOMER ACCOUUNT IF IS EXIST
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
				log.debug("account is exist");
				return exist;
			}else {
				throw new AccountIsNotFoundException("Invalide Account Number");
			}
		} 
		catch ( AccountIsNotFoundException e) {
			log.info(e.getMessage());
			log.debug(e.getMessage());
		}catch (SQLException  e) {
			log.debug("SQLException");
		}
		return exist;
	}

	// DEPOSIT MONEY INTO CUSTOMER ACCOUNT BY USING ACCOUNT NUMBER
	@Override
	public void depositToAccount(int amount, int customerIDYouWantToTransfer) {
		//get balance first
		int balance = 0;
		try(Connection connection = ConnectionUtil.getConnection()) {
			connection.setAutoCommit(false);
			String sql = "SELECT balance FROM banking.account WHERE account_number = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, customerIDYouWantToTransfer);
			ResultSet resultSet = preparedStatement.executeQuery();
			log.debug("get banalance from account number");
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
					log.info("--------------------------------------------------");
					TransactionUtil.send(CustomerLoginView.cusID, "transfer", "toOtherAccount", amount);
					log.debug("transafer success");
				}else {
					log.info("Transaction Failed");
				}
			}
			connection.setAutoCommit(true);
			
		} catch (SQLException e) {
			
		}
	}

	// WITHDRAW MONEY FROM SAVING ACCOUNT
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
				TransactionUtil.send(CustomerLoginView.cusID, "withdraw", "fromSaving", amount);
				log.debug("transfer success");
			} else {
				log.info("Transaction failed");
				log.debug("Transaction failed");
			}
		} catch (Exception e) {
			log.debug("withdraw failed");
		}
		
	}

	// GET CUSTOMER ID BY USING LOGIN ID
	@Override
	public int getCusIdByLoginId() {
		int cusId = 0;
		
		try(Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT customer_id FROM banking.customer WHERE login_id = ?";
			PreparedStatement p = connection.prepareStatement(sql);
			p.setInt(1, CustomerLoginView.id);
			ResultSet r = p.executeQuery();
			if(r.next()) {
				cusId = r.getInt("customer_id");
				log.debug("get customer id success");
			}
		} catch (SQLException e) {
			log.debug("connection error");
		}
		
		return cusId;
	}

	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
