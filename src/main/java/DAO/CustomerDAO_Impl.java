package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

import DatabaseConnection.DatabaseConnection;
import Entity.Account;
import Entity.Customer;

public class CustomerDAO_Impl{
	private Connection connection = DatabaseConnection.getConnection();

	public Customer verifyLogin(String username, String password) {
		String query = "SELECT * FROM Customer WHERE customer_username = ? AND customer_password = ?";

		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(query);

			statement.setString(1, username);
			statement.setString(2, password);

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				String customerId = resultSet.getString("customer_id");
				String customerName = resultSet.getString("customer_name");
				LocalDate customerDob = resultSet.getDate("customer_dob").toLocalDate();
				String customerAddress = resultSet.getString("customer_address");
				String customerPhoneNumber = resultSet.getString("customer_phoneNumber");
				String customerEmail = resultSet.getString("customer_email");
				String customerBankAccount = resultSet.getString("customer_bankAccount");
				String customerUsername = resultSet.getString("customer_username");
				String customerPassword = resultSet.getString("customer_password");
				int customerLoginAttempts = resultSet.getInt("customer_loginAttempts");

				Customer customer = new Customer(customerId, customerName, customerDob, customerAddress,
						customerPhoneNumber, customerEmail, customerBankAccount, customerUsername, customerPassword,
						customerLoginAttempts);

				return customer;
			}
		} catch (SQLException e) {
			System.out.println("Error occurred while verifying customer login.");
			e.printStackTrace();
		}

		return null;
	}

	public void changePassword(Customer customer, String newPassword) {
		String query = "UPDATE Customer SET customer_password = ? WHERE customer_id = ?";

		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, newPassword);
			statement.setString(2, customer.getCustomerId());

			statement.executeUpdate();

			System.out.println("Password changed successfully.");
		} catch (SQLException e) {
			System.out.println("Failed to change password: " + e.getMessage());
		}
	}

	public String idGenerator() {
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuilder sb = new StringBuilder();

		// Always start with a capitalized 'C'
		sb.append("C");

		// Generate 4 random letters
		for (int i = 0; i < 4; i++) {
			char randomLetter = letters.charAt(random.nextInt(letters.length()));
			sb.append(randomLetter);
		}

		return sb.toString();
	}

	public String generateUniqueId() {
		String generatedId = null;

		do {
			// Generate a random ID
			generatedId = idGenerator();

			// Check if the ID exists in the database
			if (isIdExists(generatedId)) {
				generatedId = null; // Reset the generated ID if it exists
			}
		} while (generatedId == null);

		return generatedId;
	}

	public boolean isIdExists(String id) {
		boolean exists = false;

		try (PreparedStatement statement = connection
				.prepareStatement("SELECT COUNT(*) FROM Customer WHERE customer_id = ?")) {

			statement.setString(1, id);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				int count = resultSet.getInt(1);
				if (count > 0) {
					exists = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return exists;
	}

	public void signUp(Customer c) {
		try {
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO Customer (customer_id, customer_name, customer_dob, customer_address, customer_phoneNumber, customer_email, customer_username, customer_password, customer_bankAccount) "
							+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

			statement.setString(1, idGenerator());
			statement.setString(2, c.getCustomerName());
			statement.setDate(3, Date.valueOf(c.getCustomerDob()));
			statement.setString(4, c.getCustomerAddress());
			statement.setString(5, c.getCustomerPhoneNumber());
			statement.setString(6, c.getCustomerEmail());
			statement.setString(7, c.getCustomerUsername());
			statement.setString(8, c.getCustomerPassword());
			statement.setString(9, c.getCustomerBankAccount());

			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean accNumValidation(String accountNumber) {
		boolean isDuplicated = false;

		try (PreparedStatement statement = connection
				.prepareStatement("SELECT COUNT(*) FROM Account WHERE account_number = ?")) {

			statement.setString(1, accountNumber);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					int count = resultSet.getInt(1);
					if (count > 0) {
						isDuplicated = true;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return isDuplicated;
	}

	public boolean addAccount(Account account) {
		boolean success = false;

		try (PreparedStatement statement = connection
				.prepareStatement("INSERT INTO Account (account_number, account_otp) VALUES (?, ?)")) {

			statement.setString(1, account.getAccountNumber());
			statement.setString(2, account.getAccountOTP());

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return success;
	}

	public boolean isUsernameDuplicated(String username) {
		boolean isDuplicated = false;

		try (PreparedStatement statement = connection
				.prepareStatement("SELECT COUNT(*) FROM Customer WHERE customer_username = ?")) {

			statement.setString(1, username);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					int count = resultSet.getInt(1);
					if (count > 0) {
						isDuplicated = true;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return isDuplicated;
	}

	public boolean isEmailDuplicated(String email) {
		boolean isDuplicated = false;

		try (PreparedStatement statement = connection
				.prepareStatement("SELECT COUNT(*) FROM Customer WHERE customer_email = ?")) {

			statement.setString(1, email);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					int count = resultSet.getInt(1);
					if (count > 0) {
						isDuplicated = true;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return isDuplicated;
	}

	public boolean isPhoneNumberDuplicated(String phoneNumber) {
		boolean isDuplicated = false;

		try (PreparedStatement statement = connection
				.prepareStatement("SELECT COUNT(*) FROM Customer WHERE customer_phoneNumber = ?")) {

			statement.setString(1, phoneNumber);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					int count = resultSet.getInt(1);
					if (count > 0) {
						isDuplicated = true;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return isDuplicated;
	}

	public void lockAccount(String username) {
		try (PreparedStatement statement = connection.prepareStatement("UPDATE Account SET account_isLocked = ? "
				+ "FROM Account INNER JOIN Customer ON Customer.customer_bankAccount = Account.account_number "
				+ "WHERE Customer.customer_username = ?")) {

			statement.setBoolean(1, true);
			statement.setString(2, username);

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateLoginAttempts(String username, int loginAttempts) {
		try (PreparedStatement statement = connection
				.prepareStatement("UPDATE Customer SET customer_loginAttempts = ? WHERE customer_username = ?")) {

			statement.setInt(1, loginAttempts);
			statement.setString(2, username);

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean verifyCustomerLogin(String username, String password) {
		boolean isLoginSuccessful = false;

		try (PreparedStatement statement = connection
				.prepareStatement("SELECT * FROM Customer WHERE customer_username = ?")) {

			statement.setString(1, username);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					String storedPassword = resultSet.getString("customer_password");
					int loginAttempts = resultSet.getInt("customer_loginAttempts");

					if (storedPassword.equals(password)) {
						isLoginSuccessful = true;
					} else {
						// Increment the login attempts counter
						loginAttempts++;
						updateLoginAttempts(username, loginAttempts);

						// Check if the maximum login attempts limit is reached
						if (loginAttempts >= 4) {
							lockAccount(username);
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return isLoginSuccessful;
	}

	public int getLoginAttempts(String username) {
		int loginAttempts = 0;

		try (PreparedStatement statement = connection
				.prepareStatement("SELECT customer_loginAttempts FROM Customer WHERE customer_username = ?")) {

			statement.setString(1, username);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					loginAttempts = resultSet.getInt("customer_loginAttempts");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return loginAttempts;
	}

	public boolean getAccountLockStatus(String username) {
		boolean isLocked = false;

		try (PreparedStatement statement = connection.prepareStatement("SELECT account_isLocked "
				+ "FROM Account INNER JOIN Customer ON Account.account_number = Customer.customer_bankAccount "
				+ "WHERE Customer.customer_username = ?")) {

			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				isLocked = resultSet.getBoolean("account_isLocked");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return isLocked;
	}

	public boolean isUsernameExists(String username) {
		boolean exists = false;

		try (PreparedStatement statement = connection
				.prepareStatement("SELECT customer_username FROM Customer WHERE customer_username = ?")) {

			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				exists = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return exists;
	}

	public Customer getCustomerByEmail(String email) {
		String query = "SELECT * FROM Customer WHERE customer_email = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, email);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					String customerId = resultSet.getString("customer_id");
					String customerName = resultSet.getString("customer_name");
					LocalDate customerDob = resultSet.getDate("customer_dob").toLocalDate();
					String customerAddress = resultSet.getString("customer_address");
					String customerPhoneNumber = resultSet.getString("customer_phoneNumber");
					String customerEmail = resultSet.getString("customer_email");
					String customerBankAccount = resultSet.getString("customer_bankAccount");
					String customerUsername = resultSet.getString("customer_username");
					String customerPassword = resultSet.getString("customer_password");
					int customerLoginAttempts = resultSet.getInt("customer_loginAttempts");

					Customer customer = new Customer(customerId, customerName, customerDob, customerAddress,
							customerPhoneNumber, customerEmail, customerBankAccount, customerUsername, customerPassword,
							customerLoginAttempts);

					return customer;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean updateCustomerPassword(Customer customer) {
		try {
			String sql = "UPDATE Customer SET customer_password = ? WHERE customer_id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);

			// Set the password value for the placeholder in the SQL statement
			statement.setString(1, customer.getCustomerPassword());
			statement.setString(2, customer.getCustomerId());

			// Execute the update statement
			int rowsAffected = statement.executeUpdate();

			// Return true if at least one row was affected, indicating a successful update
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
