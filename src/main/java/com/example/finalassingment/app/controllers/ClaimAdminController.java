package com.example.finalassingment.app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import com.example.finalassingment.app.components.sorting.ClaimAmountSortingForm;
import com.example.finalassingment.app.components.sortingSet.ClaimSortingSet;
import com.example.finalassingment.app.components.table.ClaimTable;
import com.example.finalassingment.global.ClaimQueryType;
import com.example.finalassingment.model.enums.ClaimStatus;
import com.example.finalassingment.model.items.Claim;
import com.example.finalassingment.repository.impl.ClaimRepository;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClaimAdminController implements Initializable {
    @FXML
    private HBox tableViewContainer;
    @FXML
    private HBox sortingContainer;
    @FXML
    private Label claimAmountLabel;
    @FXML
    private Button filterButton;
    @FXML
    private double totalClaimAmount;
    private List<Claim> originalData;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpPage();
    }

    private void setUpPage() {
        ClaimTable claimTable = new ClaimTable(ClaimQueryType.QueryType.GET_ALL);
        this.tableViewContainer.getChildren().add(claimTable);
        ClaimSortingSet sortingSet = new ClaimSortingSet(claimTable);
        sortingContainer.getChildren().add(sortingSet);

        this.filterButton.setOnAction(this::openSortingForm);
        displayTotalClaimAmount(calculateTotalClaimAmount());

        originalData = claimTable.getItems();
    }

    private void openSortingForm(ActionEvent actionEvent) {
        new ClaimAmountSortingForm(this, originalData);
    }

    public void displayTotalClaimAmount(double claimAmount) {
        claimAmountLabel.setText("$" + claimAmount);
    }

    public double calculateTotalClaimAmount() {
        ClaimRepository repository = new ClaimRepository();
        List<Claim> claimList = repository.getAll();
        double totalClaimAmount = claimList.stream()
                .filter(claim -> claim.getStatus() == ClaimStatus.PROCESSING || claim.getStatus() == ClaimStatus.DONE)
                .mapToDouble(Claim::getClaimAmount)
                .sum();
        repository.close();
        return totalClaimAmount;
    }

}
