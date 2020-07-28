package test;

import model.*;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.*;

class JunitTest {

  @Test
  void addAccount() throws SQLException {
    User user = new User("testUser", "testPassword");
    Account account = new BankAccount("TestAccount", 100.00);
    user.addAccount(account);
    assertEquals("TestAccount", user.getAccount("TestAccount").getAccountName());
  }

  @Test
  void addCategory() throws SQLException {
    User user = new User("testUser", "testPassword");
    Category category = new Expense("testCategory");
    user.addCategory(category);
    assertNotNull(user.getCategory("testCategory"));
  }

  @Test
  void makeTransaction() throws SQLException {
    User user = new User("testUser", "testPassword");
    Account account = new BankAccount("TestAccount", 100.00);
    user.addAccount(account);
    user.makeTransaction(account, 100.00, user.getCategory("Food"), "descr");
    assertEquals(0, account.getAccountBalance(), 0.0);
  }
}