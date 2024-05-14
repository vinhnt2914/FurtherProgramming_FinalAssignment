package org.example.app.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.example.app.components.table.*;
import org.example.global.CustomerQueryType;
import org.example.model.enums.ClaimStatus;
import org.example.model.items.Proposal;

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
        tableViewContainer.getChildren().add(new ClaimTable());
    }

    private void rejectProposal(ActionEvent actionEvent) {
        ProposalTable tableView = checkIfProposalTableView();
        if (tableView != null) {
            tableView.rejectProposal();
        } else System.out.println("Not proposal table");
    }

    private void approveProposal(ActionEvent actionEvent) {
        ProposalTable tableView = checkIfProposalTableView();
        if (tableView != null) {
            tableView.approveProposal();
        } else System.out.println("Not proposal table");
    }

    private ProposalTable checkIfProposalTableView() {
        Node tableView = tableViewContainer.getChildren().getFirst();
        if (tableView instanceof ProposalTable) return (ProposalTable) tableView;
        return null;
    }

    private void swapTable(Event event) {
        String tableType = swapTableChoiceBox.getValue();
        tableViewContainer.getChildren().clear();

        if (tableType.equalsIgnoreCase("Claim")) {
            tableViewContainer.getChildren().add(new ClaimTable());
        } else if (tableType.equalsIgnoreCase("Customer")) {
            tableViewContainer.getChildren().add(new CustomerTable(CustomerQueryType.QueryType.GET_ALL));
        } else if (tableType.equalsIgnoreCase("Surveyor")) {
            tableViewContainer.getChildren().add(new SurveyorTable());
        } else tableViewContainer.getChildren().add(new ProposalTable());
    }
}
