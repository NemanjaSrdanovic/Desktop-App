package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import enums.AccountType;
import enums.CategoryType;
import model.Account;
import model.Category;
import model.Transaction;

public class DriverClass {

	Connection connection;
	Statement statement;
	private static String currentUser = "";

	public DriverClass() {
		try {
			
			connection = DriverManager.getConnection("jdbc:sqlite:.\\testDB.db");
			connection.setAutoCommit(false);

			this.createUserTable();
			this.createAccountTable();
			this.createCategoryTable();
			this.createTransactionTable();


		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean checkUserName(String userName) {

		return false;
	}

	public boolean checkUserPassword(String password) {
		return false;
	}

	public void createUserTable() {

		try {
			connection = DriverManager.getConnection("jdbc:sqlite:.\\testDB.db");

			connection.setAutoCommit(false);
			statement = connection.createStatement();
			statement.execute(
					"CREATE TABLE IF NOT EXISTS user (username varchar(100) primary key, " + "password varchar(100))");

			statement.close();
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void setCurrentUser(String username) {
		currentUser = username;
	}

	public void createAccountTable() {

		String createAccountTable = "CREATE TABLE IF NOT EXISTS ACCOUNT(accountName varchar(100), "
				+ "accountBalance double, "
				+ "accountType varchar(150) CHECK ( accountType IN ('BANK','CASH','CARD','STOCK')) NOT NULL DEFAULT 'BANK', "
				+ "overdraft double, " + "username varchar(150), " + "PRIMARY KEY (accountName, username), "
				+ "FOREIGN KEY(username) REFERENCES USER(username))";

		try {
			connection = DriverManager.getConnection("jdbc:sqlite:.\\testDB.db");
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			statement.execute(createAccountTable);
			statement.close();
			connection.commit();

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}

	}

	public void createCategoryTable() throws SQLException {

		connection = DriverManager.getConnection("jdbc:sqlite:.\\testDB.db");

		statement = connection.createStatement();
		connection.setAutoCommit(false);

		statement.execute("CREATE TABLE IF NOT EXISTS CATEGORY (categoryType TEXT, " + "categoryName TEXT NOT NULL, "
				+ "categoryBalance double, "
				+ "username varchar(150), "
				+ "PRIMARY KEY(categoryName,username), "
				+ "FOREIGN KEY(username) REFERENCES user (username))");

		statement.close();
		connection.commit();
	}

	public void createTransactionTable() {

		try {
			connection = DriverManager.getConnection("jdbc:sqlite:.\\testDB.db");
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			statement.execute("CREATE TABLE IF NOT EXISTS TRANSACTION_TABLE(transactionId int AUTO_INCREMENT, "
					+ "accountName varchar(150), " + "date varchar(200) , " + "amount double, "
					+ "description varchar(200), " + "categoryName varchar(150), " + "username varchar(150), "
					+ "PRIMARY KEY (transactionId, username), "
					+ "FOREIGN KEY (categoryName) REFERENCES CATEGORY(categoryName), "
					+ "FOREIGN KEY (accountName) REFERENCES ACCOUNT(accountName), "
					+ "FOREIGN KEY(username) REFERENCES USER(username))");

			statement.close();
			connection.commit();

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}

	}

	/**
	 * Insert Functions called from SQLInsert Class
	 * 
	 */
	public void insertUserTable(String username, String password) throws SQLException {

		SQLInsert insertStatement = new SQLInsert();
		insertStatement.insertUserTable(username, password);
		currentUser = username;

	}

	public void insertAccountTable(String accountName, double balance, AccountType accountType, double overdraft)
			throws SQLException {

		SQLInsert insertStatement = new SQLInsert();
		insertStatement.insertAccountTable(accountName, balance, accountType, overdraft, currentUser);
	}

	public void insertTransactionTable(Transaction transaction) throws SQLException {

		SQLInsert insertStatement = new SQLInsert();
		insertStatement.insertTransactionTable(transaction, currentUser);
	}

	public void insertCategoryTable(CategoryType categoryType, String categoryName) throws SQLException {

		SQLInsert insertStatement = new SQLInsert();
		insertStatement.insertCategoryTable(categoryType, categoryName, currentUser);
	}

	/**
	 * Select Functions called from SQLSelect Class
	 *
	 */

	public boolean findUser(String name, String password) throws SQLException {

		SQLSelect selectStatement = new SQLSelect();
		return selectStatement.findUser(name, password);
	}

	public Account findAccountByName(String accountName) throws SQLException {

		SQLSelect selectStatement = new SQLSelect();
		return selectStatement.findAccountByName(accountName);
	}

	public boolean findCategory(String categoryName) throws SQLException {

		SQLSelect selectStatement = new SQLSelect();
		return selectStatement.findCategory(categoryName);
	}

	public Category findCategoryByName(String categoryName) throws SQLException {

		SQLSelect selectStatement = new SQLSelect();
		return selectStatement.findCategoryByName(categoryName);
	}

	public Transaction findTransactionById(String transactionId) throws SQLException {

		SQLSelect selectStatement = new SQLSelect();
		return selectStatement.findTransactionById(transactionId);
	}

	public  List<Transaction> findTransactionTillDate(LocalDate date) throws SQLException{

		SQLSelect selectStatement = new SQLSelect();
		return selectStatement.findTransactionTillDate(currentUser,date);
	}

	public List<Transaction> findTransactionBetweenDate(LocalDate dateFirst, LocalDate dateSecond) throws SQLException{

		SQLSelect selectStatement = new SQLSelect();
		return selectStatement.findTransactionBetweenDate(currentUser,dateFirst,dateSecond);
	}

	public final List<Transaction> findAllUserTransactions() throws SQLException {

		SQLSelect selectStatement = new SQLSelect();
		return selectStatement.findAllUserTransactions(currentUser);
	}


	public final List<Account> findAllUserAccounts() throws SQLException {

		SQLSelect selectStatement = new SQLSelect();
		return selectStatement.findAllUserAccounts(currentUser);
	}
	
	public final List<Category> findAllUserCategories() throws SQLException {

		SQLSelect selectStatement = new SQLSelect();
		return selectStatement.findAllUserCategories(currentUser);
	}
	

	/**
	 * Update Functions called from SQLUpdate Class
	 * 
	 */

	public void updateAccountTable(Transaction transaction, Account account) throws SQLException {
		SQLUpdate updateStatement = new SQLUpdate();
		updateStatement.updateAccountTable(transaction, account, currentUser);
	}

	
	public void updateCategoryTable(Transaction transaction, Account account) throws SQLException {
		SQLUpdate updateStatement = new SQLUpdate();
		updateStatement.updateCategoryTable(transaction, account, currentUser);
	}

	public void updateBalances(Account account, Category category) throws SQLException{
		SQLUpdate updateStatement = new SQLUpdate();
		updateStatement.updateBalances(account,category,currentUser);
	}

	/**
	 * Delete functions called from SQLDelete Class
	 * 
	 */

	public boolean DeleteAccountByName(String accountName) throws SQLException {
		SQLDelete deleteStatament = new SQLDelete();
		return deleteStatament.deleteAccountByName(accountName);
	}

	public boolean DeleteTransactionById(String transactionId) throws SQLException {
		SQLDelete deleteStatament = new SQLDelete();
		return deleteStatament.deleteTransactionById(transactionId);
	}

	public boolean DeleteCategoryByName(String categoryName) throws SQLException {
		SQLDelete deleteStatament = new SQLDelete();
		return deleteStatament.deleteCategoryByName(categoryName);
	}

	public void deleteCategoryTable() {
		SQLDelete deleteStatament = new SQLDelete();
		deleteStatament.deleteCategoryTable();
	}

	public void deleteTransactionTable() {
		SQLDelete deleteStatament = new SQLDelete();
		deleteStatament.deleteTransactionTable();
	}

	public void deleteAccountTable() {
		SQLDelete deleteStatament = new SQLDelete();
		deleteStatament.deleteAccountTable();
	}

	public void deleteUserTable() {
		SQLDelete deleteStatament = new SQLDelete();
		deleteStatament.deleteUserTable();
	}

	/**
	 * Functions for deleting all table records
	 */

	public void clearUserTable() {
		SQLDelete deleteStatament = new SQLDelete();
		deleteStatament.clearUserTable();
	}

	public void clearAccountTable() {
		SQLDelete deleteStatament = new SQLDelete();
		deleteStatament.clearAccountTable();
	}

	public void clearTransactionTable() {
		SQLDelete deleteStatament = new SQLDelete();
		deleteStatament.clearTransactionTable();
	}

	public void clearCategoryTable() {
		SQLDelete deleteStatament = new SQLDelete();
		deleteStatament.clearCategoryTable();
	}

	public void closeDbConnection() {
		try {
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	

}
