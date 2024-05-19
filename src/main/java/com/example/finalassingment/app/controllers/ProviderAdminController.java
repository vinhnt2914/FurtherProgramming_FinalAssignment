package com.example.finalassingment.app.controllers;

import com.example.finalassingment.app.components.alert.ErrorAlert;
import com.example.finalassingment.model.User;
import com.example.finalassingment.repository.impl.UserRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import com.example.finalassingment.app.components.buttonSet.ManagerButtonSet;
import com.example.finalassingment.app.components.buttonSet.SurveyorButtonSet;
import com.example.finalassingment.app.components.form.AddManagerForm;
import com.example.finalassingment.app.components.form.AddSurveyorForm;
import com.example.finalassingment.app.components.form.UpdateInfoForm;
import com.example.finalassingment.app.components.table.ManagerTable;
import com.example.finalassingment.app.components.table.RefreshableTable;
import com.example.finalassingment.app.components.table.SurveyorTable;
import com.example.finalassingment.global.ProviderQueryType;
import com.example.finalassingment.model.provider.InsuranceManager;
import com.example.finalassingment.model.provider.InsuranceSurveyor;
import com.example.finalassingment.repository.impl.ProviderRepository;

import java.net.URL;
import java.util.ResourceBundle;

public class ProviderAdminController implements Initializable, RefreshableController {
    public HBox buttonSetContainer;
    public ChoiceBox<String> swapTableChoiceBox;
    public HBox tableViewContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpPage();
    }

    private void setUpPage() {
        ObservableList<String> comboBoxOptions = FXCollections.observableArrayList();
        comboBoxOptions.addAll("Insurance Surveyor", "Insurance Manager");
        this.swapTableChoiceBox.getItems().addAll(comboBoxOptions);
        this.swapTableChoiceBox.getSelectionModel().select("Insurance Surveyor");
        this.swapTableChoiceBox.setOnAction(this::swapTable);

        SurveyorTable surveyorTable = new SurveyorTable(ProviderQueryType.QueryType.GET_ALL_SURVEYOR);
        SurveyorButtonSet buttonSet = new SurveyorButtonSet(surveyorTable);
        this.tableViewContainer.getChildren().setAll(surveyorTable);
        this.buttonSetContainer.getChildren().add(buttonSet);

        setUpSurveyorButtonActions();
    }

    private void setUpSurveyorButtonActions() {
        SurveyorButtonSet buttonSet = (SurveyorButtonSet) buttonSetContainer.getChildren().get(0);

        buttonSet.addButton.setOnAction(event -> addSurveyor());
        buttonSet.updateButton.setOnAction(event -> updateSurveyor());
        buttonSet.deleteButton.setOnAction(event -> deleteSurveyor());
    }

    private void deleteSurveyor() {
        SurveyorTable tableView = (SurveyorTable) tableViewContainer.getChildren().get(0);
        InsuranceSurveyor selectedSurveyor = tableView.getSelectionModel().getSelectedItem();
        if (selectedSurveyor != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Deletion");
            confirmationAlert.setHeaderText("Delete Surveyor");
            confirmationAlert.setContentText("Are you sure you want to delete the selected surveyor?");

            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    UserRepository repository = new UserRepository();
                    repository.removeByID(selectedSurveyor.getId());
                    tableView.getItems().remove(selectedSurveyor);
                    System.out.println("Removed Surveyor: " + selectedSurveyor.getFullName());
                    repository.close();
                }
            });
        } else {
            new ErrorAlert("No Surveyor selected");
        }
    }

    private void updateSurveyor() {
        SurveyorTable surveyorTable = (SurveyorTable) tableViewContainer.getChildren().get(0);
        InsuranceSurveyor surveyor = surveyorTable.getSelectionModel().getSelectedItem();
        if (surveyor != null) {
            new UpdateInfoForm(surveyor, this);
        } else {
            new ErrorAlert("No Surveyor selected");
        }
    }
    private void addSurveyor() {
        new AddSurveyorForm(this);
    }

    private void setUpManagerButtonActions() {
        ManagerButtonSet buttonSet = (ManagerButtonSet) buttonSetContainer.getChildren().get(0);

        buttonSet.addButton.setOnAction(event -> addManager());
        buttonSet.updateButton.setOnAction(event -> updateManager());
        buttonSet.deleteButton.setOnAction(event -> deleteManager());
    }

    private void deleteManager() {
        ManagerTable tableView = (ManagerTable) tableViewContainer.getChildren().get(0);
        InsuranceManager selectedManager = tableView.getSelectionModel().getSelectedItem();
        if (selectedManager != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Deletion");
            confirmationAlert.setHeaderText("Delete Manager");
            confirmationAlert.setContentText("Are you sure you want to delete the selected manager?");

            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    UserRepository repository = new UserRepository();
                    repository.removeByID(selectedManager.getId());
                    tableView.getItems().remove(selectedManager);
                    System.out.println("Removed Manager: " + selectedManager.getFullName());
                    repository.close();
                }
            });
        } else {
            new ErrorAlert("No Manager selected");
        }
    }


    private void updateManager() {
        ManagerTable managerTable = (ManagerTable) tableViewContainer.getChildren().get(0);
        InsuranceManager manager = managerTable.getSelectionModel().getSelectedItem();
        if (manager != null) {
            System.out.println("SELECTED: " + manager);
            new UpdateInfoForm(manager, this);
        } else {
            new ErrorAlert("No Manager selected");
        }
    }


    private void addManager() {
        new AddManagerForm(this);
    }

    private void swapTable(ActionEvent actionEvent) {
        String tableType = swapTableChoiceBox.getValue();

        if (tableType.equalsIgnoreCase("Insurance Surveyor")) {
            SurveyorTable surveyorTable = new SurveyorTable(ProviderQueryType.QueryType.GET_ALL_SURVEYOR);
            SurveyorButtonSet buttonSet = new SurveyorButtonSet(surveyorTable);

            tableViewContainer.getChildren().setAll(surveyorTable);
            buttonSetContainer.getChildren().setAll(buttonSet);

            setUpSurveyorButtonActions();

        } else if (tableType.equalsIgnoreCase("Insurance Manager")) {
            ManagerTable managerTable = new ManagerTable(ProviderQueryType.QueryType.GET_ALL_MANAGER);
            ManagerButtonSet buttonSet = new ManagerButtonSet(managerTable);

            tableViewContainer.getChildren().setAll(managerTable);
            buttonSetContainer.getChildren().setAll(buttonSet);

            setUpManagerButtonActions();
        }
    }

    @Override
    public void refresh() {
        RefreshableTable tableView = (RefreshableTable) tableViewContainer.getChildren().get(0);
        tableView.refreshTable();
    }
}
