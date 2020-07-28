package model;

import enums.CategoryType;
import template.TransactionTemplate;

public class Expense extends TransactionTemplate implements Category{
  private final String categoryName;
  private double balance;

  public Expense(String categoryName) {
    this.categoryName = categoryName;
    this.balance = 0;
  }
  
  public Expense(String categoryName, double balance) {
	    this.categoryName = categoryName;
	    this.balance = balance;
	  }

  @Override
  public CategoryType getCategoryType() {
    return CategoryType.EXPENSE;
  }

  @Override
  public String getCategoryName() {
    return categoryName;
  }

  @Override
  public String toString() {
    return "Expense{" +
            "Category name: '" + categoryName + '\'' +
            " => Balance: " + balance + '}';
  }

  public double getCategoryBalance() {
	  return balance;
  }

  public void setCategoryBalance(double amount){

    this.balance=amount;
  }

  @Override
  public void adjustAccountBalance(double amount,Account account) {
    double help = account.getAccountBalance() - amount;
	  account.setAccountBalance(help);
	  balance -= amount;
  }

}
