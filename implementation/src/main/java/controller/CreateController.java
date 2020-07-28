package controller;

import database.SQLInsert;
import database.SQLSelect;
import enums.CategoryType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class CreateController {

    String username, password;
    SQLSelect select = new SQLSelect();
    SQLInsert insert = new SQLInsert();

    @FXML
    private TextField txt_username;
    @FXML
    private PasswordField txt_passowrd1, txt_passowrd2;

    @FXML
    public void go_back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/welcome.fxml"));
        Parent nextPage = loader.load();
        Scene scene = new Scene(nextPage);
        Stage App_Stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        App_Stage.setScene(scene);
        App_Stage.show();
    }

    @FXML
    public void create(ActionEvent event) throws SQLException, IOException {
        if(txt_username.getText().isEmpty() || txt_passowrd1.getText().isEmpty() || txt_passowrd2.getText().isEmpty()){
            infoBox("insert data in all field!","Empty Fields ERROR404");
        return;}
        if(txt_passowrd1.getText().equals(txt_passowrd2.getText()) == false) infoBox("PASSWORDS DON'T MATCH\n\ttry again","Password Error");
        else {
            password = txt_passowrd1.getText();
            username = txt_username.getText();
            if (select.findUseremail(username)) {
                infoBox("already exists", "Username Error");
            } else {
                insert.insertUserTable(username, password);
                insert.insertCategoryTable(CategoryType.INCOME,"Salary",username);
                insert.insertCategoryTable(CategoryType.INCOME,"Dividend",username);
                insert.insertCategoryTable(CategoryType.EXPENSE,"Food",username);
                insert.insertCategoryTable(CategoryType.EXPENSE,"Transportation",username);
                insert.insertCategoryTable(CategoryType.EXPENSE,"Education",username);
                infoBox("registration succsesfull!", "login correct");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
                Parent nextPage = loader.load();
                Scene scene = new Scene(nextPage);
                Stage App_Stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
                App_Stage.setScene(scene);
                App_Stage.show();

            }
        }
    }

    private void infoBox(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.showAndWait();
    }
}
