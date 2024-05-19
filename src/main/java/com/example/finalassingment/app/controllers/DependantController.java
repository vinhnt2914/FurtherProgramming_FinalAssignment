package com.example.finalassingment.app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import com.example.finalassingment.app.components.table.ClaimTable;
import com.example.finalassingment.global.ClaimQueryType;

import java.net.URL;
import java.util.ResourceBundle;

public class DependantController implements Initializable {
    @FXML
    private HBox tableViewContainer;
    @Override
    public synchronized void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewContainer.getChildren().add(new ClaimTable(ClaimQueryType.QueryType.GET_ALL_OF_DEPENDANT));
    }


}
