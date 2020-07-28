package controller;

import enums.AccountType;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Account;
import model.User;
import observer.BarChartController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AccountsController implements Initializable {
    private User user;

    private AccountType accountType;
    private String accountName;
    private Double accountBalance;

    @FXML
    private ToggleGroup tg_type;

    @FXML
    private RadioButton bank_radio, cash_radio, card_radio, stock_radio;

    @FXML
    private Button accounts_profile, accounts_transactions, accounts_diagrams, accounts_logout;

    @FXML
    private TextField accountName_txt, accountBalance_txt, deleteAccount_txt;

    @FXML
    private HBox addingWindow, deleteWindow;

    @FXML
    private Label uspeh;

    @FXML
    private ListView<String> accounts_list;

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    public void addAccountWindow(){ addingWindow.setVisible(true); }

    @FXML
    public void deleteAccountWindow(){ deleteWindow.setVisible(true); }

    @FXML
    public void addAccount() throws IllegalArgumentException, SQLException {

        try {
            if(bank_radio.isSelected() == false &&
                    card_radio.isSelected() == false &&
                    stock_radio.isSelected() == false &&
                    card_radio.isSelected() == false                  
            ) throw new IllegalArgumentException("YOU NEED TO SELECET WHAT TYPE OF ACCOUNT YOU WANT TO ADD!");

            if(accountName_txt.getText().isEmpty() || accountBalance_txt.getText().isEmpty())
                throw new IllegalArgumentException("YOU NEED TO FILL ALL TEXT FIELDS");

        } catch (IllegalArgumentException e){ System.out.println(e.getMessage());}

        if(bank_radio.isSelected()) { accountType = AccountType.BANK; }
        if(cash_radio.isSelected()) { accountType = AccountType.CASH; }
        if(card_radio.isSelected()) { accountType = AccountType.CARD; }
        if(stock_radio.isSelected()) { accountType = AccountType.STOCK; }

        accountName= this.accountName_txt.getText();
        accountBalance = Double.parseDouble(this.accountBalance_txt.getText());
        user.addAccount(accountType,accountName,accountBalance);

        refresh();
    }

    @FXML
    public void close_add(){
        refresh();
    }
    @FXML
    public void close_delete(){
        refresh();
    }

    @FXML
    public void deleteAccount() throws IllegalArgumentException, SQLException {
        try {
            if(deleteAccount_txt.getText().isEmpty()) throw new IllegalArgumentException("please write what account you want to delete");
        } catch (IllegalArgumentException e){System.out.println(e.getMessage());}

        accountName = deleteAccount_txt.getText();
        user.deleteAccount(accountName);
        user.notifyObservers();
        refresh();
    }

    @FXML
    public void handleButton(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        Parent nextPage = null;
        String button = null;
        if(event.getSource() == accounts_profile){
            System.out.println("[PROFILE|button| clicked!]");
            loader = new FXMLLoader(getClass().getResource("/view/profile.fxml"));
            nextPage = loader.load();
            button = "profile";
        }
        else if(event.getSource() == accounts_transactions) {
            System.out.println("[TRANSACTIONS|button| clicked!]");
            loader = new FXMLLoader(getClass().getResource("/view/transactions.fxml"));
            nextPage = loader.load();
            button = "transactions";
        }
        else if(event.getSource() == accounts_diagrams) {
            System.out.println("[DIAGRAMS|button| clicked!]");
            loader = new FXMLLoader(getClass().getResource("/view/diagrams.fxml"));
            nextPage = loader.load();
            button = "diagrams";
        }
        else if(event.getSource() == accounts_logout) {
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

    private void refresh() {
        addingWindow.setVisible(false);
        deleteWindow.setVisible(false);
        tg_type.selectToggle(null);
        accountType = null;
        accountName = null;
        accountBalance = null;
        accountName_txt.setText("");
        accountBalance_txt.setText("");
        loadData();
    }

    public void loadData() {
        System.out.println("Loading data for AccountPage........");

        uspeh.setText(user.geteMail());
        ObservableList<String> empty = FXCollections.observableArrayList();
        empty.clear();
        accounts_list.setItems(empty);

        List<Account> accounts_collection = user.getAccounts().getAccounts();
        for(Account val : accounts_collection){ accounts_list.getItems().add(val.toString());}
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
