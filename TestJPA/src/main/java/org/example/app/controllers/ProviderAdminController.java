package org.example.app.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import org.example.app.components.buttonSet.ManagerButtonSet;
import org.example.app.components.buttonSet.PolicyHolderButtonSet;
import org.example.app.components.buttonSet.SurveyorButtonSet;
import org.example.app.components.form.AddManagerForm;
import org.example.app.components.form.AddSurveyorForm;
import org.example.app.components.form.UpdateInfoForm;
import org.example.app.components.table.DependantTable;
import org.example.app.components.table.ManagerTable;
import org.example.app.components.table.RefreshableTable;
import org.example.app.components.table.SurveyorTable;
import org.example.global.ProviderQueryType;
import org.example.model.customer.Dependant;
import org.example.model.provider.InsuranceManager;
import org.example.model.provider.InsuranceSurveyor;
import org.example.repository.impl.CustomerRepository;
import org.example.repository.impl.ProviderRepository;

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
            ProviderRepository repository = new ProviderRepository();

            repository.removeByID(selectedSurveyor.getId());
            tableView.getItems().remove(selectedSurveyor);
            System.out.println("Removed Surveyor: " + selectedSurveyor.getFullName());
            repository.close();
        } else {
            System.out.println("No Surveyor selected");
        }
    }

    private void updateSurveyor() {
        SurveyorTable surveyorTable = new SurveyorTable(ProviderQueryType.QueryType.GET_ALL_SURVEYOR);
//        InsuranceSurveyor surveyor = surveyorTable.getSelectionModel().getSelectedItem();
        new UpdateInfoForm(this);
    }

    private void addSurveyor() {
        new AddSurveyorForm();
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
            ProviderRepository repository = new ProviderRepository();

            repository.removeByID(selectedManager.getId());
            tableView.getItems().remove(selectedManager);
            System.out.println("Removed Manager: " + selectedManager.getFullName());
            repository.close();
        } else {
            System.out.println("No Manager selected");
        }
    }

    private void updateManager() {
        ManagerTable surveyorTable = new ManagerTable(ProviderQueryType.QueryType.GET_ALL_MANAGER);
//        InsuranceSurveyor surveyor = surveyorTable.getSelectionModel().getSelectedItem();
        new UpdateInfoForm(this);
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

        } else if (tableType.equalsIgnoreCase("Insurance Manager")) {
            ManagerTable managerTable = new ManagerTable(ProviderQueryType.QueryType.GET_ALL_MANAGER);
            ManagerButtonSet buttonSet = new ManagerButtonSet(managerTable);

            tableViewContainer.getChildren().setAll(managerTable);
            buttonSetContainer.getChildren().setAll(buttonSet);
        }
    }

    @Override
    public void refresh() {
        RefreshableTable tableView = (RefreshableTable) tableViewContainer.getChildren().get(0);
        tableView.refreshTable();
    }
}
