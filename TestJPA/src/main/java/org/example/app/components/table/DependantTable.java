package org.example.app.components.table;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import org.example.model.customer.Dependant;
import org.example.repository.impl.CustomerRepository;

import java.util.List;

public class DependantTable extends GenericCustomerTable<Dependant> {
    private TableColumn<Dependant, Integer> policyHolderCol;
    private TableColumn<Dependant, Integer> policyOwnerCol;
    public DependantTable() {
        super();
    }


    @Override
    void modifyTableView() {
        policyHolderCol = new TableColumn<>("Policy Holder");
        policyOwnerCol = new TableColumn<>("Policy Owner");
        policyHolderCol.setCellValueFactory(cellData -> {
            Dependant dependant = cellData.getValue();
            return new ReadOnlyObjectWrapper<>(dependant.getPolicyHolder().getId());
        });
        policyOwnerCol.setCellValueFactory(cellData -> {
            Dependant dependant = cellData.getValue();
            return new ReadOnlyObjectWrapper<>(dependant.getPolicyOwner().getId());
        });

        policyHolderCol.setMinWidth(200);
        policyOwnerCol.setMinWidth(200);

        double newWidth = customerTableView.getWidth() + policyHolderCol.getWidth() + policyOwnerCol.getWidth();

        customerTableView.getColumns().add(policyHolderCol);
        customerTableView.getColumns().add(policyOwnerCol);
        customerTableView.resize(newWidth + 50, this.getHeight()); // Extra 50 for resize
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
