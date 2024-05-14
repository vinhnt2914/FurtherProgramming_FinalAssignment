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
import org.example.global.GlobalVariable;
import org.example.global.Role;
import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyHolder;
import org.example.model.customer.PolicyOwner;
import org.example.repository.impl.CustomerRepository;
import org.example.service.CustomerService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

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

            // Handle policy owner for PolicyOwner role and Admin role
            if (GlobalVariable.getRole() == Role.PolicyOwner) {
                dependant.setPolicyOwner((PolicyOwner) GlobalVariable.getUser());
            } else dependant.setPolicyOwner(dependant.getPolicyHolder().getPolicyOwner());

            repository.add(dependant);
            repository.close();
            close();
        } else {
            System.out.println("Please select a policy holder.");
        }
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

        // Set a custom button cell to display the selected policy holder's name
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
