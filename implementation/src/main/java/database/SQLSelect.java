package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import composite.AccountCompositeCollection;
import enums.AccountType;
import enums.CategoryType;
import factory.AccountFactory;
import factory.CategoryFactory;
import iterator.AccountCollectionIterator;
import model.Account;
import model.Category;
import model.Transaction;

public class SQLSelect {

	Connection connection;
	Statement statement;

	public SQLSelect() {
		try { 
			connection = DriverManager.getConnection("jdbc:sqlite:.\\Database.db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function for checking if user exists
	 * 
	 */
	public boolean findUser(String name, String password) throws SQLException {

		boolean returnValue = false;
		Statement statement = connection.createStatement();
		String sql = "SELECT username, password FROM user";
		ResultSet rs = statement.executeQuery(sql);

		while (rs.next()) {

			String usernameFromTable = rs.getString("username");
			String passwordFromTable = rs.getString("password");

			if (usernameFromTable.equals(name) && passwordFromTable.equals(password)) {
				returnValue = true;
				break;
			}
		}

		statement.close();

		return returnValue;
	}


    public boolean findUseremail(String name) throws SQLException {

        boolean returnValue = false;
        Statement statement = connection.createStatement();
        String sql = "SELECT username FROM user";
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            String usernameFromTable = rs.getString("username");
            if (usernameFromTable.equals(name)) {
                returnValue = true;
                break;
            }
        }

        statement.close();
        return returnValue;
    }



	public boolean findCategory(String categoryName) throws SQLException {

		try {
			Statement statement = connection.createStatement();
			String sql = "SELECT * FROM CATEGORY WHERE CATEGORYNAME = '" + categoryName + "'";
			ResultSet rs = connection.createStatement().executeQuery(sql);

			if (rs != null)
				return true;

			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public List<Account> findAllUserAccounts(String username) {

		List<Account> accounts = new ArrayList<Account>();
		Account account;
		AccountFactory factory = new AccountFactory();

		try {
			connection.setAutoCommit(false);
			Statement statement2 = connection.createStatement();
			ResultSet rs = statement2.executeQuery("SELECT * FROM ACCOUNT WHERE USERNAME = '" + username + "'");

			while (rs.next()) {
				String accountName = rs.getString("ACCOUNTNAME");
				double accountBalance = rs.getDouble("ACCOUNTBALANCE");
				String accountType = rs.getString("ACCOUNTTYPE");

				account = factory.makeAccount(convertToAccountTypeEnum(accountType), accountName, accountBalance);

				accounts.add(account);
			}

			statement2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return accounts;
	}

	public Account findAccountByName(String accountName) throws SQLException {

		Account account = null;

		try {
			connection.setAutoCommit(false);
			Statement statement2 = connection.createStatement();
			ResultSet rs = statement2.executeQuery("SELECT * FROM ACCOUNT WHERE ACCOUNTNAME = '" + accountName + "'");

			while(rs.next()) {
				String accountNameResult = rs.getString("ACCOUNTNAME");
				double accountBalance = rs.getDouble("ACCOUNTBALANCE");
				String accountType = rs.getString("ACCOUNTTYPE");

				AccountFactory accountFactory = new AccountFactory();
				account = accountFactory.makeAccount(convertToAccountTypeEnum(accountType), accountNameResult,
						accountBalance);
			
			}
				rs.close();
				statement2.close();
				connection.commit();
				
		}catch (IllegalArgumentException | SQLException ex) {
			System.out.println("ovo je u account: " + ex.toString());
		} finally {	
			connection.commit();
		}
		return account;
	}

	public Category findCategoryByName(String categoryName) {

		Category category = null;

		try {
			connection.setAutoCommit(false);
			Statement statement2 = connection.createStatement();
			
			ResultSet rs = statement2.executeQuery("SELECT * FROM CATEGORY WHERE CATEGORYNAME = '" + categoryName + "'");

			while(rs.next()) {
			String categoryType = rs.getString("CATEGORYTYPE");
			String categoryNameResult = rs.getString("CATEGORYNAME");
			double categoryBalance = rs.getDouble("CATEGORYBALANCE");

			CategoryFactory categoryFactory = new CategoryFactory();
			category = categoryFactory.makeCategory(convertToCategoryTypeEnum(categoryType), categoryNameResult, categoryBalance);

			}
			
			rs.close();
			statement2.close();
			connection.commit();
			
			
		}catch (IllegalArgumentException | SQLException ex) {
			System.out.println("ovo je u category: " + ex.toString());
		}

		return category;
	}

	public List<Category> findAllUserCategories(String userName) {

		List<Category> categories = new ArrayList<Category>();
		Account account;
		CategoryFactory factory = new CategoryFactory();

		try {
			connection.setAutoCommit(false);
			ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM CATEGORY WHERE USERNAME = '" + userName + "'");

			while (rs.next()) {
				String categoryNameResult = rs.getString("CATEGORYNAME");
				String categoryType = rs.getString("CATEGORYTYPE");
				double categoryBalance = rs.getDouble("CATEGORYBALANCE");

				Category category = factory.makeCategory(convertToCategoryTypeEnum(categoryType), categoryNameResult, categoryBalance);

				categories.add(category);
			}

			rs.close();
	//		statement2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				
				connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return categories;
	}

	
	public Transaction findTransactionById(String transactionIdString) {

		Transaction transaction = null;
		try {
			connection.setAutoCommit(false);
			Statement statement = connection.createStatement();
			int transactionId = Integer.parseInt(transactionIdString);
			ResultSet rs = statement.executeQuery("SELECT * FROM TRANSACTION_TABLE WHERE TRANSACTIONID = '" + transactionId + "'");

			String accountName = rs.getString("ACCOUNTNAME");
			String sqlDateString = rs.getString("DATE");
			double amount = rs.getDouble("AMOUNT");
			String category = rs.getString("CATEGORYNAME");
			String description = rs.getString("DESCRIPTION");

			DriverClass database = new DriverClass();
			Account accountResult = database.findAccountByName(accountName);
			Category categoryObject = database.findCategoryByName(category);


			Date sqlDate = Date.valueOf(sqlDateString);
			transaction = new Transaction(accountResult, sqlDate, amount, categoryObject, description);

			statement.close();
			connection.commit();
			return transaction;

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}

		return transaction;
	}

	public List<Transaction> findTransactionTillDate(String username, LocalDate date){

		Transaction transaction = null;
		List<Transaction> allTransactions = new ArrayList<Transaction>();

		try {
			connection.setAutoCommit(false);
			Statement statement2 = connection.createStatement();

			ResultSet rs = statement2.executeQuery("SELECT * FROM TRANSACTION_TABLE WHERE USERNAME = '" + username + "' " +
					"AND DATE >= '" + date + "'");

			while (rs.next()) {
				int transactionId = rs.getInt("TRANSACTIONID");
				String accountName = rs.getString("ACCOUNTNAME");
				String sqlDateString = rs.getString("DATE");
				double amount = rs.getDouble("AMOUNT");
				String category = rs.getString("CATEGORYNAME");
				String description = rs.getString("DESCRIPTION");

				DriverClass database = new DriverClass();

				Account accountResult = database.findAccountByName(accountName);
				Thread.sleep(100);

				Category categoryObject = database.findCategoryByName(category);
				Thread.sleep(100);

				Date sqlDate = Date.valueOf(sqlDateString);
				transaction = new Transaction(transactionId, accountResult, sqlDate, amount, categoryObject, description);
				allTransactions.add(transaction);
			}

			rs.close();
			statement2.close();
			connection.commit();


		} catch (Exception ex) {
			System.out.println(ex.toString());
		}

		return allTransactions;

	}

	public List<Transaction> findTransactionBetweenDate(String username, LocalDate dateFirst, LocalDate dateSecond) {

		Transaction transaction = null;
		List<Transaction> allTransactions = new ArrayList<Transaction>();

		try {
			connection.setAutoCommit(false);
			Statement statement2 = connection.createStatement();

			ResultSet rs = statement2.executeQuery("SELECT * FROM TRANSACTION_TABLE WHERE USERNAME = '" + username + "' " +
					"AND DATE BETWEEN '" + dateFirst + "' AND '" + dateSecond + "'");

			while (rs.next()) {
				int transactionId = rs.getInt("TRANSACTIONID");
				String accountName = rs.getString("ACCOUNTNAME");
				String sqlDateString = rs.getString("DATE");
				double amount = rs.getDouble("AMOUNT");
				String category = rs.getString("CATEGORYNAME");
				String description = rs.getString("DESCRIPTION");

				DriverClass database = new DriverClass();

				Account accountResult = database.findAccountByName(accountName);
				Thread.sleep(100);

				Category categoryObject = database.findCategoryByName(category);
				Thread.sleep(100);

				Date sqlDate = Date.valueOf(sqlDateString);
				transaction = new Transaction(transactionId, accountResult, sqlDate, amount, categoryObject, description);
				allTransactions.add(transaction);
			}

			rs.close();
			statement2.close();
			connection.commit();


		} catch (Exception ex) {
			System.out.println(ex.toString());
		}

		System.out.println("TRANSACTION LIST EQUALS "+ allTransactions.size());
		return allTransactions;

	}

	public final List<Transaction> findAllUserTransactions(String username) {

		Transaction transaction = null;
		List<Transaction> allTransactions = new ArrayList<Transaction>();

		try {
			connection.setAutoCommit(false);
			Statement statement2 = connection.createStatement();

			ResultSet rs = statement2.executeQuery("SELECT * FROM TRANSACTION_TABLE WHERE USERNAME = '" + username + "' ");

			while (rs.next()) {
				int transactionId = rs.getInt("TRANSACTIONID");
				String accountName = rs.getString("ACCOUNTNAME");
				String sqlDateString = rs.getString("DATE");
				double amount = rs.getDouble("AMOUNT");
				String category = rs.getString("CATEGORYNAME");
				String description = rs.getString("DESCRIPTION");

				//System.out.println(sqlDateString);

				DriverClass database = new DriverClass();	
				
				Account accountResult = database.findAccountByName(accountName);
				Thread.sleep(100);

				Category categoryObject = database.findCategoryByName(category);
				Thread.sleep(100);

				Date sqlDate = Date.valueOf(sqlDateString);
				transaction = new Transaction(transactionId, accountResult, sqlDate, amount, categoryObject, description);
				allTransactions.add(transaction);
			}
			
			rs.close();
			statement2.close();
			connection.commit();
			

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}

		return allTransactions;
	}

	public AccountType convertToAccountTypeEnum(String accountType) {

		if (accountType.equalsIgnoreCase(AccountType.BANK.toString()))
			return AccountType.BANK;
		else if (accountType.equalsIgnoreCase(AccountType.CASH.toString()))
			return AccountType.CASH;
		else if (accountType.equalsIgnoreCase(AccountType.CARD.toString()))
			return AccountType.CARD;
		else if (accountType.equalsIgnoreCase(AccountType.STOCK.toString()))
			return AccountType.STOCK;

		return AccountType.BANK;
	}

	public CategoryType convertToCategoryTypeEnum(String categoryType) {

		if (categoryType.equalsIgnoreCase(CategoryType.EXPENSE.toString()))
			return CategoryType.EXPENSE;
		else if (categoryType.equalsIgnoreCase(CategoryType.INCOME.toString()))
			return CategoryType.INCOME;

		return CategoryType.INCOME;
	}
}
