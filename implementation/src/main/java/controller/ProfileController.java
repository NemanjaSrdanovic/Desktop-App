package controller;

import enums.CategoryType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Account;
import model.Category;
import model.User;
import observer.BarChartController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    private User user;

    private double overal_balance = 0.0;

    private String categoryName;
    private CategoryType categoryType;

    @FXML
    private Label email_label;

    @FXML
    private Label overall_balance_label;

    @FXML
    private ListView<String > lista;

    @FXML
    private Button addCategory_button;

    @FXML
    private RadioButton expense_radio;

    @FXML
    private RadioButton income_radio;

    @FXML
    private TextField txt_name_add;

    @FXML
    private TextField txt_name_delete;

    @FXML
    private ToggleGroup tg_type;

    @FXML
    private Button profile_accounts, profile_transactions, profile_diagrams, profile_logout;


    @FXML
    public void handleButton(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        Parent nextPage = null;
        String button = null;
        if(event.getSource() == profile_accounts){
            System.out.println("[ACCOUNTS|button| clicked!]");
            loader = new FXMLLoader(getClass().getResource("/view/accounts.fxml"));
            nextPage = loader.load();
            button = "accounts";
            //controllerLoad(loader, "accounts");
        }
        else if(event.getSource() == profile_transactions) {
            System.out.println("[TRANSACTIONS|button| clicked!]");
            loader = new FXMLLoader(getClass().getResource("/view/transactions.fxml"));
            nextPage = loader.load();
            button = "transactions";
        }
        else if(event.getSource() == profile_diagrams) {
            System.out.println("[DIAGRAMS|button| clicked!]");
            loader = new FXMLLoader(getClass().getResource("/view/diagrams.fxml"));
            nextPage = loader.load();
            button = "diagrams";
        }
        else if(event.getSource() == profile_logout) {
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

    @FXML
    public void deleteCategory() throws SQLException {
        categoryName = txt_name_delete.getText();
        user.deleteCategory(categoryName);
        categoryName = null;
        txt_name_add.setText("");
        txt_name_delete.setText("");
        loadData();
    }

    @FXML
    public void addCategory() throws SQLException {

        if(expense_radio.isSelected()) {
            categoryType = CategoryType.EXPENSE;
            categoryName = this.txt_name_add.getText();
            user.addCategory(categoryType,categoryName);
        }

        if(income_radio.isSelected()){
            categoryType = CategoryType.INCOME;
            categoryName = this.txt_name_add.getText();
            user.addCategory(categoryType,categoryName);
        }

        categoryName = null;
        categoryType = null;
        txt_name_add.setText("");
        txt_name_delete.setText("");
        tg_type.selectToggle(null);

        loadData();
    }

    public ProfileController(){}

    public void setUser(User user) { this.user = user; }

    public void loadData() throws SQLException {
      email_label.setText(user.geteMail());

        ObservableList<String> empty = FXCollections.observableArrayList();
        empty.clear();
        lista.setItems(empty);

        List<Category> categories_collection = user.getCategories().getCategories();
        for(Category val : categories_collection){ lista.getItems().add(val.toString());}
        List<Account> accounts_collection = user.getAccounts().getAccounts();
        overal_balance = 0.0;
        for(Account val : accounts_collection){ overal_balance+=val.getAccountBalance();}
        overall_balance_label.setText("Your overall account balance: " + overal_balance + " �");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}
}


