package org.example.app.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import org.example.app.components.alert.ErrorAlert;
import org.example.app.components.buttonSet.ClaimButtonSet;
import org.example.app.components.buttonSet.DependantButtonSet;
import org.example.app.components.buttonSet.PolicyHolderButtonSet;
import org.example.app.components.form.*;
import org.example.app.components.table.ClaimTable;
import org.example.app.components.table.DependantTable;
import org.example.app.components.table.PolicyHolderTable;
import org.example.app.components.table.RefreshableTable;
import org.example.global.ClaimQueryType;
import org.example.global.CustomerQueryType;
import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyHolder;
import org.example.model.items.Claim;
import org.example.repository.impl.ClaimRepository;
import org.example.repository.impl.CustomerRepository;

import java.net.URL;
import java.util.ResourceBundle;

public class PolicyOwnerController implements Initializable, RefreshableController {
    @FXML
    private HBox buttonSetContainer;
    @FXML
    private ChoiceBox<String> swapTableChoiceBox;
    @FXML
    private HBox tableViewContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpPage();
    }

    private void setUpPage() {
        ObservableList<String> comboBoxOptions = FXCollections.observableArrayList();
        comboBoxOptions.addAll("Claim", "Dependant", "PolicyHolder");

        ClaimTable claimTable = new ClaimTable(ClaimQueryType.QueryType.GET_ALL);
        ClaimButtonSet claimButtonSet = new ClaimButtonSet(claimTable);
        this.tableViewContainer.getChildren().add(claimTable);
        this.buttonSetContainer.getChildren().add(claimButtonSet);
        setClaimButtonActions();

        this.swapTableChoiceBox.getItems().addAll(comboBoxOptions);
        this.swapTableChoiceBox.getSelectionModel().select("Claim");
        this.swapTableChoiceBox.setOnAction(this::swapTable);
    }

    private void swapTable(Event event) {
        String tableType = swapTableChoiceBox.getValue();
        tableViewContainer.getChildren().clear();
        buttonSetContainer.getChildren().clear();

        if (tableType.equalsIgnoreCase("Claim")) {
            ClaimTable claimTable = new ClaimTable(ClaimQueryType.QueryType.GET_ALL);
            ClaimButtonSet claimButtonSet = new ClaimButtonSet(claimTable);
            tableViewContainer.getChildren().add(claimTable);
            buttonSetContainer.getChildren().add(claimButtonSet);

            setClaimButtonActions();
        } else if (tableType.equalsIgnoreCase("Dependant")) {
            DependantTable dependantTable = new DependantTable(
                    CustomerQueryType.
                            QueryType.
                            GET_ALL_DEPENDANT_OF_POLICY_OWNER);
            DependantButtonSet dependantButtonSet = new DependantButtonSet(dependantTable);
            tableViewContainer.getChildren().add(dependantTable);
            buttonSetContainer.getChildren().add(dependantButtonSet);

            setDependantButtonActions();
        } else if (tableType.equalsIgnoreCase("PolicyHolder")) {
            PolicyHolderTable policyHolderTable = new PolicyHolderTable(
                    CustomerQueryType.QueryType.GET_ALL_POLICY_HOLDER_OF_POLICY_OWNER);
            PolicyHolderButtonSet policyHolderButtonSet = new PolicyHolderButtonSet(policyHolderTable);
            tableViewContainer.getChildren().add(policyHolderTable);
            buttonSetContainer.getChildren().add(policyHolderButtonSet);

            setPolicyHolderButtonActions();
        }
    }

    private void setPolicyHolderButtonActions() {
        PolicyHolderButtonSet policyHolderButtonSet = (PolicyHolderButtonSet) buttonSetContainer.getChildren().get(0);

        policyHolderButtonSet.addButton.setOnAction(event -> addPolicyHolder());
        policyHolderButtonSet.updateButton.setOnAction(event -> updatePolicyHolder());
        policyHolderButtonSet.deleteButton.setOnAction(event -> deletePolicyHolder());
    }

    private void setClaimButtonActions() {
        ClaimButtonSet claimButtonSet = (ClaimButtonSet) buttonSetContainer.getChildren().get(0);

        claimButtonSet.addButton.setOnAction(event -> addClaim());
        claimButtonSet.updateButton.setOnAction(event -> updateClaim());
        claimButtonSet.deleteButton.setOnAction(event -> deleteClaim());
    }

    private void deleteClaim() {
        ClaimRepository repository = new ClaimRepository();
        ClaimTable claimTable = (ClaimTable) tableViewContainer.getChildren().get(0);
        Claim claim = claimTable.getSelectionModel().getSelectedItem();
        if (claim != null) {
            repository.removeByID(claim.getId());
            repository.close();
        } else new ErrorAlert("Please select a claim");

    }

    private void updateClaim() {
        ClaimTable claimTable = (ClaimTable) tableViewContainer.getChildren().get(0);
        Claim selectedClaim = claimTable.getSelectionModel().getSelectedItem();
        if (selectedClaim != null) {
            // Call update claim form
            new UpdateClaimForm(selectedClaim);
        }
        else new ErrorAlert("Please select a claim");
    }

    private void addClaim() {
        new FileClaimForm(this);
    }

    private void deletePolicyHolder() {
        CustomerRepository repository = new CustomerRepository();
        PolicyHolderTable tableView = (PolicyHolderTable) tableViewContainer.getChildren().get(0);
        PolicyHolder policyHolder = tableView.getSelectionModel().getSelectedItem();
        if (policyHolder != null) {
            repository.removeByID(policyHolder.getId());
            repository.close();
        } else new ErrorAlert("Please select a policy holder");
    }

    private void updatePolicyHolder() {
        PolicyHolderTable tableView = (PolicyHolderTable) tableViewContainer.getChildren().get(0);
        PolicyHolder selectedPolicyHolder = tableView.getSelectionModel().getSelectedItem();
        if (selectedPolicyHolder != null) {
            new UpdateInfoForm(selectedPolicyHolder, this);
        } else new ErrorAlert("Please select a policyHolder");
    }

    private void addPolicyHolder() {
        new AddPolicyHolderForm(this);
    }

    private void setDependantButtonActions() {
        DependantButtonSet dependantButtonSet = (DependantButtonSet) buttonSetContainer.getChildren().get(0);

        dependantButtonSet.addButton.setOnAction(event -> addDependant());
        dependantButtonSet.deleteButton.setOnAction(event -> deleteDependant());
        dependantButtonSet.updateButton.setOnAction(event -> updateDependant());
    }

    private void deleteDependant() {
        CustomerRepository repository = new CustomerRepository();
        DependantTable tableView = (DependantTable) tableViewContainer.getChildren().get(0);
        Dependant dependant = tableView.getSelectionModel().getSelectedItem();
        if (dependant != null) {
            repository.removeByID(dependant.getId());
            repository.close();
        } else new ErrorAlert("Please select a dependant");
    }

    private void updateDependant() {
        DependantTable tableView = (DependantTable) tableViewContainer.getChildren().get(0);
        Dependant selectedDependant = tableView.getSelectionModel().getSelectedItem();
        if (selectedDependant != null) {
            new UpdateInfoForm(selectedDependant, this);
        } else new ErrorAlert("Please select a dependant");
    }

    private void addDependant() {
        new AddDependantForm(this);
    }

    @Override
    public void refresh() {
        RefreshableTable tableView = (RefreshableTable) tableViewContainer.getChildren().get(0);
        tableView.refreshTable();
    }
}
