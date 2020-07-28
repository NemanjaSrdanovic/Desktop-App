package factory;

import enums.AccountType;
import model.*;

public class AccountFactory {

  public Account makeAccount(AccountType type, String name, double balance) {

    Account newAccount = null;

    try {
      switch (type) {
        case CASH:
          newAccount = new CashAccount(name, balance);
          break;
        case BANK:
          newAccount = new BankAccount(name, balance);
          break;
        case CARD:
          newAccount = new CardAccount(name, balance);
          break;
        case STOCK:
          newAccount = new StockAccount(name, balance);
          break;
        default:
          throw new IllegalArgumentException("Error: Account type " + type + " doesn't exist.");
      }
    } catch (IllegalArgumentException e) {
      System.err.println(e.getMessage());

    }
    return newAccount;
  }

}
