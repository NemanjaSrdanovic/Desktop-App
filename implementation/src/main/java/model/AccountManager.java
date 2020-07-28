package model;

import enums.AccountType;
import enums.CategoryType;
import composite.AccountCompositeCollection;
import composite.CategoryCompositeCollection;
import composite.TransactionCompositeCollection;
import observer.Observer;

import java.sql.SQLException;


public interface AccountManager {

  AccountCompositeCollection getAccounts();
  CategoryCompositeCollection getCategories();
  TransactionCompositeCollection getTransactions();

  void addAccount(AccountType type, String name, double balance) throws SQLException;

  Account getAccount(String accountName);

  void registerObserver(Observer o) throws SQLException;

  void removeObserver(Observer o);

  void notifyObservers() throws SQLException;

  void makeTransaction(Account account,double amount, Category category,
                       String description) throws SQLException;

  void makeTransaction(String account,double amount, Category category,
                       String description) throws SQLException;

  void deleteAccount(String accountName);

  void listAccounts();

  void listTransactions();

  void deleteTransaction(String id) throws SQLException;

  void addCategory(CategoryType categoryType, String categoryName) throws SQLException;

  void addCategory(Category category) throws SQLException;

  void listCategories();

  void deleteCategory(String categoryName);

  Category getCategory(String categoryName);

}

