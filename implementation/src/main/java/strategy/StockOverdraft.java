package strategy;

import strategy.Overdraft;

public class StockOverdraft implements Overdraft {
  @Override
  public double allowedOverdraft() {
    return -50;
  }
}
