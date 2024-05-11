package org.example.app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.app.App;
import org.example.global.GlobalVariable;
import org.example.model.User;
import org.example.repository.impl.UserRepository;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable{
    public BorderPane loginPane;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    private UserRepository repository;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginButton.setOnAction(this::login);
        repository = new UserRepository();
    }

    private void login(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = repository.findUser(username, password);
        GlobalVariable.setRole(user);
        GlobalVariable.setUserID(user.getId());
        System.out.println("User logged in: " + GlobalVariable.getUserID());
        System.out.println("User role: " + GlobalVariable.getRole());

        // Close the login stage
        Stage stage = (Stage) loginPane.getScene().getWindow();
        stage.close();

        // Run the app
        App.getInstance();
    }
}
