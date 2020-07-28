package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import enums.AccountType;
import enums.CategoryType;
import model.Category;
import model.Transaction;

public class SQLInsert {

	Connection connection;
	Statement statement;
	
	public SQLInsert() {
		try {
			
			 connection = DriverManager
		              .getConnection("jdbc:sqlite:.\\testDB.db");

	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	}
	
    public void insertUserTable(String username, String password) throws SQLException {
			
			String insertUserTable = "INSERT OR IGNORE INTO user " + "VALUES('"+username+"', '"+password+"')";
				
			try {
				connection.setAutoCommit(false);
				statement = connection.createStatement();
				statement.execute(insertUserTable);
				statement.close();
				connection.commit();

			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
	}
    
    public void insertAccountTable(String accountName, double balance, AccountType accountType, double overdraft,
			String userName) throws SQLException {

			String insertAccountTable = "INSERT OR IGNORE INTO ACCOUNT " + "VALUES('" + accountName + "', " + " " + balance + ", "
					+ "'" + accountType + "', " + " " + overdraft + "," + "'" + userName + "'" + ")";

			try {
				connection.setAutoCommit(false);
				statement = connection.createStatement();
				statement.execute(insertAccountTable);
				statement.close();
				connection.commit();

			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
	}

    public void insertCategoryTable(CategoryType categoryType, String categoryName, String currentUser) throws SQLException {


		String categoryTypeString = categoryType.toString();
		double categoryBalance = 0;
		String insertCategoryTable = "INSERT OR IGNORE INTO CATEGORY " + "VALUES('" + categoryTypeString + "', " + " '" + categoryName + "' , "+categoryBalance+", '"+currentUser+"')";

		try {
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			statement.execute(insertCategoryTable);
			statement.close();
			connection.commit();

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}

}

	public void insertTransactionTable(Transaction transaction, String currentUser) throws SQLException {
			String insertTransactionTable = 
				"INSERT OR IGNORE INTO TRANSACTION_TABLE  VALUES("+transaction.getTransactionId()+", '"+transaction.getAccount().getAccountName()+"', '"+transaction.getDate()+"' , "+transaction.getAmount()+" ,  '"+transaction.getDescription()+"', '"+transaction.getCategory().getCategoryName()+"', "
						+ "'"+currentUser+"')";

				connection.setAutoCommit(false);
				Statement statement = connection.createStatement();
				statement.execute(insertTransactionTable);
				statement.close();
				connection.commit();
				
	}
		
		


}
