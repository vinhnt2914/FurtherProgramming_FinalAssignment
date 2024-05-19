package org.example.app.components.table;
/**
 * @author Group 11
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.global.CustomerQueryType;
import org.example.model.customer.PolicyOwner;
import org.example.repository.impl.CustomerRepository;

import java.util.List;

public class PolicyOwnerTable extends GenericCustomerTable<PolicyOwner> {
    private TableColumn<PolicyOwner, Double> feeCol;

    public PolicyOwnerTable(CustomerQueryType.QueryType queryType) {
        super(queryType);
    }

    @Override
    void modifyTableView() {
        feeCol = new TableColumn<>("Fee");
        feeCol.setCellValueFactory(new PropertyValueFactory<>("fee"));
        feeCol.setMinWidth(150);
        customerTableView.getColumns().add(feeCol);
    }

    @Override
    public void populateTableView(CustomerQueryType.QueryType queryType) {
        CustomerRepository repository = new CustomerRepository();
        List<PolicyOwner> policyOwnerList = null;
        switch (queryType) {
            case GET_ALL_POLICY_OWNER:
                policyOwnerList = repository.getAllPolicyOwners();
                break;
        }
        ObservableList<PolicyOwner> data = FXCollections.observableArrayList(policyOwnerList);
        customerTableView.setItems(data);
        repository.close();
    }

    @Override
    public void refreshTable() {
        populateTableView(queryType);
    }
}
