package org.example.app.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.example.app.components.alert.ErrorAlert;
import org.example.app.components.buttonSet.DependantButtonSet;
import org.example.app.components.buttonSet.PolicyHolderButtonSet;
import org.example.app.components.form.*;
import org.example.app.components.table.DependantTable;
import org.example.app.components.table.PolicyHolderTable;
import org.example.app.components.table.RefreshableTable;
import org.example.global.CustomerQueryType;
import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyHolder;
import org.example.repository.impl.CustomerRepository;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerAdminController implements Initializable, RefreshableController {
    public HBox sortingContainer;
    @FXML
    private HBox buttonSetContainer;
    @FXML
    private HBox tableViewContainer;
    @FXML
    private ChoiceBox<String> swapTableChoiceBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpPage();
    }

    private void setUpPage() {
        ObservableList<String> options = FXCollections.observableArrayList();
        options.addAll("Policy Holders", "Dependants");
        this.swapTableChoiceBox.getItems().addAll(options);
        this.swapTableChoiceBox.getSelectionModel().select("Policy Holders");
        this.swapTableChoiceBox.setOnAction(this::swapTable);

        PolicyHolderTable policyHolderTable = new PolicyHolderTable(CustomerQueryType.QueryType.GET_ALL_POLICY_HOLDER);
        PolicyHolderButtonSet policyHolderButtonSet = new PolicyHolderButtonSet(policyHolderTable);

        this.tableViewContainer.getChildren().add(policyHolderTable);
        this.buttonSetContainer.getChildren().add(policyHolderButtonSet);
        setPolicyHolderButtonActions();
    }

    private void swapTable(Event event) {
        String tableType = swapTableChoiceBox.getValue();
        tableViewContainer.getChildren().clear();
        buttonSetContainer.getChildren().clear();

        if (tableType.equalsIgnoreCase("Policy Holders")) {
            PolicyHolderTable policyHolderTable = new PolicyHolderTable(CustomerQueryType.QueryType.GET_ALL_POLICY_HOLDER);
            PolicyHolderButtonSet policyHolderButtonSet = new PolicyHolderButtonSet(policyHolderTable);

            tableViewContainer.getChildren().add(policyHolderTable);
            buttonSetContainer.getChildren().add(policyHolderButtonSet);

            setPolicyHolderButtonActions();

        } else if (tableType.equalsIgnoreCase("Dependants")) {
            DependantTable dependantTable = new DependantTable(CustomerQueryType.QueryType.GET_ALL_DEPENDANT);
            DependantButtonSet dependantButtonSet = new DependantButtonSet(dependantTable);

            tableViewContainer.getChildren().add(dependantTable);
            buttonSetContainer.getChildren().add(dependantButtonSet);

            setDependantButtonActions();
        }
    }

    private void setPolicyHolderButtonActions() {
        PolicyHolderButtonSet policyHolderButtonSet = (PolicyHolderButtonSet) buttonSetContainer.getChildren().get(0);

        policyHolderButtonSet.addButton.setOnAction(event -> handleAddPolicyHolder());
        policyHolderButtonSet.updateButton.setOnAction(event -> handleEditPolicyHolder());
        policyHolderButtonSet.deleteButton.setOnAction(event -> handleDeletePolicyHolder());
    }

    private void setDependantButtonActions() {
        DependantButtonSet dependantButtonSet = (DependantButtonSet) buttonSetContainer.getChildren().get(0);

        dependantButtonSet.addButton.setOnAction(event -> handleAddDependant());
        dependantButtonSet.deleteButton.setOnAction(event -> handleRemoveDependant());
        dependantButtonSet.updateButton.setOnAction(event -> handleUpdateDependant());
    }

    private void handleAddPolicyHolder() {
        new AddPolicyHolderForm(this);
    }

    private void handleEditPolicyHolder() {
        PolicyHolderTable tableView = (PolicyHolderTable) tableViewContainer.getChildren().get(0);
        PolicyHolder selectedPolicyHolder = tableView.getSelectionModel().getSelectedItem();
        if (selectedPolicyHolder != null) {
            new UpdateInfoForm(selectedPolicyHolder, this);
        } else {
            new ErrorAlert("Please select a policy holder");
        }
    }

    private void handleDeletePolicyHolder() {
        PolicyHolderTable tableView = (PolicyHolderTable) tableViewContainer.getChildren().get(0);
        PolicyHolder selectedPolicyHolder = tableView.getSelectionModel().getSelectedItem();
        if (selectedPolicyHolder != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Deletion");
            confirmationAlert.setHeaderText("Delete Policy Holder");
            confirmationAlert.setContentText("Are you sure you want to delete the selected policy holder?");

            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    CustomerRepository repository = new CustomerRepository();
                    repository.removeByID(selectedPolicyHolder.getId());
                    tableView.getItems().remove(selectedPolicyHolder);
                    System.out.println("Deleted Policy Holder: " + selectedPolicyHolder.getFullName());
                    repository.close();
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Policy Holder Selected");
            alert.setContentText("Please select a policy holder in the table.");
            alert.showAndWait();
        }
    }

    private void handleAddDependant() {
        new AddDependantForm(this);
    }

    private void handleRemoveDependant() {
        DependantTable tableView = (DependantTable) tableViewContainer.getChildren().get(0);
        Dependant selectedDependant = tableView.getSelectionModel().getSelectedItem();
        if (selectedDependant != null) {
            CustomerRepository repository = new CustomerRepository();

            repository.removeByID(selectedDependant.getId());
            tableView.getItems().remove(selectedDependant);
            System.out.println("Removed Dependant: " + selectedDependant.getFullName());
            repository.close();
        } else {
            System.out.println("No Dependant selected");
        }
    }

    private void handleUpdateDependant() {
        DependantTable tableView = (DependantTable) tableViewContainer.getChildren().get(0);
        Dependant selectedDependant = tableView.getSelectionModel().getSelectedItem();
        if (selectedDependant != null) {
            new UpdateInfoForm(selectedDependant, this);
        } else {
            new ErrorAlert("Please select a dependant");
        }
    }

    @Override
    public void refresh() {
        RefreshableTable tableView = (RefreshableTable) tableViewContainer.getChildren().get(0);
        tableView.refreshTable();
    }
}
