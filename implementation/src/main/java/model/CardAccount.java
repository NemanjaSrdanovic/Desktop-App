package model;

import enums.AccountType;
import strategy.BankAccountOverdraft;

public class CardAccount extends Account {
  public CardAccount(String accountName) {
    this(accountName, 0.0);
  }

  public CardAccount(String accountName, double accountBalance) {
    super(accountName, accountBalance);
    this.accountType = AccountType.CARD;
    overdraft = new BankAccountOverdraft();
  }
}
