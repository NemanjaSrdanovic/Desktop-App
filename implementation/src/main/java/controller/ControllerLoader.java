package controller;

import javafx.fxml.FXMLLoader;
import model.User;
import observer.BarChartController;

import java.sql.SQLException;

public class ControllerLoader {

  public static void load(FXMLLoader loader, String button, User user) throws SQLException {
    ProfileController profileController;
    AccountsController accountsController;
    TransactionsController transactionsController;
    BarChartController barChartController;

    switch (button) {
      case "profile":
        profileController = loader.getController();
        profileController.setUser(user);
        profileController.loadData();
        break;
      case "accounts":
        accountsController = loader.getController();
        accountsController.setUser(user);
        accountsController.loadData();
        break;
      case "transactions":
        transactionsController = loader.getController();
        transactionsController.setUser(user);
        transactionsController.loadData();
        break;
      case "diagrams":
        barChartController = loader.getController();
        barChartController.setUser(user);
        break;
      default:
        break;
    }
  }

}
