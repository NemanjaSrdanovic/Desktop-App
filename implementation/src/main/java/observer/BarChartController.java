package observer;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import controller.AccountsController;
import database.DriverClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import composite.AccountCompositeCollection;
import composite.CategoryCompositeCollection;
import composite.CompositeCollection;
import controller.ProfileController;
import controller.TransactionsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Stage;
import enums.AccountType;
import enums.CategoryType;
import model.Transaction;
import model.User;
import javafx.scene.chart.PieChart;

/**
 *
 * @author Nemanja
 */

public class BarChartController implements Initializable, Observer {

  public BarChartController() {}

  private ProfileController profileController;
  private TransactionsController transactionsController;
  private AccountsController accountsController;
  private User user;

  public void setUser(User user) {
    this.user = user;
  }

  ObservableList<String> chartlist = FXCollections.observableArrayList("BarChart", "PieChart");

  @FXML
  private DatePicker date_picker_begin,date_picker_end,pie_date_picker_begin,pie_date_picker_end;

  @FXML
  private ChoiceBox<String> choice;

  @FXML
  private Button diagrams_profile, diagrams_transactions, diagrams_accounts, diagrams_logout;

  @FXML
  private Button ApplyBtn,PieApplyBtn;

  @FXML
  private BarChart<String, Number> AccountBalanceChart;
  
  @FXML
  private PieChart pieChart;

  @FXML
  private CategoryAxis X;

  @FXML
  private NumberAxis Y;

  private static volatile double bankAccountBalance;
  private static volatile double cardBalance;
  private static volatile double cashBalance;
  private static volatile double stockBalance;
  
  private static volatile double incomeCategories=0.0;
  private static volatile double expenseCategories=0.0;
  
  private  XYChart.Series<String, Number> set1 = new Series<>();
  private  ObservableList<PieChart.Data> pieChartData;

  @Override
  public void update(CompositeCollection accounts,CompositeCollection categories) throws SQLException {
    
  AccountCompositeCollection account=(AccountCompositeCollection) accounts;
    
  bankAccountBalance=account.getAllBalanceByType(AccountType.BANK);
  cardBalance=account.getAllBalanceByType(AccountType.CARD);
  stockBalance=account.getAllBalanceByType(AccountType.STOCK);
  cashBalance=account.getAllBalanceByType(AccountType.CASH);
  
  CategoryCompositeCollection category = (CategoryCompositeCollection) categories;
  incomeCategories=category.getAllBalanceByType(CategoryType.INCOME);
  expenseCategories=category.getAllBalanceByType(CategoryType.EXPENSE);
  
  }

  @FXML
  public void handleButton(ActionEvent event) throws IOException, SQLException {
    FXMLLoader loader = new FXMLLoader();
    Parent nextPage = null;

    // FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/accounts.fxml"));
    if(event.getSource() == diagrams_profile){
      System.out.println("[PROFILE|button| clicked!]");
      loader = new FXMLLoader(getClass().getResource("/view/Profile.fxml"));
      nextPage = loader.load();
      controllerLoad(loader, "profile");
    }
    else if(event.getSource() == diagrams_accounts) {
      System.out.println("[ACCOUNTS|button| clicked!]");
      loader = new FXMLLoader(getClass().getResource("/view/accounts.fxml"));
      nextPage = loader.load();
      controllerLoad(loader,"accounts");
    }
    else if(event.getSource() == diagrams_transactions) {
      System.out.println("[TRANSACTIONS|button| clicked!]");
      loader = new FXMLLoader(getClass().getResource("/view/transactions.fxml"));
      nextPage = loader.load();
      controllerLoad(loader,"transactions");
    }
    else if(event.getSource() == diagrams_logout) {
      System.out.println("[LOGOUT|button| clicked!]");
      loader = new FXMLLoader(getClass().getResource("/view/welcome.fxml"));
      nextPage = loader.load();
    }

    Scene scene = new Scene(nextPage);
    Stage App_Stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
    App_Stage.setScene(scene);
    App_Stage.show();
  }

  private void controllerLoad(FXMLLoader loader, String button) throws SQLException {
    switch (button){
      case "profile": 
    	  profileController = loader.getController(); 
    	  profileController.setUser(user); 
    	  profileController.loadData(); 
    	  break;
      case "transactions": 
    	  transactionsController= loader.getController(); 
    	  transactionsController.setUser(user); 
    	  transactionsController.loadData(); 
    	  break;
      case "accounts": 
    	  accountsController= loader.getController(); 
    	  accountsController.setUser(user); 
    	  accountsController.loadData(); 
    	  break;
    }
  }

  public void loadData() {
    if(choice.getValue().equals("BarChart")){
      loadBarDiagram();

      AccountBalanceChart.setVisible(true);
      date_picker_begin.setVisible(true);
      date_picker_end.setVisible(true);
      ApplyBtn.setVisible(true);

      pieChart.setVisible(false);
      pie_date_picker_begin.setVisible(false);
      pie_date_picker_end.setVisible(false);
      PieApplyBtn.setVisible(false);
    }
    else if(choice.getValue().equals("PieChart")){
      loadPieDiagram();
      AccountBalanceChart.setVisible(false);
      pieChart.setVisible(true);
      pie_date_picker_begin.setVisible(true);
      pie_date_picker_end.setVisible(true);
      PieApplyBtn.setVisible(true);
      date_picker_begin.setVisible(false);
      date_picker_end.setVisible(false);
      ApplyBtn.setVisible(false);
    }

  }

  public void updateChartDateValues(LocalDate dateFirst,LocalDate dateSecond ) throws SQLException {

    DriverClass database = new DriverClass();
    List<Transaction> transactions=null;

    if(dateSecond!=null && dateFirst==null){
      transactions=database.findTransactionTillDate(dateSecond);
    }else if(dateFirst!=null && dateSecond!=null)
    {
      transactions=database.findTransactionBetweenDate(dateFirst,dateSecond);
        if(transactions.size()==0){user.notifyObservers();}
    }

    for(Transaction t: transactions ) {
      if (t.getCategory().getCategoryType().equals(CategoryType.INCOME)) {

        switch (t.getAccount().getAccountType()) {
          case BANK:
            bankAccountBalance -= t.getAmount();
            break;
          case CARD:
            cardBalance -= t.getAmount();
            break;
          case CASH:
            cashBalance -= t.getAmount();
            break;
          case STOCK:
            stockBalance -= t.getAmount();
            break;
        }
      } else {

        switch (t.getAccount().getAccountType()) {
          case BANK:
            bankAccountBalance += t.getAmount();
            break;
          case CARD:
            cardBalance += t.getAmount();
            break;
          case CASH:
            cashBalance += t.getAmount();
            break;
          case STOCK:
            stockBalance += t.getAmount();
            break;

        }
      }
    }
  }

  public void updatePieChartDateValues(LocalDate dateFirst,LocalDate dateSecond ) throws SQLException {

    DriverClass database = new DriverClass();
    List<Transaction> transactions = null;

    if (dateSecond != null && dateFirst == null) {
      transactions = database.findTransactionTillDate(dateSecond);
    } else if (dateFirst != null && dateSecond != null) {
      transactions = database.findTransactionBetweenDate(dateFirst, dateSecond);
    }

  if(transactions.size()!=0) {
    for (Transaction t : transactions) {
      if (t.getCategory().getCategoryType().equals(CategoryType.INCOME)) {
        incomeCategories -= t.getAmount();
      } else {
        expenseCategories -= t.getAmount();
      }
    }
  }

  }

  @FXML
  public void applyDate() throws SQLException {

   LocalDate date_begin = date_picker_begin.getValue();
   LocalDate date_end = date_picker_end.getValue();

   if(date_begin!=null && date_end!= null || date_end!=null){
     updateChartDateValues(date_begin,date_end);
   } else if(date_begin==null && date_end==null) user.notifyObservers();

  date_picker_begin.setValue(null);
  date_picker_end.setValue(null);
   loadData();
  }

  @FXML
  public void PieApplyDate() throws SQLException{

    LocalDate date_begin = pie_date_picker_begin.getValue();
    LocalDate date_end = pie_date_picker_end.getValue();

    if(date_begin!=null && date_end!= null || date_end!=null){
      updatePieChartDateValues(date_begin,date_end);
    } else if(date_begin==null && date_end==null) user.notifyObservers();

    pieChartData = FXCollections.observableArrayList(
            new PieChart.Data("Income", incomeCategories),
            new PieChart.Data("Expense", expenseCategories)
    );

    pieChart.setData(pieChartData);

    pie_date_picker_begin.setValue(null);
    pie_date_picker_end.setValue(null);
   loadData();

  }

  @Override@FXML
  public void initialize(URL url, ResourceBundle rb) {
	  
    choice.setValue("BarChart");
    choice.setItems(chartlist);
    AccountBalanceChart.setVisible(true);
    loadPieDiagram();
    loadBarDiagram();

    AccountBalanceChart.getData().add(set1);
    pieChart.setData(pieChartData);

    pieChart.setVisible(false);
    pie_date_picker_begin.setVisible(false);
    pie_date_picker_end.setVisible(false);
    PieApplyBtn.setVisible(false);
    	
    }

  private void loadBarDiagram() {
    set1.getData().add(new XYChart.Data<>("BankAccount", bankAccountBalance));
    set1.getData().add(new XYChart.Data<>("CardAccount", cardBalance));
    set1.getData().add(new XYChart.Data<>("CashAccount", cashBalance));
    set1.getData().add(new XYChart.Data<>("StockAccount", stockBalance));
  }

  private void loadPieDiagram() {

    pieChartData = FXCollections.observableArrayList(
            new PieChart.Data("Income", incomeCategories),
            new PieChart.Data("Expense", expenseCategories));
   
  }

}
