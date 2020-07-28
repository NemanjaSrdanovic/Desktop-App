package strategy;

import strategy.Overdraft;

public class CardOverdraft implements Overdraft {
  @Override
  public double allowedOverdraft() {
    return -100;
  }
}
