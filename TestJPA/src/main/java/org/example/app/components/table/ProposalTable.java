package org.example.app.components.table;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.model.enums.ClaimStatus;
import org.example.model.items.Claim;
import org.example.model.items.Proposal;
import org.example.model.provider.InsuranceSurveyor;
import org.example.repository.impl.ClaimRepository;
import org.example.repository.impl.ProposalRepository;

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

    public ProposalTable() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/proposalTable.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setUpTableView();
        populateTableView();
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

    private void populateTableView() {
        ProposalRepository repository = new ProposalRepository();
        List<Proposal> proposalList = repository.getAll();
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

            // Remove the proposal after rejection
            ProposalRepository proposalRepository = new ProposalRepository();
            proposalRepository.removeByID(proposal.getId());
            proposalRepository.close();

            refreshTable();
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

            // Remove the proposal after approval
            ProposalRepository proposalRepository = new ProposalRepository();
            proposalRepository.removeByID(proposal.getId());
            proposalRepository.close();

            refreshTable();
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
        populateTableView();
    }
}
