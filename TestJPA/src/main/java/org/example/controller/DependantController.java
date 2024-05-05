package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.model.customer.Dependant;
import org.example.model.items.Claim;
import org.example.repository.impl.ClaimRepository;
import org.example.repository.impl.CustomerRepository;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class DependantController implements Initializable {

    @FXML
    private Label dependantInsuranceCard;

    @FXML
    private Label dependantEmail;

    @FXML
    private Label dependantAddress;

    @FXML
    private Label dependantPhone;

    @FXML
    private TableView<Claim> dependantTable;

    @FXML
    private TableColumn<Claim, String> claimIdColumn;

    @FXML
    private TableColumn<Claim, LocalDate> claimDateColumn;

    @FXML
    private TableColumn<Claim, LocalDate> examDateColumn;

    @FXML
    private TableColumn<Claim, Double> claimAmountColumn;

    @FXML
    private TableColumn<Claim, String> claimStatusColumn;

    @FXML
    private TableColumn<Claim, String> bankingInfoColumn;
    private ObservableList<Claim> claimsData;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateDependantFields();
        populateTable();
        // Initialize TableColumn cell value factories
        claimIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        claimDateColumn.setCellValueFactory(cellData -> cellData.getValue().claimDateProperty());
        examDateColumn.setCellValueFactory(cellData -> cellData.getValue().examDateProperty());
        claimAmountColumn.setCellValueFactory(cellData -> cellData.getValue().claimAmountProperty().asObject());
        claimStatusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty().asString());
        bankingInfoColumn.setCellValueFactory(cellData -> cellData.getValue().bankingInfoProperty());
    }
    public void populateDependantFields() {
        CustomerRepository customerRepository = new CustomerRepository();
        // Get a random dependant
        Dependant dependant = customerRepository.getAllDependant().getFirst();
        dependantAddress.setText(dependant.getAddress());
        dependantEmail.setText(dependant.getEmail());
        dependantPhone.setText(dependant.getPhone());
        dependantInsuranceCard.setText(dependant.getInsuranceCard().getCardNumber());
    }
    public void populateTable() {
        getClaimFromDB();
        dependantTable.setItems(claimsData);
    }

    private void getClaimFromDB() {
        ClaimRepository claimRepository = new ClaimRepository();
        List<Claim> claimList = claimRepository.getAll();
        claimsData = FXCollections.observableArrayList(claimList);
        claimRepository.close();
    }


}
