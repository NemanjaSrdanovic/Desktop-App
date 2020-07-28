package model;

import enums.AccountType;
import strategy.BankAccountOverdraft;

public class BankAccount extends Account {
  public BankAccount(String accountName) {
    this(accountName, 0.0);
  }

  public BankAccount(String accountName, double accountBalance) {
    super(accountName, accountBalance);
    this.accountType = AccountType.BANK;
    overdraft = new BankAccountOverdraft();
  }
}
