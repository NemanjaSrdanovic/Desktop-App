package model;


import enums.AccountType;
import composite.TransactionCompositeCollection;
import strategy.Overdraft;

import java.sql.SQLException;


public abstract class Account {
	protected AccountType accountType;
	private String accountName;
	private double accountBalance = 0;
	private final TransactionCompositeCollection transactions;
	protected Overdraft overdraft;

	public Account(String accountName, double accountBalance) {
		setAccountName(accountName);
		this.accountBalance = accountBalance;
		this.transactions = new TransactionCompositeCollection();
	}

	public Account(AccountType accountType, String accountName) {
		this(accountName, 0.0);
	}

	public Account getThisAccount() {
		return this;
	}
	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		try {
			if (accountName.isEmpty()) {
				throw new IllegalArgumentException("Account name cannot be empty");
			} else {
				this.accountName = accountName;
			}
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}
	}

	public double getAccountBalance() {
		return accountBalance;
	}
	

	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public void setOverdraft(Overdraft overdraft) {
		this.overdraft = overdraft;
	}

	public boolean checkOverdraft() {
		return getAccountBalance() < overdraft.allowedOverdraft();
	}

	public void makeTransaction(User user,double amount, Category category, String description) throws SQLException {
			
		switch (category.getCategoryType()) {
	
			case INCOME:
				Income income = (Income) category;				
				income.transactionTemplateMethod(user,this, amount, category,description);
				break;
			case EXPENSE:
				Expense expense = (Expense) category;				
				expense.transactionTemplateMethod(user,this, amount, category,description);
				break;
		}

	}

	public TransactionCompositeCollection getTransactions() {
		return transactions;
	}

	public String toString() {
		return  accountType.toString() + " account |" + accountName + "| current balance: " + accountBalance ;
	}

	public double getOverdraft() {
		return this.overdraft.allowedOverdraft();
	}

}
