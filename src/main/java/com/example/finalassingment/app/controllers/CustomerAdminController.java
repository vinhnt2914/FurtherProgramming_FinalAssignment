package com.example.finalassingment.app.controllers;

import com.example.finalassingment.app.components.form.AddDependantForm;
import com.example.finalassingment.app.components.form.AddPolicyHolderForm;
import com.example.finalassingment.app.components.form.AddPolicyOwnerForm;
import com.example.finalassingment.app.components.form.UpdateInfoForm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import com.example.finalassingment.app.components.alert.ErrorAlert;
import com.example.finalassingment.app.components.buttonSet.DependantButtonSet;
import com.example.finalassingment.app.components.buttonSet.PolicyHolderButtonSet;
import com.example.finalassingment.app.components.buttonSet.PolicyOwnerButtonSet;
import com.example.finalassingment.app.components.sortingSet.CustomerSortingSet;
import com.example.finalassingment.app.components.table.DependantTable;
import com.example.finalassingment.app.components.table.PolicyHolderTable;
import com.example.finalassingment.app.components.table.PolicyOwnerTable;
import com.example.finalassingment.app.components.table.RefreshableTable;
import com.example.finalassingment.global.CustomerQueryType;
import com.example.finalassingment.model.customer.Dependant;
import com.example.finalassingment.model.customer.PolicyHolder;
import com.example.finalassingment.model.customer.PolicyOwner;
import com.example.finalassingment.repository.impl.CustomerRepository;

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
        ObservableList<String> comboBoxOptions = FXCollections.observableArrayList();
        comboBoxOptions.addAll("Policy Holders", "Dependants", "Policy Owners");
        this.swapTableChoiceBox.getItems().addAll(comboBoxOptions);
        this.swapTableChoiceBox.getSelectionModel().select("Policy Holders");
        this.swapTableChoiceBox.setOnAction(this::swapTable);

        PolicyHolderTable policyHolderTable = new PolicyHolderTable(CustomerQueryType.QueryType.GET_ALL_POLICY_HOLDER);
        PolicyHolderButtonSet policyHolderButtonSet = new PolicyHolderButtonSet(policyHolderTable);
        CustomerSortingSet sortingSet = new CustomerSortingSet(policyHolderTable);

        this.tableViewContainer.getChildren().add(policyHolderTable);
        this.buttonSetContainer.getChildren().add(policyHolderButtonSet);
        this.sortingContainer.getChildren().add(sortingSet);
        setPolicyHolderButtonActions();
    }

    private void swapTable(Event event) {
        String tableType = swapTableChoiceBox.getValue();

        if (tableType.equalsIgnoreCase("Policy Holders")) {
            PolicyHolderTable policyHolderTable = new PolicyHolderTable(CustomerQueryType.QueryType.GET_ALL_POLICY_HOLDER);
            PolicyHolderButtonSet policyHolderButtonSet = new PolicyHolderButtonSet(policyHolderTable);
            CustomerSortingSet sortingSet = new CustomerSortingSet(policyHolderTable);

            tableViewContainer.getChildren().setAll(policyHolderTable);
            buttonSetContainer.getChildren().setAll(policyHolderButtonSet);
            sortingContainer.getChildren().setAll(sortingSet);

            setPolicyHolderButtonActions();

        } else if (tableType.equalsIgnoreCase("Dependants")) {
            DependantTable dependantTable = new DependantTable(CustomerQueryType.QueryType.GET_ALL_DEPENDANT);

            DependantButtonSet dependantButtonSet = new DependantButtonSet(dependantTable);
            CustomerSortingSet sortingSet = new CustomerSortingSet(dependantTable);

            tableViewContainer.getChildren().setAll(dependantTable);
            buttonSetContainer.getChildren().setAll(dependantButtonSet);
            sortingContainer.getChildren().setAll(sortingSet);

            setDependantButtonActions();
        } else if (tableType.equalsIgnoreCase("Policy Owners")) {
            PolicyOwnerTable policyOwnerTable = new PolicyOwnerTable(CustomerQueryType.QueryType.GET_ALL_POLICY_OWNER);

            PolicyOwnerButtonSet policyOwnerButtonSet = new PolicyOwnerButtonSet(policyOwnerTable);
            CustomerSortingSet sortingSet = new CustomerSortingSet(policyOwnerTable);

            tableViewContainer.getChildren().setAll(policyOwnerTable);
            buttonSetContainer.getChildren().setAll(policyOwnerButtonSet);
            sortingContainer.getChildren().setAll(sortingSet);

            setPolicyOwnerButtonActions();
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
    private void setPolicyOwnerButtonActions() {
        PolicyOwnerButtonSet policyOwnerButtonSet = (PolicyOwnerButtonSet) buttonSetContainer.getChildren().get(0);

        policyOwnerButtonSet.addButton.setOnAction(event -> handleAddPolicyOwner());
        policyOwnerButtonSet.updateButton.setOnAction(event -> handleEditPolicyOwner());
        policyOwnerButtonSet.deleteButton.setOnAction(event -> handleDeletePolicyOwner());
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
            new ErrorAlert("No Policy Holder selected");
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
            new ErrorAlert("No Policy Holder selected");
        }
    }

    private void handleAddDependant() {
        new AddDependantForm(this);
    }

    private void handleRemoveDependant() {
        DependantTable tableView = (DependantTable) tableViewContainer.getChildren().get(0);
        Dependant selectedDependant = tableView.getSelectionModel().getSelectedItem();
        if (selectedDependant != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Deletion");
            confirmationAlert.setHeaderText("Delete Dependant");
            confirmationAlert.setContentText("Are you sure you want to delete the selected dependant?");

            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    CustomerRepository repository = new CustomerRepository();
                    repository.removeByID(selectedDependant.getId());
                    tableView.getItems().remove(selectedDependant);
                    System.out.println("Removed Dependant: " + selectedDependant.getFullName());
                    repository.close();
                }
            });
        } else {
            new ErrorAlert("No Dependant selected");
        }
    }

    private void handleUpdateDependant() {
        DependantTable tableView = (DependantTable) tableViewContainer.getChildren().get(0);
        Dependant selectedDependant = tableView.getSelectionModel().getSelectedItem();
        if (selectedDependant != null) {
            new UpdateInfoForm(selectedDependant, this);
        } else {
            new ErrorAlert("No Dependant selected");
        }
    }

        private void handleDeletePolicyOwner() {
            PolicyOwnerTable tableView = (PolicyOwnerTable) tableViewContainer.getChildren().get(0);
            PolicyOwner selectedPolicyOwner = tableView.getSelectionModel().getSelectedItem();
            if (selectedPolicyOwner != null) {
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirm Deletion");
                confirmationAlert.setHeaderText("Delete Policy Owner");
                confirmationAlert.setContentText("Are you sure you want to delete the selected policy owner?");

                confirmationAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        CustomerRepository repository = new CustomerRepository();
                        repository.removeByID(selectedPolicyOwner.getId());
                        repository.close();
                        refresh();
                    }
                });
            } else {
                new ErrorAlert("Please select a policy owner");
                System.out.println("No Policy Owner selected");
            }
        }

    private void handleEditPolicyOwner() {
        PolicyOwnerTable tableView = (PolicyOwnerTable) tableViewContainer.getChildren().get(0);
        PolicyOwner selectedPolicyOwner = tableView.getSelectionModel().getSelectedItem();

        if (selectedPolicyOwner != null) {

            new UpdateInfoForm(selectedPolicyOwner, this);
        } else {
            System.out.println("No Policy Owner selected");
        }
    }


    private void handleAddPolicyOwner() {
        new AddPolicyOwnerForm(this);
    }

    @Override
    public void refresh() {
        RefreshableTable tableView = (RefreshableTable) tableViewContainer.getChildren().get(0);
        tableView.refreshTable();
    }
}
