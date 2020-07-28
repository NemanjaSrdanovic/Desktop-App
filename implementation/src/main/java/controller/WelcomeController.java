package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class WelcomeController {

    @FXML
    private Button createBtn, loginBtn;

    @FXML
    public void handle(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        if(event.getSource() == createBtn) loader = new FXMLLoader(getClass().getResource("/view/create.fxml"));
        else if(event.getSource() == loginBtn) loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));

        Parent nextPage = loader.load();
        Scene scene = new Scene(nextPage);
        Stage App_Stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        App_Stage.setScene(scene);
        App_Stage.show();
    }
}
