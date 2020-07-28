package model;

import database.DriverClass;
import enums.AccountType;
import enums.CategoryType;
import facade.UserAccountFacade;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.SQLException;


public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/view/welcome.fxml"));
    primaryStage.setTitle("ExpenseTracker");
    Image icon = new Image("/styles/icons/expensive.png");
    primaryStage.getIcons().add(icon);
    primaryStage.setScene(new Scene(root));
    primaryStage.show();

  }

  public static void main(String[] args) throws SQLException {
    DriverClass driver = new DriverClass();

    InsertDataClass testData = new InsertDataClass();
    testData.insertFakeData();

    driver.closeDbConnection();
            launch(args);



  }
}
