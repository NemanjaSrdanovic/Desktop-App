package model;

import enums.CategoryType;
import template.TransactionTemplate;

public class Income extends TransactionTemplate implements Category {
  private final String categoryName;
  private double balance;

  public Income(String categoryName) {
    this.categoryName = categoryName;
    this.balance = 0;
  }
  
  public Income(String categoryName, double balance) {
	    this.categoryName = categoryName;
	    this.balance = balance;
	  }

  @Override
  public CategoryType getCategoryType() {
    return CategoryType.INCOME;
  }

  @Override
  public String getCategoryName() {
    return categoryName;
  }

  @Override
  public String toString() {
    return "Income{" +
            "Category name:'" + categoryName + '\'' +
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
    double help = account.getAccountBalance() + amount;
    account.setAccountBalance(help);
	  balance += amount;
  }
}
