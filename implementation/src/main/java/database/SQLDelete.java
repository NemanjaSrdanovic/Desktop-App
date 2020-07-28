package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import composite.AccountCompositeCollection;
import composite.CategoryCompositeCollection;
import composite.TransactionCompositeCollection;
import model.Account;
import model.Category;
import model.Transaction;

public class SQLDelete {

	Connection connection;
	Statement statement;
	SQLSelect selectStatement = null;

	public SQLDelete() {

		try {

			connection = DriverManager.getConnection("jdbc:sqlite:.\\testDB.db");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean deleteAccountByName(String accountName) throws SQLException {

		connection.setAutoCommit(false);
		AccountCompositeCollection compositeCollection = new AccountCompositeCollection();
		Account account = compositeCollection.getByName(accountName);
		connection.commit();

		try {
			if (account != null) {

				String sqlDelete = "DELETE FROM ACCOUNT WHERE ACCOUNTNAME = '" + accountName + "'";

				try {
					connection.setAutoCommit(false);
					statement = connection.createStatement();
					statement.execute(sqlDelete);
					statement.close();
					connection.commit();

				} catch (SQLException e) {
					e.printStackTrace();
				}

				return true;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return false;

	}

	public boolean deleteTransactionById(String transactionIdString) throws SQLException {

		connection.setAutoCommit(false);
		TransactionCompositeCollection compositeCollection = new TransactionCompositeCollection();
		Transaction transaction = (Transaction) compositeCollection.getByName(transactionIdString);
		connection.commit();
		int transactionID=Integer.parseInt(transactionIdString);

		try {
			if (transaction != null) {

				String sqlDelete = "DELETE FROM TRANSACTION_TABLE WHERE TRANSACTIONID = '" + transactionID + "'";

				try {
					connection.setAutoCommit(false);
					statement = connection.createStatement();
					statement.execute(sqlDelete);
					statement.close();
					connection.commit();

				} catch (SQLException e) {
					e.printStackTrace();
				}

				return true;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return false;

	}

	public boolean deleteCategoryByName(String categoryName) throws SQLException {

		connection.setAutoCommit(false);
		CategoryCompositeCollection compositeCollection = new CategoryCompositeCollection();
		Category category = compositeCollection.getByName(categoryName);
		connection.commit();

		try {
			if (category != null) {

				String sqlDelete = "DELETE FROM CATEGORY WHERE CATEGORYNAME = '" + categoryName + "'";

				try {
					connection.setAutoCommit(false);
					statement = connection.createStatement();
					statement.execute(sqlDelete);
					statement.close();
					connection.commit();

				} catch (SQLException e) {
					e.printStackTrace();
				}

				return true;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return false;

	}

	public void deleteCategoryTable() {

		String sqlDelete = "DROP TABLE CATEGORY";

		try {
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			statement.execute(sqlDelete);
			statement.close();
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void deleteTransactionTable() {
		String sqlDelete = "DROP TABLE TRANSACTION_TABLE";

		try {
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			statement.execute(sqlDelete);
			statement.close();
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void deleteAccountTable() {
		String sqlDelete = "DROP TABLE ACCOUNT";

		try {
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			statement.execute(sqlDelete);
			statement.close();
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void deleteUserTable() {
		String sqlDelete = "DROP TABLE USER";

		try {
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			statement.execute(sqlDelete);
			statement.close();
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void clearUserTable() {
		String sqlClear = "DELETE TABLE USER";

		try {
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			statement.execute(sqlClear);
			statement.close();
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void clearAccountTable() {
		String sqlClear = "DELETE TABLE ACCOUNT";

		try {
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			statement.execute(sqlClear);
			statement.close();
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void clearCategoryTable() {
		String sqlClear = "DELETE TABLE CATEGORY";

		try {
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			statement.execute(sqlClear);
			statement.close();
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void clearTransactionTable() {
		String sqlClear = "DELETE TABLE TRANSACTION_TABLE";

		try {
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			statement.execute(sqlClear);
			statement.close();
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
