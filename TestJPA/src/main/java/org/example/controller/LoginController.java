package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.repository.impl.CustomerRepository;
import org.example.repository.impl.UserRepository;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable{
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
        System.out.println("Get username: " + username);
        System.out.println("Get password: " + password);
        System.out.println(repository.findUser(username, password));
    }
}
