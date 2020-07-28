package controller;

import database.DriverClass;
import enums.CategoryType;
import facade.UserAccountFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Account;
import model.Category;
import model.Transaction;
import model.User;
import observer.BarChartController;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class TransactionsController implements Initializable {

  private User user;
  private UserAccountFacade facade;
  private String account, descr;
  double value;
  Category category;
  private String transactionId;

  @FXML
  private TextField account_name, category_name, amount, description, id_text;

  @FXML
  private ListView<String> lista;

  @FXML
  public void makeTransaction() throws SQLException {
    if (account_name.getText().isEmpty() || category_name.getText().isEmpty() || description.getText().isEmpty() || amount.getText().isEmpty()) {
      System.out.println("praazno");
    } else {
      account = account_name.getText();
      category = user.getCategory(category_name.getText());
      descr = description.getText();
      value = Double.parseDouble(amount.getText());
      user.makeTransaction(account, value, category, descr);
      user.getAccounts().print();
      refresh();
    }
  }

  @FXML
  public void deleteTransaction() throws SQLException {
    transactionId = id_text.getText();

    DriverClass database = new DriverClass();
    Account acc = database.findTransactionById(transactionId).getAccount();
    Category cat = database.findTransactionById(transactionId).getCategory();
    double amount = database.findTransactionById(transactionId).getAmount();

    switch (cat.getCategoryType()){
      case EXPENSE:
        acc.setAccountBalance(acc.getAccountBalance()+amount);
        cat.setCategoryBalance(cat.getCategoryBalance()+amount);
        break;
      case INCOME:
        acc.setAccountBalance(acc.getAccountBalance()-amount);
        cat.setCategoryBalance(cat.getCategoryBalance()-amount);
        break;
    }

    database.updateBalances(acc,cat);

    user.deleteTransaction(transactionId);
    user.notifyObservers();

    refresh();
  }

  private void refresh() {
    account_name.setText("");
    category_name.setText("");
    amount.setText("");
    description.setText("");
    id_text.setText("");
    loadData();
  }

  public void setUser(User user) {
    this.user = user;
  }

  @FXML
  private Button trans_profile, trans_acc, trans_diag, trans_logout;

  @FXML
  public void handleButton(ActionEvent event) throws IOException, SQLException {
    FXMLLoader loader = new FXMLLoader();
    Parent nextPage = null;
    String button = null;

    if (event.getSource() == trans_profile) {
      System.out.println("[PROFILE|button| clicked!]");
      loader = new FXMLLoader(getClass().getResource("/view/profile.fxml"));
      nextPage = loader.load();
      button = "profile";

    } else if (event.getSource() == trans_acc) {
      System.out.println("[ACCOUNTS|button| clicked!]");
      loader = new FXMLLoader(getClass().getResource("/view/accounts.fxml"));
      nextPage = loader.load();
      button = "accounts";

    } else if (event.getSource() == trans_diag) {
      System.out.println("[DIAGRAMS|button| clicked!]");
      loader = new FXMLLoader(getClass().getResource("/view/diagrams.fxml"));
      nextPage = loader.load();
      button = "diagrams";

    } else if (event.getSource() == trans_logout) {
      System.out.println("[LOGOUT|button| clicked!]");
      loader = new FXMLLoader(getClass().getResource("/view/welcome.fxml"));
      nextPage = loader.load();
      button = "";
    }
    ControllerLoader.load(loader, button, user);
    Scene scene = new Scene(nextPage);
    Stage App_Stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
    App_Stage.setScene(scene);
    App_Stage.show();
  }

  public void loadData() {
    System.out.println("Loading data for TransactionPage........");

    ObservableList<String> empty = FXCollections.observableArrayList();
    empty.clear();
    lista.setItems(empty);

    List<Transaction> accounts_collection = user.getTransactions().getTransactions();
    for (Transaction val : accounts_collection) {
      lista.getItems().add(val.toString());
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }
}