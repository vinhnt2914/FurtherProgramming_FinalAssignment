package org.example.app.controllers;
/**
 * @author Group 11
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.example.app.components.alert.ErrorAlert;
import org.example.app.components.sortingSet.ClaimSortingSet;
import org.example.app.components.sortingSet.CustomerSortingSet;
import org.example.app.components.table.*;
import org.example.global.ClaimQueryType;
import org.example.global.CustomerQueryType;
import org.example.global.ProviderQueryType;


import java.net.URL;
import java.util.List;
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
        } else {
            new ErrorAlert("This is not proposal table");
        }
    }

    private void approveProposal(ActionEvent actionEvent) {
        ProposalTable tableView = checkIfProposalTableView();
        if (tableView != null) {
            tableView.approveProposal();
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
            tableViewContainer.getChildren().setAll(new ProposalTable());
            sortingContainer.getChildren().clear();
        }
    }
}
