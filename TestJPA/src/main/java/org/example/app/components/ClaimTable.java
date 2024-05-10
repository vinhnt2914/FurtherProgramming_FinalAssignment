package org.example.app.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.model.enums.ClaimStatus;
import org.example.model.items.Claim;
import org.example.repository.impl.ClaimRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ClaimTable extends TableView<Claim> {
    @FXML TableView<Claim> claimTable;
    @FXML
    private TableColumn<Claim, String> idCol;
    @FXML
    private TableColumn<Claim, String> insuredPersonCol;
    @FXML
    private TableColumn<Claim, String> cardNumberCol;
    @FXML
    private TableColumn<Claim, LocalDate> claimDateCol;
    @FXML
    private TableColumn<Claim, LocalDate> examDateCol;
    @FXML
    private TableColumn<Claim, Double> claimAmountCol;
    @FXML
    private TableColumn<Claim, ClaimStatus> statusCol;
    @FXML
    private TableColumn<Claim, String> bankingInfoCol;
    private ClaimRepository repository;

    public ClaimTable(ClaimRepository repository) {
        // Set up claim repository
        this.repository = repository;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/claimTable.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setUpTableView();
    }


    private void setUpTableView() {
        // Set up table columns
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        insuredPersonCol.setCellValueFactory(new PropertyValueFactory<>("insuredPerson"));
        cardNumberCol.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));
        claimDateCol.setCellValueFactory(new PropertyValueFactory<>("claimDate"));
        examDateCol.setCellValueFactory(new PropertyValueFactory<>("examDate"));
        claimAmountCol.setCellValueFactory(new PropertyValueFactory<>("claimAmount"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        bankingInfoCol.setCellValueFactory(new PropertyValueFactory<>("bankingInfo"));

        // Populate table view data
        populateTableView();
    }

    private void populateTableView() {
        List<Claim> claimList = repository.getAll();
        ObservableList<Claim> data = FXCollections.observableArrayList(claimList);
        claimTable.setItems(data);
        repository.close();
    }

}
