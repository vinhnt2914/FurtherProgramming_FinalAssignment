package com.example.finalassingment.app.controllers;

import com.example.finalassingment.global.ProposalQueryType;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import com.example.finalassingment.app.components.table.ProposalTable;

import java.net.URL;
import java.util.ResourceBundle;

public class ProposalAdminController implements Initializable {
    public HBox tableViewContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpPage();
    }

    private void setUpPage() {
        ProposalTable proposalTable = new ProposalTable(ProposalQueryType.QueryType.GET_ALL);
        this.tableViewContainer.getChildren().add(proposalTable);
    }
}
