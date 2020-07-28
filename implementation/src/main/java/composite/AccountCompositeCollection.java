package composite;

import enums.AccountType;
import factory.AccountFactory;
import iterator.AccountCollectionIterator;
import iterator.Iterator;
import model.Account;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DriverClass;

public class AccountCompositeCollection implements CompositeCollection {

	private final List<Account> accounts;

	public AccountCompositeCollection() {
		this.accounts = new ArrayList<>();
	}

	public void addAccount(AccountType type, String name, double balance) {
		Account account;
		AccountFactory factory = new AccountFactory();
		account = factory.makeAccount(type, name, balance);

		if (account != null) {
			add(account);
		}
	}

	public List<Account> getAccounts() {
		
		List<Account> accounts = null;
		DriverClass database;
		try {
			database = new DriverClass();
			accounts = database.findAllUserAccounts();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return accounts;
	}
	
	public double getAllBalanceByType(AccountType type) {
		
		double balance=0.0;
		
		for(Account a: getAccounts()) {
			if(a.getAccountType().equals(type)) {balance+=a.getAccountBalance();}
		}
		
		return balance;
	}

	@Override
	public void add(Object account) {

		DriverClass database;
		try {
			database = new DriverClass();
			database.insertAccountTable(((Account) account).getAccountName(), ((Account) account).getAccountBalance(),
					((Account) account).getAccountType(), ((Account) account).getOverdraft());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Account getByName(String accountName) {

		Account account = null;

		DriverClass database;
		database = new DriverClass();
		try {
			account = database.findAccountByName(accountName);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return account;
	}

	@Override
	public void delete(String accountName) {

		if(getByName(accountName) != null) {
			DriverClass database;
			database = new DriverClass();
			try {
				database.DeleteAccountByName(accountName);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else { throw new
			  IllegalArgumentException(accountName + " does not exist"); 
		}
	}

	@Override
	public void print() {
		this.accounts.clear();
		 
		  try {		  
			  DriverClass database = new DriverClass();
			  for(Account a : database.findAllUserAccounts()) {
				  this.accounts.add(a);
			  }
			 }
			 catch(SQLException e) {
				 e.getMessage();
			 }
	    for(Account account : accounts) {
	      System.out.println(account.toString());
	    }
	  }

	@Override
	public Iterator createIterator() {
		return new AccountCollectionIterator(accounts);
	}
}
