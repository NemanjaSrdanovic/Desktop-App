package model;

import enums.AccountType;
import strategy.BankAccountOverdraft;

public class StockAccount extends Account {
  public StockAccount(String accountName) {
    this(accountName, 0.0);
  }

  public StockAccount(String accountName, double accountBalance) {
    super(accountName, accountBalance);
    this.accountType = AccountType.STOCK;
    overdraft = new BankAccountOverdraft();
  }
}
