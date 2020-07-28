package model;

import enums.AccountType;
import strategy.BankAccountOverdraft;


public class CashAccount extends Account {
  public CashAccount(String accountName) {
    this(accountName, 0.0);
  }

  public CashAccount(String accountName, double accountBalance) {
    super(accountName, accountBalance);
    this.accountType = AccountType.CASH;
    overdraft = new BankAccountOverdraft();
  }
}
