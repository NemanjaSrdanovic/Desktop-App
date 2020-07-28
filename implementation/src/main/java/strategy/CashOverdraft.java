package strategy;

import strategy.Overdraft;

public class CashOverdraft implements Overdraft {
  @Override
  public double allowedOverdraft() {
    return 0;
  }
}
