package org.example.app.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import org.example.app.components.sortingSet.ClaimSortingSet;
import org.example.app.components.sortingSet.GenericSortingSet;
import org.example.app.components.table.ClaimTable;
import org.example.global.ClaimQueryType;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ClaimAdminController implements Initializable {
    @FXML
    private HBox tableViewContainer;
    @FXML
    private HBox sortingContainer;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpPage();
    }

    private void setUpPage() {
        ClaimTable claimTable = new ClaimTable(ClaimQueryType.QueryType.GET_ALL);
        this.tableViewContainer.getChildren().add(claimTable);
        ClaimSortingSet sortingSet = new ClaimSortingSet("Sort Claim: ", claimTable);
        sortingContainer.getChildren().add(sortingSet);

        setUpSorting(sortingSet);
    }

    private void setUpSorting(GenericSortingSet sortingSet) {
        ObservableList<String> options = FXCollections.observableArrayList();
        options.add("None");
        options.add("Display NEW claim");
        options.add("Display PROCESSING claim");
        options.add("Display DONE claim");

        ChoiceBox<String> sortOptionChoiceBox = sortingSet.sortOptionChoiceBox;
        sortOptionChoiceBox.setItems(options);
        sortOptionChoiceBox.getSelectionModel().selectFirst();
        sortOptionChoiceBox.setOnAction(this::sort);


    }

    private void sort(ActionEvent actionEvent) {
        ClaimSortingSet sortingSet = (ClaimSortingSet) sortingContainer.getChildren().get(0);
        ClaimTable table = (ClaimTable) sortingSet.table;
        String option = sortingSet.sortOptionChoiceBox.getValue();

        switch (option) {
            case "None" -> table.populateTableView(ClaimQueryType.QueryType.GET_ALL);
            case "Display NEW claim" -> table.populateTableView(ClaimQueryType.QueryType.GET_ALL_NEW);
            case "Display PROCESSING claim" -> table.populateTableView(ClaimQueryType.QueryType.GET_ALL_PROCESSING);
            case "Display DONE claim" -> table.populateTableView(ClaimQueryType.QueryType.GET_ALL_DONE);
        }
    }


}
