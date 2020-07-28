package composite;

import iterator.Iterator;
import iterator.TransactionCollectionIterator;
import model.Account;
import model.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DriverClass;

public class TransactionCompositeCollection implements CompositeCollection {

  private final List<Transaction> transactions;

  public TransactionCompositeCollection() {
    this.transactions = new ArrayList<>();
  }

  public List<Transaction> getTransactions() {

	  List<Transaction> transactions = null;
	  DriverClass database;
	  try {
		  database = new DriverClass();
		  transactions = database.findAllUserTransactions();

	  } catch (SQLException e) {
		  e.printStackTrace();
	  }

	  return transactions;
  }

  @Override
  public void add(Object obj) {
	  
	  try {
	    	Transaction newTransaction = (Transaction) obj;
	        transactions.add(newTransaction);
	        newTransaction.setTransactionId(transactions.size());
	        
	        
			  DriverClass database = new DriverClass();
			  database.insertTransactionTable(newTransaction);
			  
			 }
			 catch(SQLException e) {
				 e.getMessage();
			 }
	  }

  @Override
  public Object getByName(String transactionId) {
	  
 try {
		  
		  DriverClass database = new DriverClass();
		  Transaction transaction = database.findTransactionById(transactionId);
		  return transaction;
		 }
		 catch(SQLException e) {
			 e.getMessage();
		 }
  
	  return null;
  } 

  @Override
  public void delete(String transactionId) {

	  
	  try {
			DriverClass database = new DriverClass();
			
			Transaction transaction = database.findTransactionById(transactionId);
			
			if(transaction != null) {
				  database.DeleteTransactionById(transactionId);
				  
				  Account acc = transaction.getAccount();
			      double accBalance = acc.getAccountBalance();
			      double amount = transaction.getAmount();
				  
				  switch (transaction.getCategory().getCategoryType()) {
			        case INCOME:
			          acc.setAccountBalance(accBalance - amount);
			          break;
			        case EXPENSE:
			          acc.setAccountBalance(accBalance + amount);
			          break;
			      }
			    } else {
			      throw new IllegalArgumentException("Transaction " + transactionId + " does not exist");
			    }
				  
			  
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
}

  @Override
  public void print() {
	  
	  this.transactions.clear();
		 
	  try {		  
		  DriverClass database = new DriverClass();
		  for(Transaction t : database.findAllUserTransactions()) {
			  this.transactions.add(t);
		  }
		 }
		 catch(SQLException e) {
			 e.getMessage();
		 } 
  
    for(Transaction transaction : transactions) {
      System.out.println(transaction.toString());
    }
  }

  
  @Override
  public Iterator createIterator() {
    return new TransactionCollectionIterator(this.transactions);
  }
}
