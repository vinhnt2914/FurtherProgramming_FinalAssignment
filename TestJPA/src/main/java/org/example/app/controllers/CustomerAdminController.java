package org.example.app.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
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

        policyHolderButtonSet.viewButton.setOnAction(event -> handleViewPolicyHolder());
        policyHolderButtonSet.editButton.setOnAction(event -> handleEditPolicyHolder());
        policyHolderButtonSet.deleteButton.setOnAction(event -> handleDeletePolicyHolder());
    }

    private void setDependantButtonActions() {
        DependantButtonSet dependantButtonSet = (DependantButtonSet) buttonSetContainer.getChildren().get(0);

        dependantButtonSet.addButton.setOnAction(event -> handleAddDependant());
        dependantButtonSet.removeButton.setOnAction(event -> handleRemoveDependant());
        dependantButtonSet.updateButton.setOnAction(event -> handleUpdateDependant());
    }

    private void handleViewPolicyHolder() {
        TableView<PolicyHolder> tableView = (TableView<PolicyHolder>) tableViewContainer.getChildren().get(0);
        PolicyHolder selectedPolicyHolder = tableView.getSelectionModel().getSelectedItem();
        if (selectedPolicyHolder != null) {
            String details = String.format("ID: %d\nName: %s\nAddress: %s\nEmail: %s\nPhone: %s",
                    selectedPolicyHolder.getId(),
                    selectedPolicyHolder.getFullName(),
                    selectedPolicyHolder.getAddress(),
                    selectedPolicyHolder.getEmail(),
                    selectedPolicyHolder.getPhone());

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Policy Holder Details");
            alert.setHeaderText("Details of the Selected Policy Holder");
            alert.setContentText(details);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Policy Holder Selected");
            alert.setContentText("Please select a policy holder in the table.");
            alert.showAndWait();
        }
    }

    private void handleEditPolicyHolder() {
        TableView<PolicyHolder> tableView = (TableView<PolicyHolder>) tableViewContainer.getChildren().get(0);
        PolicyHolder selectedPolicyHolder = tableView.getSelectionModel().getSelectedItem();
        if (selectedPolicyHolder != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/components/editPolicyHolderForm.fxml"));
                BorderPane root = loader.load(); // Ensure the root is set correctly
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Edit Policy Holder");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(tableView.getScene().getWindow());
                dialogStage.setScene(new Scene(root));

                EditPolicyHolderFormController controller = loader.getController();
                controller.setPolicyHolder(selectedPolicyHolder);
                controller.setDialogStage(dialogStage);

                dialogStage.showAndWait();
                tableView.refresh();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Could not load edit form");
                alert.setContentText("Please try again.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(AlertType.WARNING);
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
            customerRepository.removeByID(selectedPolicyHolder.getId());
            tableView.getItems().remove(selectedPolicyHolder);
            System.out.println("Deleted Policy Holder: " + selectedPolicyHolder.getFullName());
        } else {
            System.out.println("No Policy Holder selected");
        }
    }

    private void handleAddDependant() {
        // Implement add logic, e.g., show an add form
        System.out.println("Adding Dependant");
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
        // Implement update logic, e.g., show an update form
        System.out.println("Updating Dependant");
    }
}
