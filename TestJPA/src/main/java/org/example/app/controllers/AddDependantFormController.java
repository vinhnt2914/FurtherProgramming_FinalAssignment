package org.example.app.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyHolder;
import org.example.repository.impl.CustomerRepository;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddDependantFormController implements Initializable {
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

    private Stage dialogStage;
    private CustomerRepository customerRepository;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerRepository = new CustomerRepository();
        loadPolicyHolders();
    }

    private void loadPolicyHolders() {
        List<PolicyHolder> policyHolders = customerRepository.getAllPolicyHolders();
        ObservableList<PolicyHolder> policyHolderList = FXCollections.observableArrayList(policyHolders);
        policyHolderComboBox.setItems(policyHolderList);


        policyHolderComboBox.setCellFactory(new Callback<>() {
            @Override
            public ListCell<PolicyHolder> call(ListView<PolicyHolder> param) {
                return new ListCell<PolicyHolder>() {
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
        policyHolderComboBox.setButtonCell(new ListCell<PolicyHolder>() {
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

    @FXML
    private void handleSave() {
        PolicyHolder selectedPolicyHolder = policyHolderComboBox.getValue();
        if (selectedPolicyHolder != null) {
            Dependant dependant = new Dependant();
            dependant.setFullName(nameField.getText());
            dependant.setUsername(usernameField.getText());
            dependant.setAddress(addressField.getText());
            dependant.setEmail(emailField.getText());
            dependant.setPhone(phoneField.getText());
            dependant.setPassword(passwordField.getText());
            dependant.setPolicyHolder(selectedPolicyHolder);

            customerRepository.add(dependant);
            dialogStage.close();
        } else {

            System.out.println("Please select a policy holder.");
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
