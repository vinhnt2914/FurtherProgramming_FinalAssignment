package org.example.app.components.table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.model.provider.InsuranceManager;
import org.example.repository.impl.InsuranceManagerRepository;

import javax.swing.text.TableView;
import java.util.List;

public class ManagerTable extends GenericProviderTable<InsuranceManager> {
    @Override
    void modifyTableView() {
        // Do nothing for now
    }

    @Override
    void populateTableView() {
        InsuranceManagerRepository repository = new InsuranceManagerRepository();
        List<InsuranceManager> managerList = repository.getAll();
        ObservableList<InsuranceManager> data = FXCollections.observableArrayList(managerList);
        providerTableView.setItems(data);
        repository.close();
    }
}
