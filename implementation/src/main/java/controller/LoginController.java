package controller;

import database.DatabaseConnection;
import database.SQLSelect;
import facade.UserAccountFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.sql.SQLException;


public class LoginController {

    private final DatabaseConnection databaseConnection = new DatabaseConnection();
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user;

    @FXML
    private TextField txt_username;

    @FXML
    private PasswordField txt_password;

    @FXML
    public void login(ActionEvent event) throws IOException, SQLException {
        boolean output;
        output = databaseConnection.logIn(this.txt_username.getText(), this.txt_password.getText());

        if (output) {
            User user = new User(txt_username.getText(), txt_password.getText());
            UserAccountFacade userFacade = new UserAccountFacade();
            userFacade.registerUserChart(user);
            infoBox("Correct credentials", "Home");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/profile.fxml"));
            Parent nextPage = loader.load();

            ProfileController profileController = loader.getController();
            profileController.setUser(user);
            profileController.loadData();

            Scene scene = new Scene(nextPage);
            Stage App_Stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
            App_Stage.setScene(scene);
            App_Stage.show();

        } else {
            infoBox("Error, try again!", "Login Error");
        }
    }

    @FXML
    public void createUser(ActionEvent event) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/view/create.fxml"));
        Parent nextPage = root.load();
        Scene scene = new Scene(nextPage);
        Stage App_Stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        App_Stage.setScene(scene);
        App_Stage.show();
    }

    private void infoBox(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.showAndWait();
    }
}
