package org.example.app.controllers;

import jakarta.persistence.NoResultException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.app.App;
import org.example.app.components.alert.ErrorAlert;
import org.example.global.GlobalVariable;
import org.example.global.Role;
import org.example.model.Admin;
import org.example.model.User;
import org.example.repository.impl.CustomerRepository;
import org.example.repository.impl.UserRepository;
import org.example.utility.PasswordUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable{
    @FXML
    private AnchorPane loginPane;
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

        if(username.equals("admin") && password.equals("123456")) {
            GlobalVariable.setRole(Role.Admin);
            GlobalVariable.setUser(Admin.getInstance());
            System.out.println("Admin logged in");
        } else {
            String encryptedPassword = PasswordUtil.encrypt(password);
            // Encrypt the user password before searching in the database
            System.out.println("ENCRYPTED PASSWORD: " + encryptedPassword);

            User user = repository.findUser(username, encryptedPassword);
            if (user == null) {
                new ErrorAlert("Username or password is incorrect!");
                return;
            }

            GlobalVariable.setRole(user);
            GlobalVariable.setUserID(user.getId());
            GlobalVariable.setUser(user);
            System.out.println("User logged in: " + GlobalVariable.getUserID());
            System.out.println("User role: " + GlobalVariable.getRole());
            System.out.println("User password: " + GlobalVariable.getUser().getPassword());


        }

        // Close the login stage
        Stage stage = (Stage) loginPane.getScene().getWindow();
        stage.close();

        // Run the app
        App.getInstance();
    }
}
