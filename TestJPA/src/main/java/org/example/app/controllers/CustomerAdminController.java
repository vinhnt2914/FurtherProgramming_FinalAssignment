package org.example.app.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.app.components.table.DependantTable;
import org.example.app.components.table.PolicyHolderTable;
import org.example.app.components.buttonset.DependantButtonSet;
import org.example.app.components.buttonset.PolicyHolderButtonSet;
import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyHolder;
import org.example.repository.impl.CustomerRepository;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerAdminController implements Initializable {
    @FXML
    private HBox buttonSetContainer;
    @FXML
    private HBox tableViewContainer;
    @FXML
    private ChoiceBox<String> swapTableChoiceBox;

    private CustomerRepository customerRepository;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerRepository = new CustomerRepository();
        setUpPage();
    }

    private void setUpPage() {
        ObservableList<String> comboBoxOptions = FXCollections.observableArrayList();
        comboBoxOptions.addAll("Policy Holders", "Dependants");
        this.swapTableChoiceBox.getItems().addAll(comboBoxOptions);
        this.swapTableChoiceBox.getSelectionModel().select("Policy Holders");
        this.swapTableChoiceBox.setOnAction(this::swapTable);
        this.tableViewContainer.getChildren().add(new PolicyHolderTable());
        this.buttonSetContainer.getChildren().add(new PolicyHolderButtonSet());
        setPolicyHolderButtonActions();
    }

    private void swapTable(Event event) {
        String tableType = swapTableChoiceBox.getValue();
        tableViewContainer.getChildren().clear();
        buttonSetContainer.getChildren().clear();

        if (tableType.equalsIgnoreCase("Policy Holders")) {
            PolicyHolderTable policyHolderTable = new PolicyHolderTable();
            PolicyHolderButtonSet policyHolderButtonSet = new PolicyHolderButtonSet();

            tableViewContainer.getChildren().add(policyHolderTable);
            buttonSetContainer.getChildren().add(policyHolderButtonSet);

            setPolicyHolderButtonActions();

        } else if (tableType.equalsIgnoreCase("Dependants")) {
            DependantTable dependantTable = new DependantTable();
            DependantButtonSet dependantButtonSet = new DependantButtonSet();

            tableViewContainer.getChildren().add(dependantTable);
            buttonSetContainer.getChildren().add(dependantButtonSet);

            setDependantButtonActions();
        }
    }

    private void setPolicyHolderButtonActions() {
        PolicyHolderButtonSet policyHolderButtonSet = (PolicyHolderButtonSet) buttonSetContainer.getChildren().get(0);

        policyHolderButtonSet.addButton.setOnAction(event -> handleAddPolicyHolder());
        policyHolderButtonSet.editButton.setOnAction(event -> handleEditPolicyHolder());
        policyHolderButtonSet.deleteButton.setOnAction(event -> handleDeletePolicyHolder());
    }

    private void setDependantButtonActions() {
        DependantButtonSet dependantButtonSet = (DependantButtonSet) buttonSetContainer.getChildren().get(0);

        dependantButtonSet.addButton.setOnAction(event -> handleAddDependant());
        dependantButtonSet.removeButton.setOnAction(event -> handleRemoveDependant());
        dependantButtonSet.updateButton.setOnAction(event -> handleUpdateDependant());
    }

    private void handleAddPolicyHolder() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/components/addPolicyHolderForm.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add Policy Holder");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(tableViewContainer.getScene().getWindow());
            dialogStage.setScene(new Scene(loader.load()));

            AddPolicyHolderFormController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();
            refreshPolicyHolderTable();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load add form");
            alert.setContentText("Please try again.");
            alert.showAndWait();
        }
    }

    private void handleEditPolicyHolder() {
        TableView<PolicyHolder> tableView = (TableView<PolicyHolder>) tableViewContainer.getChildren().get(0);
        PolicyHolder selectedPolicyHolder = tableView.getSelectionModel().getSelectedItem();
        if (selectedPolicyHolder != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/components/editPolicyHolderForm.fxml"));
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Edit Policy Holder");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(tableView.getScene().getWindow());
                dialogStage.setScene(new Scene(loader.load()));

                EditPolicyHolderFormController controller = loader.getController();
                controller.setPolicyHolder(selectedPolicyHolder);
                controller.setDialogStage(dialogStage);

                dialogStage.showAndWait();
                tableView.refresh();

            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Could not load edit form");
                alert.setContentText("Please try again.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Policy Holder Selected");
            alert.setContentText("Please select a policy holder in the table.");
            alert.showAndWait();
        }
    }

    private void handleDeletePolicyHolder() {
        TableView<PolicyHolder> tableView = (TableView<PolicyHolder>) tableViewContainer.getChildren().get(0);
        PolicyHolder selectedPolicyHolder = tableView.getSelectionModel().getSelectedItem();
        if (selectedPolicyHolder != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Deletion");
            confirmationAlert.setHeaderText("Delete Policy Holder");
            confirmationAlert.setContentText("Are you sure you want to delete the selected policy holder?");

            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    customerRepository.removeByID(selectedPolicyHolder.getId());
                    tableView.getItems().remove(selectedPolicyHolder);
                    System.out.println("Deleted Policy Holder: " + selectedPolicyHolder.getFullName());
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/components/addDependantForm.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add Dependant");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(tableViewContainer.getScene().getWindow());
            dialogStage.setScene(new Scene(loader.load()));

            AddDependantFormController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();
            refreshDependantTable();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load add form");
            alert.setContentText("Please try again.");
            alert.showAndWait();
        }
    }

    private void handleRemoveDependant() {
        TableView<Dependant> tableView = (TableView<Dependant>) tableViewContainer.getChildren().get(0);
        Dependant selectedDependant = tableView.getSelectionModel().getSelectedItem();
        if (selectedDependant != null) {
            customerRepository.removeByID(selectedDependant.getId());
            tableView.getItems().remove(selectedDependant);
            System.out.println("Removed Dependant: " + selectedDependant.getFullName());
        } else {
            System.out.println("No Dependant selected");
        }
    }

    private void handleUpdateDependant() {
        TableView<Dependant> tableView = (TableView<Dependant>) tableViewContainer.getChildren().get(0);
        Dependant selectedDependant = tableView.getSelectionModel().getSelectedItem();
        if (selectedDependant != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/components/updateDependantForm.fxml"));
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Update Dependant");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(tableView.getScene().getWindow());
                dialogStage.setScene(new Scene(loader.load()));

                UpdateDependantFormController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.setDependant(selectedDependant);

                dialogStage.showAndWait();
                refreshDependantTable();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Could not load update form");
                alert.setContentText("Please try again.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Dependant Selected");
            alert.setContentText("Please select a dependant in the table.");
            alert.showAndWait();
        }
    }



    private void refreshPolicyHolderTable() {
        PolicyHolderTable policyHolderTable = new PolicyHolderTable();
        tableViewContainer.getChildren().setAll(policyHolderTable);
    }

    private void refreshDependantTable() {
        DependantTable dependantTable = new DependantTable();
        tableViewContainer.getChildren().setAll(dependantTable);
    }
}
