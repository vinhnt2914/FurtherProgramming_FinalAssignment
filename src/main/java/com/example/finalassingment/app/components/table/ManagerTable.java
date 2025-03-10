package com.example.finalassingment.app.components.table;
/**
 * @author Group 11
 */
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import com.example.finalassingment.global.ProviderQueryType;
import com.example.finalassingment.model.provider.InsuranceManager;
import com.example.finalassingment.repository.impl.ProviderRepository;

import java.util.List;

public class ManagerTable extends GenericProviderTable<InsuranceManager>{
    private TableColumn<InsuranceManager, List<Integer>> proposalsCol;
    public ManagerTable(ProviderQueryType.QueryType queryType) {
        super(queryType);
    }

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
    void populateTableView(ProviderQueryType.QueryType queryType) {
        // Query type is not necessary as manager is only viewed by admin
        ProviderRepository repository = new ProviderRepository();
        List<InsuranceManager> managerList = repository.getAllManager();
        ObservableList<InsuranceManager> data = FXCollections.observableArrayList(managerList);
        providerTableView.setItems(data);
        repository.close();
    }

    @Override
    public void refreshTable() {
        populateTableView(queryType);
    }
}
