package com.example.finalassingment.app.components.form;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.finalassingment.app.components.alert.ErrorAlert;
import com.example.finalassingment.app.components.table.SelectManagerTable;
import com.example.finalassingment.app.controllers.RefreshableController;
import com.example.finalassingment.global.ProviderQueryType;
import com.example.finalassingment.model.provider.InsuranceManager;
import com.example.finalassingment.model.provider.InsuranceSurveyor;
import com.example.finalassingment.repository.impl.ProviderRepository;
import com.example.finalassingment.repository.impl.UserRepository;
import com.example.finalassingment.service.ProviderService;
import com.example.finalassingment.utility.PasswordUtil;

import java.io.IOException;
import java.util.regex.Pattern;

public class AddSurveyorForm extends BorderPane {
    @FXML
    private TextField nameField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField passwordField;
    @FXML
    private VBox managerContainer;
    @FXML
    private Button selectManagerButton;
    @FXML
    private Label managerLabel;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    private Stage stage;
    private RefreshableController controller;
    private InsuranceManager manager;

    public AddSurveyorForm(RefreshableController controller) {
        this.controller = controller;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/form/addSurveyorForm.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            BorderPane rootPane = fxmlLoader.load();

            Scene scene = new Scene(rootPane);
            stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setUpForm();
    }

    private void setUpForm() {
        this.selectManagerButton.setOnAction(this::openSelectManager);
        this.saveButton.setOnAction(this::addSurveyor);
        this.cancelButton.setOnAction(this::handleCancel);
    }

    private void openSelectManager(ActionEvent actionEvent) {
        new SelectManagerTable(ProviderQueryType.QueryType.GET_ALL, this);
    }

    void addSurveyor(ActionEvent actionEvent) {
        if (validateInput()) {
            UserRepository userRepository = new UserRepository();
            ProviderRepository providerRepository = new ProviderRepository();
            ProviderService service = new ProviderService();

            try {
                String username = usernameField.getText();
                String password = passwordField.getText();
                String hashedPassword = PasswordUtil.encrypt(password);

                if (userRepository.findUser(username, hashedPassword) != null) {
                    new ErrorAlert("This username is already taken!");
                    return;
                }

                InsuranceSurveyor surveyor = service.makeSurveyor()
                        .fullName(nameField.getText())
                        .username(username)
                        .address(addressField.getText())
                        .email(emailField.getText())
                        .phone(phoneField.getText())
                        .password(password)
                        .manager(manager)
                        .build();

                providerRepository.add(surveyor);
                providerRepository.close();
                controller.refresh(); // Refresh the table after adding
                close();
            } catch (NumberFormatException e) {
                new ErrorAlert("Please enter valid input values.");
            } finally {
                userRepository.close();
            }
        }
    }

    private boolean validateInput() {
        if (isFieldEmpty(nameField) || isFieldEmpty(usernameField) || isFieldEmpty(addressField) ||
                isFieldEmpty(emailField) || isFieldEmpty(phoneField) || isFieldEmpty(passwordField) ||
                isManagerNull()) {
            new ErrorAlert("All fields must be filled out.");
            return false;
        }

        if (!isValidEmail(emailField.getText())) {
            new ErrorAlert("Please enter a valid email address.");
            return false;
        }

        return true;
    }

    private boolean isFieldEmpty(TextField field) {
        return field.getText() == null || field.getText().trim().isEmpty();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean isManagerNull() {
        return manager == null;
    }

    private void handleCancel(ActionEvent actionEvent) {
        close();
    }

    private void close() {
        stage.close();
    }

    public void setManager(InsuranceManager manager) {
        this.manager = manager;
        managerLabel.setText(manager.getId() + " - " + manager.getFullName());
    }
}
