package com.example.finalassingment.app.components.table;
/**
 * @author Group 11
 */
import com.example.finalassingment.global.GlobalVariable;
import com.example.finalassingment.global.ProposalQueryType;
import com.example.finalassingment.model.provider.InsuranceManager;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.finalassingment.model.enums.ClaimStatus;
import com.example.finalassingment.model.items.Claim;
import com.example.finalassingment.model.items.Proposal;
import com.example.finalassingment.model.provider.InsuranceSurveyor;
import com.example.finalassingment.repository.impl.ClaimRepository;
import com.example.finalassingment.repository.impl.ProposalRepository;

import java.io.IOException;
import java.util.List;

public class ProposalTable extends TableView<Proposal> implements RefreshableTable {
    @FXML
    private TableView<Proposal> proposalTableView;
    @FXML
    private TableColumn<Proposal, Integer> idCol;
    @FXML
    private TableColumn<Proposal, String> claimCol;
    @FXML
    private TableColumn<Proposal, Integer> surveyorCol;
    @FXML
    private TableColumn<Proposal, String> messageCol;
    private ProposalQueryType.QueryType queryType;
    public ProposalTable(ProposalQueryType.QueryType queryType) {
        this.queryType = queryType;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/table/proposalTable.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setUpTableView();
        populateTableView(queryType);
    }

    private void setUpTableView() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        claimCol.setCellValueFactory(cellData -> {
            Claim claim = cellData.getValue().getClaim();
            return new ReadOnlyObjectWrapper<>(claim.getId());
        });
        surveyorCol.setCellValueFactory(cellData -> {
            InsuranceSurveyor surveyor = cellData.getValue().getInsuranceSurveyor();
            return new ReadOnlyObjectWrapper<>(surveyor.getId());
        });
        messageCol.setCellValueFactory(new PropertyValueFactory<>("message"));
    }

    private void populateTableView(ProposalQueryType.QueryType queryType) {
        ProposalRepository repository = new ProposalRepository();
        List<Proposal> proposalList = null;

        switch (queryType) {
            case GET_ALL -> proposalList = repository.getAll();
            case GET_ALL_TO_MANAGER -> proposalList = repository.getAllToManager((InsuranceManager) GlobalVariable.getUser());
        }

        ObservableList<Proposal> data = FXCollections.observableArrayList(proposalList);
        proposalTableView.setItems(data);
        repository.close();
    }

    public void rejectProposal() {
        Proposal proposal = proposalTableView.getSelectionModel().getSelectedItem();
        if (proposal != null) {
            Claim claim = proposal.getClaim();
            claim.setStatus(ClaimStatus.REJECTED);
            updateProposal(claim);
        } else {
            System.out.println("Select something!");
        }
    }

    public void approveProposal() {
        Proposal proposal = proposalTableView.getSelectionModel().getSelectedItem();
        if (proposal != null) {
            Claim claim = proposal.getClaim();
            claim.setStatus(ClaimStatus.PROCESSING);
            updateProposal(claim);
        } else {
            System.out.println("Select something!");
        }
    }

    private void updateProposal(Claim claim) {
        ClaimRepository repository = new ClaimRepository();
        repository.update(claim);
        repository.close();
    }

    @Override
    public void refreshTable() {
        populateTableView(queryType);
    }
}
