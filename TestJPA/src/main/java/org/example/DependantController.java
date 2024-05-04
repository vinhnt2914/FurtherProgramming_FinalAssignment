package org.example;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.model.items.Claim;

import java.time.LocalDate;

public class DependantController {

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

    public void initialize() {
        // Initialize TableColumn cell value factories
        claimIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        claimDateColumn.setCellValueFactory(cellData -> cellData.getValue().claimDateProperty());
        examDateColumn.setCellValueFactory(cellData -> cellData.getValue().examDateProperty());
        claimAmountColumn.setCellValueFactory(cellData -> cellData.getValue().claimAmountProperty().asObject());
        claimStatusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty().asString());
        bankingInfoColumn.setCellValueFactory(cellData -> cellData.getValue().bankingInfoProperty());
    }

    public void setClaimsData(ObservableList<Claim> claimsData) {
        this.claimsData = claimsData;
        dependantTable.setItems(claimsData);
    }
}
