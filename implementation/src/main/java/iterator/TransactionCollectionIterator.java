package iterator;

import model.Transaction;

import java.util.List;

public class TransactionCollectionIterator implements Iterator{

  private final List<Transaction> transactionList;
  private int index = 0;

  public TransactionCollectionIterator(List<Transaction> transactionList) {
    this.transactionList = transactionList;
  }

  @Override
  public boolean hasNext() {
    return index < transactionList.size();
  }

  @Override
  public Transaction next() {
    Transaction transaction = transactionList.get(index);
    ++index;
    return transaction;
  }
}
