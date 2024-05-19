package com.example.finalassingment.app.controllers;

import com.example.finalassingment.app.components.alert.SuccessAlert;
import com.example.finalassingment.app.components.table.ClaimTable;
import com.example.finalassingment.app.components.table.CustomerTable;
import com.example.finalassingment.app.components.table.ProposalTable;
import com.example.finalassingment.app.components.table.SurveyorTable;
import com.example.finalassingment.global.ProposalQueryType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import com.example.finalassingment.app.components.alert.ErrorAlert;
import com.example.finalassingment.app.components.sortingSet.ClaimSortingSet;
import com.example.finalassingment.app.components.sortingSet.CustomerSortingSet;
import com.example.finalassingment.global.ClaimQueryType;
import com.example.finalassingment.global.CustomerQueryType;
import com.example.finalassingment.global.ProviderQueryType;

import java.net.URL;
import java.util.ResourceBundle;

public class InsuranceManagerController implements Initializable {
    @FXML
    private ChoiceBox<String> swapTableChoiceBox;
    @FXML
    private Button approveButton;
    @FXML
    private Button rejectButton;
    @FXML
    private HBox tableViewContainer;
    @FXML
    private HBox sortingContainer;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpPage();
    }

    private void setUpPage() {
        ObservableList<String> options = FXCollections.observableArrayList();
        options.addAll("Claim", "Customer", "Surveyor", "Proposal");
        this.swapTableChoiceBox.setItems(options);
        swapTableChoiceBox.getSelectionModel().selectFirst();
        swapTableChoiceBox.setOnAction(this::swapTable);
        approveButton.setOnAction(this::approveProposal);
        rejectButton.setOnAction(this::rejectProposal);

        ClaimTable claimTable = new ClaimTable(ClaimQueryType.QueryType.GET_ALL);
        ClaimSortingSet sortingSet = new ClaimSortingSet(claimTable);

        tableViewContainer.getChildren().setAll(claimTable);
        sortingContainer.getChildren().setAll(sortingSet);
    }

    private void rejectProposal(ActionEvent actionEvent) {
        ProposalTable tableView = checkIfProposalTableView();
        if (tableView != null) {
            tableView.rejectProposal();
            new SuccessAlert("Rejected");
        } else {
            new ErrorAlert("This is not proposal table");
        }
    }

    private void approveProposal(ActionEvent actionEvent) {
        ProposalTable tableView = checkIfProposalTableView();
        if (tableView != null) {
            tableView.approveProposal();
            new SuccessAlert("Approved");
        } else {
            new ErrorAlert("This is not proposal table");
        }
    }

    private ProposalTable checkIfProposalTableView() {
        Node tableView = tableViewContainer.getChildren().getFirst();
        if (tableView instanceof ProposalTable) return (ProposalTable) tableView;
        return null;
    }

    private void swapTable(Event event) {
        String tableType = swapTableChoiceBox.getValue();

        if (tableType.equalsIgnoreCase("Claim")) {
            ClaimTable claimTable = new ClaimTable(ClaimQueryType.QueryType.GET_ALL);
            ClaimSortingSet sortingSet = new ClaimSortingSet(claimTable);

            tableViewContainer.getChildren().setAll(claimTable);
            sortingContainer.getChildren().setAll(sortingSet);

        } else if (tableType.equalsIgnoreCase("Customer")) {
            CustomerTable customerTable = new CustomerTable(CustomerQueryType.QueryType.GET_ALL);
            CustomerSortingSet sortingSet = new CustomerSortingSet(customerTable);

            tableViewContainer.getChildren().setAll(customerTable);
            sortingContainer.getChildren().setAll(sortingSet);
        } else if (tableType.equalsIgnoreCase("Surveyor")) {
            tableViewContainer.getChildren().setAll(new SurveyorTable(ProviderQueryType.QueryType.GET_ALL_SURVEYOR_OF_MANAGER));
            sortingContainer.getChildren().clear();
        } else {
            tableViewContainer.getChildren().setAll(new ProposalTable(ProposalQueryType.QueryType.GET_ALL_TO_MANAGER));
            sortingContainer.getChildren().clear();
        }
    }
}
