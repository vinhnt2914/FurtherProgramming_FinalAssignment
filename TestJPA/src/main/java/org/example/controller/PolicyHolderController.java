package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.model.items.Claim;
import org.example.repository.impl.ClaimRepository;

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

//    public void initialize() {
//        // Bind table columns with corresponding properties in Claim class
//
//
//        // Populate claim table with example data
//        ClaimRepository claimRepository = new ClaimRepository();
//        CustomerRepository customerRepository = new CustomerRepository();
//        List<Claim> claimList = claimRepository.getAll();
////        List<>
//
//        claimRepository.close();
//        customerRepository.close();
//
//        ObservableList<Claim> exampleClaims = FXCollections.observableArrayList(claimList);
////        ObservableList<Dependant> dependantData = FXCollections.observableArrayList(); // Create empty list for dependant data
////        ExampleDataCreator.createExampleData(exampleClaims, dependantData); // Pass empty list as second parameter
//        claimTable.setItems(exampleClaims);
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
}
