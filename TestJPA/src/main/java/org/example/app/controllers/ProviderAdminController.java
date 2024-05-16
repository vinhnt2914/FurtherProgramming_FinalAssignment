package org.example.app.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import org.example.app.components.table.ManagerTable;
import org.example.app.components.table.SurveyorTable;
import org.example.global.ProviderQueryType;

import java.net.URL;
import java.util.ResourceBundle;

public class ProviderAdminController implements Initializable {
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
//        PolicyHolderButtonSet policyHolderButtonSet = new PolicyHolderButtonSet(policyHolderTable);
        this.tableViewContainer.getChildren().setAll(surveyorTable);
//        this.buttonSetContainer.getChildren().add(policyHolderButtonSet);
    }

    private void swapTable(ActionEvent actionEvent) {
        String tableType = swapTableChoiceBox.getValue();

        if (tableType.equalsIgnoreCase("Insurance Surveyor")) {
            SurveyorTable surveyorTable = new SurveyorTable(ProviderQueryType.QueryType.GET_ALL_SURVEYOR);

            tableViewContainer.getChildren().setAll(surveyorTable);
//            buttonSetContainer.getChildren().add(policyHolderButtonSet);

        } else if (tableType.equalsIgnoreCase("Insurance Manager")) {
            ManagerTable managerTable = new ManagerTable(ProviderQueryType.QueryType.GET_ALL_MANAGER);

            tableViewContainer.getChildren().setAll(managerTable);
//            buttonSetContainer.getChildren().add(dependantButtonSet);
        }
    }
}
