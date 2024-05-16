package org.example.app.components.table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.global.ClaimQueryType;
import org.example.global.GlobalVariable;
import org.example.model.customer.PolicyHolder;
import org.example.model.enums.ClaimStatus;
import org.example.model.items.Claim;
import org.example.repository.impl.ClaimRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ClaimTable extends TableView<Claim> implements RefreshableTable {
    @FXML TableView<Claim> claimTable;
    @FXML
    private TableColumn<Claim, String> idCol;
    @FXML
    private TableColumn<Claim, Integer> insuredPersonCol;
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
    public ClaimQueryType.QueryType queryType;

    public ClaimTable(ClaimQueryType.QueryType queryType) {
        this.queryType = queryType;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/table/claimTable.fxml"));
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
        populateTableView(queryType);
    }

    public void populateTableView(ClaimQueryType.QueryType queryType) {
        // Data is not formatted
        ClaimRepository repository = new ClaimRepository();
        List<Claim> claimList = null;
        switch (queryType) {
            case GET_OF_POLICY_HOLDER -> {
                PolicyHolder policyHolder = (PolicyHolder) GlobalVariable.getUser();
                claimList = repository.getClaimsOfPolicyHolder(policyHolder);
            }
            case GET_ALL ->  claimList = repository.getAll();
            case GET_ALL_NEW -> claimList = repository.getAllNew();
            case GET_ALL_PROCESSING -> claimList = repository.getAllProcessing();
            case GET_ALL_DONE -> claimList = repository.getAllDone();
            case GET_ALL_OF_POLICYHOLDER -> {
                PolicyHolder policyHolder = (PolicyHolder) GlobalVariable.getUser();
                claimList = repository.getAllClaimsOfPolicyHolder(policyHolder);
            }
        }

        // Format the data
        ObservableList<Claim> data = FXCollections.observableArrayList(claimList);
        claimTable.setItems(data);
        repository.close();
    }

    @Override
    public void refreshTable() {
        populateTableView(queryType);
    }
}
