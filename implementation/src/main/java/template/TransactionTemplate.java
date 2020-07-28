package template;

import java.sql.SQLException;
import java.util.Date;

import database.DriverClass;
import model.Account;
import model.Category;
import model.Transaction;
import model.User;


public abstract class TransactionTemplate {

	final Date date = new Date();
	final java.sql.Date sqlDate = new java.sql.Date(date.getTime());

	public final void transactionTemplateMethod(User user,Account account,double amount,Category category,String description) throws SQLException {
		
		adjustAccountBalance(amount, account);

		createTransaction(user,account,amount,category,description);
		
	}
	
	public void createTransaction(User user,Account account,double amount,Category category,String description) throws SQLException {

		Transaction transactionSQL = new Transaction(account, sqlDate, amount, category, description);

		 try {
				  DriverClass database = new DriverClass();

			 		double previousSize =database.findAllUserTransactions().size();

					 database.insertTransactionTable(transactionSQL);

					 double newSize = database.findAllUserTransactions().size();

					if(newSize== previousSize+1){

						database.updateAccountTable(transactionSQL, account);
						database.updateCategoryTable(transactionSQL, account);
					}

				 }
				 catch(SQLException e) {
					 e.getMessage();
				 }

	}
	

	public abstract void adjustAccountBalance(double amount,Account account);
}
