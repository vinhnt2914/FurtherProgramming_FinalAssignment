package org.example.app.components.form;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyHolder;
import org.example.repository.impl.CustomerRepository;
import org.example.service.CustomerService;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class AddDependantForm extends BorderPane {
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
    private ComboBox<PolicyHolder> policyHolderComboBox;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    private Stage stage;

    public AddDependantForm() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/addDependantForm.fxml"));
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
        loadPolicyHolders();
    }

    private void setUpForm() {
        setUpPolicyHolderComboBox();
        this.saveButton.setOnAction(this::addDependant);
    }

    private void addDependant(ActionEvent actionEvent) {
        if (validateInput()) {
            PolicyHolder selectedPolicyHolder = policyHolderComboBox.getValue();
            if (selectedPolicyHolder != null) {
                CustomerRepository repository = new CustomerRepository();
                CustomerService customerService = new CustomerService();

                Dependant dependant = customerService.makeDependant()
                        .fullName(nameField.getText())
                        .username(usernameField.getText())
                        .address(addressField.getText())
                        .email(emailField.getText())
                        .phone(phoneField.getText())
                        .password(passwordField.getText()).build();
                dependant.setPolicyHolder(selectedPolicyHolder);

                repository.add(dependant);
                repository.close();
                close();
            } else {
                showAlert("Please select a policy holder.");
            }
        }
    }
    private boolean validateInput() {
        if (isFieldEmpty(nameField) || isFieldEmpty(usernameField) || isFieldEmpty(addressField) ||
                isFieldEmpty(emailField) || isFieldEmpty(phoneField) || isFieldEmpty(passwordField)) {
            showAlert("All fields must be filled out.");
            return false;
        }

        if (!isValidEmail(emailField.getText())) {
            showAlert("Please enter a valid email address.");
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

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void close() {
        stage.close();
    }

    private void setUpPolicyHolderComboBox() {
        policyHolderComboBox.setCellFactory(new Callback<>() {
            @Override
            public ListCell<PolicyHolder> call(ListView<PolicyHolder> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(PolicyHolder item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getFullName());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });


        policyHolderComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(PolicyHolder item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getFullName());
                } else {
                    setText(null);
                }
            }
        });
    }

    private void loadPolicyHolders() {
        CustomerRepository repository = new CustomerRepository();
        List<PolicyHolder> policyHolders = repository.getAllPolicyHolders();
        ObservableList<PolicyHolder> policyHolderList = FXCollections.observableArrayList(policyHolders);
        policyHolderComboBox.setItems(policyHolderList);
    }
}
