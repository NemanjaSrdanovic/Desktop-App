package iterator;

import model.Account;

import java.util.List;

public class AccountCollectionIterator implements Iterator{

  private final List<Account> accountList;
  private int index = 0;

  public AccountCollectionIterator(List<Account> accountList) {
    this.accountList = accountList;
  }

  @Override
  public boolean hasNext() {
    return index < accountList.size();
  }

  @Override
  public Account next() {
    Account account = accountList.get(index);
    ++index;
    return account;
  }

}
