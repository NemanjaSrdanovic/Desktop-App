package model;



import java.util.Date;

public class Transaction {
  static int incrementer = 0;
  private int transactionId;
  private final Account account;
  private Date date;
  private double amount;
  private Category category;
  private String description;

  public Transaction(Account account, Date date, double amount, Category category,
      String description) {
    this.account = account;
    this.date = date;
    this.amount = amount;
    this.category = category;
    this.description = description;
    transactionId=++incrementer;
  }

  public Transaction(int transactionId, Account account, Date date, double amount, Category category,
	      String description) {
	    this.account = account;
	    this.date = date;
	    this.amount = amount;
	    this.category = category;
	    this.description = description;
	    this.transactionId=transactionId;
	  }
  
  public void setTransactionId(int id) {
    this.transactionId = id;
  }
  public int getTransactionId() {
    return this.transactionId;
  }

  public String getTransactionIdString() {
    return Integer.toString(this.transactionId);
  }
  public Date getDate() {
    return date;
  }

  public double getAmount() {
    return amount;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Account getAccount() {
    return this.account;
  }

  public String toString() {
    String from = "";
    String fromName = "";
    String to = "";
    String toName = "";
    switch (category.getCategoryType()) {
      case EXPENSE:
        from = "Account";
        fromName = account.getAccountName();
        to = "Category";
        toName = category.getCategoryName();
        break;
      case INCOME:
        from = "Category";
        fromName = category.getCategoryName();
        to = "Account";
        toName = account.getAccountName();
        break;
    }
    return String.format(
        "[Transaction ID: %s \n Date: %s \n From: %s \"%s\" \n To: %s \"%s\" \n Amount: %.2f \n Description: %s]",
        transactionId, date.toString(), from, fromName, to, toName, amount, description);
  }
}
