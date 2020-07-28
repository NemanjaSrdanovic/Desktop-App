package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Account;
import model.Category;
import model.Transaction;


/**
 * Class for all update Functions called by DriverClass
 *
 */


public class SQLUpdate {

	Connection connection;
	Statement statement;
	

	public SQLUpdate() {
		try {
			
			 connection = DriverManager
		              .getConnection("jdbc:sqlite:.\\testDB.db");

	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	}
	
	/**
	 * Function for updating account after transaction
	 *
	 */
	public void updateAccountTable(Transaction transaction, Account account, String username) throws SQLException {

			try {
	
			String updateAccountTable = transaction.getCategory().getCategoryType().toString();
			
			if (transaction.getCategory().getCategoryType().toString().equals("INCOME")) {
	
				updateAccountTable = "UPDATE ACCOUNT "
					+ "SET ACCOUNTBALANCE = ACCOUNTBALANCE + "+ transaction.getAmount()+" "
					+ "WHERE ACCOUNTNAME = '"+account.getAccountName()+"' "	
							+ "AND USERNAME = '"+username+"'";
			}
			
			else if(transaction.getCategory().getCategoryType().toString().equals("EXPENSE")){
				updateAccountTable = "UPDATE ACCOUNT "
					+ "SET ACCOUNTBALANCE = ACCOUNTBALANCE - "+transaction.getAmount()+""
					+ " WHERE ACCOUNTNAME = '"+account.getAccountName()+"'"
					+ "AND USERNAME = '"+username+"'";
			}
			
				connection.setAutoCommit(false);
				Statement statement = connection.createStatement();
				statement.execute(updateAccountTable);
				statement.close();
				connection.commit();
				
				
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

	public void updateCategoryTable(Transaction transaction, Account account, String username) {
		try {
			
			String updateCategoryTable = transaction.getCategory().getCategoryType().toString();
			
			if (transaction.getCategory().getCategoryType().toString().equals("INCOME")) {
	
				updateCategoryTable = "UPDATE CATEGORY "
					+ "SET CATEGORYBALANCE = CATEGORYBALANCE + "+transaction.getAmount()+" "
					+ "WHERE CATEGORYNAME = '"+transaction.getCategory().getCategoryName()+"' "	
							+ "AND USERNAME = '"+username+"'";
			}
			
			else if(transaction.getCategory().getCategoryType().toString().equals("EXPENSE")){
				
				updateCategoryTable = "UPDATE CATEGORY "
						+ "SET CATEGORYBALANCE = CATEGORYBALANCE - "+transaction.getAmount()+" "
						+ "WHERE CATEGORYNAME = '"+transaction.getCategory().getCategoryName()+"' "	
								+ "AND USERNAME = '"+username+"'";
			}
			
				connection.setAutoCommit(false);
				Statement statement = connection.createStatement();
				statement.execute(updateCategoryTable);
				statement.close();
				connection.commit();
				
				
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}

		
	}

	public void updateBalances(Account account, Category category,String username){

		try {

			String updateCategoryTable;
			String updateAccountTable;

				updateCategoryTable = "UPDATE CATEGORY "
					+ "SET CATEGORYBALANCE = "+ category.getCategoryBalance() +" "
					+ "WHERE CATEGORYNAME = '"+category.getCategoryName()+"' "
					+ "AND USERNAME = '"+username+"'";

			updateAccountTable = "UPDATE ACCOUNT "
				+ "SET ACCOUNTBALANCE = "+account.getAccountBalance()+""
				+ " WHERE ACCOUNTNAME = '"+account.getAccountName()+"'"
				+ "AND USERNAME = '"+username+"'";

			connection.setAutoCommit(false);
			Statement statement = connection.createStatement();
			statement.execute(updateCategoryTable);
			statement.execute(updateAccountTable);
			statement.close();
			connection.commit();

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}

	}


}
