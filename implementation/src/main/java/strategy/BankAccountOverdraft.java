package strategy;

import strategy.Overdraft;

public class BankAccountOverdraft implements Overdraft {
  @Override
  public double allowedOverdraft() {
    return -10;
  }
}
