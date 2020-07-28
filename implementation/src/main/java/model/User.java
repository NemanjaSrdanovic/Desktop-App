package model;

import composite.AccountCompositeCollection;
import composite.CategoryCompositeCollection;
import composite.CompositeCollection;
import composite.TransactionCompositeCollection;
import database.DriverClass;
import enums.AccountType;
import enums.CategoryType;
import facade.UserAccountFacade;
import factory.AccountFactory;
import observer.Observer;
import observer.Subject;
import java.util.Date;
import java.sql.SQLException;
import java.util.ArrayList;


import factory.CategoryFactory;


public class User implements AccountManager, Subject {
  private String eMail;
  private String password;
  private final CompositeCollection accounts;
  private final CompositeCollection categories;
  private final CompositeCollection transactions;
  private final ArrayList<Observer> observers;

  public User(String eMail, String password) {
    seteMail(eMail);
    setPassword(password);
    this.accounts = new AccountCompositeCollection();
    this.categories = new CategoryCompositeCollection();
    this.transactions = new TransactionCompositeCollection();
    this.observers = new ArrayList<>();
    
    try {
    	DriverClass database;
   		database = new DriverClass();
   		database.insertUserTable(eMail, password);
   	} catch (SQLException e) {
   		e.printStackTrace();
   	}
  }

  public String geteMail() {
    return eMail;
  }

  public void seteMail(String eMail) {
    try {
      if (eMail.isEmpty()) {
        throw new IllegalArgumentException("E-mail field can't be empty.");
      }
    } catch (IllegalArgumentException e) {
      System.err.println(e.getMessage());
    }
    this.eMail = eMail;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    try {
      if (password.isEmpty()) {
        throw new IllegalArgumentException("Password field can't be empty.");
      }
    } catch (IllegalArgumentException e) {
      System.err.println(e.getMessage());
    }
    this.password = password;
  }

  public AccountCompositeCollection getAccounts() {
    return (AccountCompositeCollection) accounts;
  }

  public CategoryCompositeCollection getCategories() {
    return (CategoryCompositeCollection) categories;
  }

  public TransactionCompositeCollection getTransactions() {
    return (TransactionCompositeCollection) transactions;
  }

  public Account getAccount(String accountName) {
    return (Account) accounts.getByName(accountName);
  }

  public void addAccount(AccountType type, String name, double balance) throws SQLException {
    AccountFactory accountFactory = new AccountFactory();
    accounts.add(accountFactory.makeAccount(type, name, balance));
    this.notifyObservers();
  }

  public void addAccount(Account account) throws SQLException {
    accounts.add(account);
  }

  public void deleteAccount(String accountName) {
    accounts.delete(accountName);
  }

  public void addCategory(CategoryType categoryType, String categoryName) throws SQLException {
    CategoryFactory categoryFactory = new CategoryFactory();
    categories.add(categoryFactory.makeCategory(categoryType, categoryName));
  }

  public void addCategory(Category category) throws SQLException {
    categories.add(category);
  }

  public Category getCategory(String categoryName) {
    return (Category) categories.getByName(categoryName);
  }

  public void deleteCategory(String categoryName) {
    categories.delete(categoryName);
  }

  public void makeTransaction(Account account,double amount, Category category,
                              String description) throws SQLException {
    account.makeTransaction(this, amount, category, description);
    notifyObservers();

  }

  public void makeTransaction(String account, double amount, Category category,
                              String description) throws SQLException {
    UserAccountFacade userAccountFacade = new UserAccountFacade();
    userAccountFacade.makeTransactionFacade(this, account, amount, category.getCategoryName(), description);
  }

  public void deleteTransaction(String id) throws SQLException {
    transactions.delete(id);
    notifyObservers();
  }

  @Override
  public void listAccounts() {
    accounts.print();
  }
  @Override
  public void listTransactions() {
    transactions.print();
  }

  @Override
  public void listCategories() {
    categories.print();
  }

  // Observer methods
  @Override
  public void registerObserver(Observer o) throws SQLException {
    observers.add(o);
    o.update(accounts,categories);
  }

  @Override
  public void removeObserver(Observer o) {
    observers.remove(o);
  }

  @Override
  public void notifyObservers() throws SQLException {
    for (Observer o : observers)
      o.update(this.accounts,this.categories);
  }

    @Override
    public String toString() {
        return "User{" +
                "eMail='" + eMail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
