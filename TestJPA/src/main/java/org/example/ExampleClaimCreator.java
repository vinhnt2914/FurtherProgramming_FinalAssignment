package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.example.model.customer.Beneficiary;
import org.example.model.enums.ClaimStatus;
import org.example.model.items.Claim;
import org.example.service.ClaimService;

import java.time.LocalDate;

public class ExampleClaimCreator {

    @FXML
    private DependantController dependantController;

    public ExampleClaimCreator(DependantController dependantController) {
        this.dependantController = dependantController;
    }

    public void createExampleClaims() {
        // Create a claim service instance
        ClaimService claimService = new ClaimService();

        // Create example claims
        Claim claim1 = claimService.makeClaim()
                .id("1")
                .insuredPerson(new Beneficiary()) // Initialize insuredPerson with a valid Beneficiary object
                .claimDate(LocalDate.of(2023, 1, 15))
                .examDate(LocalDate.of(2023, 1, 20))
                .claimAmount(1000.0)
                .status(ClaimStatus.PROCESSING)
                .bankingInfo("Bank of Example")
                .build();

        Claim claim2 = claimService.makeClaim()
                .id("2")
                .insuredPerson(new Beneficiary()) // Initialize insuredPerson with a valid Beneficiary object
                .claimDate(LocalDate.of(2023, 2, 10))
                .examDate(LocalDate.of(2023, 2, 15))
                .claimAmount(1500.0)
                .status(ClaimStatus.DONE)
                .bankingInfo("Example Credit Union")
                .build();

        Claim claim3 = claimService.makeClaim()
                .id("3")
                .insuredPerson(new Beneficiary()) // Initialize insuredPerson with a valid Beneficiary object
                .claimDate(LocalDate.of(2023, 3, 5))
                .examDate(LocalDate.of(2023, 3, 10))
                .claimAmount(2000.0)
                .status(ClaimStatus.NEW)
                .bankingInfo("Example Bank")
                .build();

        Claim claim4 = claimService.makeClaim()
                .id("4")
                .insuredPerson(new Beneficiary()) // Initialize insuredPerson with a valid Beneficiary object
                .claimDate(LocalDate.of(2023, 4, 12))
                .examDate(LocalDate.of(2023, 4, 18))
                .claimAmount(1800.0)
                .status(ClaimStatus.PROCESSING)
                .bankingInfo("Example Savings")
                .build();

        Claim claim5 = claimService.makeClaim()
                .id("5")
                .insuredPerson(new Beneficiary()) // Initialize insuredPerson with a valid Beneficiary object
                .claimDate(LocalDate.of(2023, 5, 8))
                .examDate(LocalDate.of(2023, 5, 15))
                .claimAmount(2200.0)
                .status(ClaimStatus.DONE)
                .bankingInfo("Example Investment")
                .build();

        Claim claim6 = claimService.makeClaim()
                .id("6")
                .insuredPerson(new Beneficiary()) // Initialize insuredPerson with a valid Beneficiary object
                .claimDate(LocalDate.of(2023, 6, 20))
                .examDate(LocalDate.of(2023, 6, 25))
                .claimAmount(1300.0)
                .status(ClaimStatus.DONE)
                .bankingInfo("Example Credit Card")
                .build();

        Claim claim7 = claimService.makeClaim()
                .id("7")
                .insuredPerson(new Beneficiary()) // Initialize insuredPerson with a valid Beneficiary object
                .claimDate(LocalDate.of(2023, 7, 3))
                .examDate(LocalDate.of(2023, 7, 8))
                .claimAmount(1900.0)
                .status(ClaimStatus.PROCESSING)
                .bankingInfo("Example Mortgage")
                .build();

        // Add example claims to a list
        ObservableList<Claim> exampleClaims = FXCollections.observableArrayList(claim1, claim2, claim3, claim4, claim5, claim6, claim7);

        // Set the example claims data to the controller
        // Set the example claims data to the controller
        dependantController.setClaimsData(exampleClaims);
    }
}
