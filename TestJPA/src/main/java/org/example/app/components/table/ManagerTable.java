package org.example.app.components.table;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import org.example.model.provider.InsuranceManager;
import org.example.repository.impl.ProviderRepository;

import java.util.List;

public class ManagerTable extends GenericProviderTable<InsuranceManager> {
    private TableColumn<InsuranceManager, List<Integer>> proposalsCol;
    @Override
    void modifyTableView() {
        proposalsCol = new TableColumn<>("Proposals");
        // Do nothing for now
        proposalsCol.setCellValueFactory(cellData -> {
            InsuranceManager manager = cellData.getValue();
            return new ReadOnlyObjectWrapper<>(manager.getProposalIDs());
        });
        proposalsCol.setMinWidth(200);

        providerTableView.getColumns().addAll(proposalsCol);
    }

    @Override
    void populateTableView() {
        ProviderRepository repository = new ProviderRepository();
        List<InsuranceManager> managerList = repository.getAllManager();
        ObservableList<InsuranceManager> data = FXCollections.observableArrayList(managerList);
        providerTableView.setItems(data);
        repository.close();
    }

    @Override
    public void refreshTable() {
        populateTableView();
    }
}
