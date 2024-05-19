package com.example.finalassingment.app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import com.example.finalassingment.app.components.table.RequestTable;
import com.example.finalassingment.global.RequestQueryType;

import java.net.URL;
import java.util.ResourceBundle;

public class RequestAdminController implements Initializable {
    @FXML
    private HBox tableViewContainer;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpPage();
    }

    private void setUpPage() {
        RequestTable requestTable = new RequestTable(RequestQueryType.QueryType.GET_ALL, null);
        this.tableViewContainer.getChildren().add(requestTable);
    }
}
