package facade;

import model.AccountManager;
import model.Category;
import observer.BarChartController;

import java.sql.SQLException;


public class UserAccountFacade {

  public UserAccountFacade() {
  }

  public void registerUserChart(AccountManager user) throws SQLException {

    BarChartController chart = new BarChartController();
    user.registerObserver(chart);
  }

  /*
   *  The Category has not to be premade and put into the method, but can be added as a Description
   */
  public void makeTransactionFacade(AccountManager user, String account, double amount, String category, String description) throws SQLException {

      Category categoryFacade = user.getCategory(category);

      if (categoryFacade == null) {
        throw new IllegalArgumentException(
               "Error: Category " + category + " doesn't exist");
      } else {

        user.makeTransaction(user.getAccount(account),amount,categoryFacade, description);
      }

  }

}
