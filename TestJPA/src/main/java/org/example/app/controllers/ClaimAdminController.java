package org.example.app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.example.app.components.sorting.ClaimAmountSortingForm;
import org.example.app.components.sortingSet.ClaimSortingSet;
import org.example.app.components.table.ClaimTable;
import org.example.global.ClaimQueryType;
import org.example.model.enums.ClaimStatus;
import org.example.model.items.Claim;
import org.example.repository.impl.ClaimRepository;

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
    }

    private void openSortingForm(ActionEvent actionEvent) {
        new ClaimAmountSortingForm(this);
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
