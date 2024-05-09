package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.model.items.Claim;
import org.example.repository.impl.ClaimRepository;
import org.example.service.ClaimService;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PolicyHolderController implements Initializable {

    @FXML
    private TableView<Claim> claimTable;

    @FXML
    private TableColumn<Claim, String> claimIdColumn;

    @FXML
    private TableColumn<Claim, String> claimDateColumn;

    @FXML
    private TableColumn<Claim, String> examDateColumn;

    @FXML
    private TableColumn<Claim, Double> claimAmountColumn;

    @FXML
    private TableColumn<Claim, String> claimStatusColumn;

    @FXML
    private TableColumn<Claim, String> bankingInfoColumn;

    private ObservableList<Claim> claimsData;

    private ClaimService claimService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        claimService = new ClaimService();
        populateTable();

        claimIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        claimDateColumn.setCellValueFactory(cellData -> cellData.getValue().claimDateProperty().asString());
        examDateColumn.setCellValueFactory(cellData -> cellData.getValue().examDateProperty().asString());
        claimAmountColumn.setCellValueFactory(cellData -> cellData.getValue().claimAmountProperty().asObject());
        claimStatusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty().asString());
        bankingInfoColumn.setCellValueFactory(cellData -> cellData.getValue().bankingInfoProperty());
    }

    public void populateTable() {
        getClaimFromDB();
        claimTable.setItems(claimsData);
    }

    private void getClaimFromDB() {
        ClaimRepository claimRepository = new ClaimRepository();
        List<Claim> claimList = claimRepository.getAll();
        claimsData = FXCollections.observableArrayList(claimList);
        claimRepository.close();
    }

    @FXML
    private void handleFileClaim() {
        try {
            // Load the FXML file for the pop-up window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/FileClaim.fxml"));
            Parent root = loader.load();

            // Create a new stage for the pop-up window
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("File Claim");
            popupStage.setScene(new Scene(root));


//            FileClaimController fileClaimController = loader.getController();
//

//            fileClaimController.setClaimService(claimService);

            // Show the pop-up window
            popupStage.showAndWait(); // This will block the main window until the pop-up is closed

            // Refresh table data after the pop-up window is closed
            populateTable();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., display an error message)
        }
    }
    @FXML
    private void handleUpdateInformation() {
        // Get the selected claim from the table view
        Claim selectedClaim = claimTable.getSelectionModel().getSelectedItem();
        if (selectedClaim != null) {
            try {
                // Load the FXML file for the pop-up window
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UpdateClaim.fxml"));
                Parent root = loader.load();

                // Create a new stage for the pop-up window
                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL);
                popupStage.setTitle("Update Information");
                popupStage.setScene(new Scene(root));

                // Pass the selected claim to the controller of the pop-up window
                UpdateClaimController controller = loader.getController();
                controller.setClaim(selectedClaim);
                controller.setStage(popupStage);

                // Show the pop-up window
                popupStage.showAndWait();

                // Refresh table data after the pop-up window is closed
                populateTable();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception (e.g., display an error message)
            }
        } else {
            // Show an alert if no claim is selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Claim Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a claim to update.");
            alert.showAndWait();
        }
    }

}
