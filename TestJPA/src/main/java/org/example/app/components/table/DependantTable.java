package org.example.app.components.table;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyHolder;
import org.example.repository.impl.CustomerRepository;

import java.util.List;

public class DependantTable extends GenericCustomerTable<Dependant> {

    @Override
    void modifyTableView() {
        TableColumn<Dependant, Integer> policyHolderIdColumn = new TableColumn<>("Policy Holder ID");
        policyHolderIdColumn.setCellValueFactory(cellData -> {
            PolicyHolder policyHolder = cellData.getValue().getPolicyHolder();
            if (policyHolder != null) {
                return new ReadOnlyObjectWrapper<>(policyHolder.getId());
            } else {
                return new ReadOnlyObjectWrapper<>(null);
            }
        });

        customerTableView.getColumns().addAll(policyHolderIdColumn);
    }

    @Override
    void populateTableView() {
        CustomerRepository repository = new CustomerRepository();
        List<Dependant> dependantList = repository.getAllDependant();
        ObservableList<Dependant> data = FXCollections.observableArrayList(dependantList);
        customerTableView.setItems(data);
        repository.close();
    }
}
