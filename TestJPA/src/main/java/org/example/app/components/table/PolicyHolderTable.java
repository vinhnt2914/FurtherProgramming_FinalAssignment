package org.example.app.components.table;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import org.example.global.CustomerQueryType;
import org.example.global.GlobalVariable;
import org.example.model.customer.PolicyHolder;
import org.example.model.customer.PolicyOwner;
import org.example.repository.impl.CustomerRepository;

import java.util.List;

public class PolicyHolderTable extends GenericCustomerTable<PolicyHolder> {
    private TableColumn<PolicyHolder, String> policyOwnerCol;
    public PolicyHolderTable(CustomerQueryType.QueryType queryType) {
        super(queryType);
    }

    @Override
    void modifyTableView() {
        policyOwnerCol = new TableColumn<>("Policy Owner");
        policyOwnerCol.setMinWidth(150);
        policyOwnerCol.setCellValueFactory(cellData -> {
            PolicyHolder policyHolder = cellData.getValue();
            return new ReadOnlyObjectWrapper<>(policyHolder.getPolicyOwner().getId()).asString();
        });
        customerTableView.getColumns().add(policyOwnerCol);
    }

    @Override
    public void populateTableView(CustomerQueryType.QueryType queryType) {
        CustomerRepository repository = new CustomerRepository();
        List<PolicyHolder> policyHolderList = null;
        switch (queryType) {
            case GET_ALL_POLICY_HOLDER -> policyHolderList = repository.getAllPolicyHolders();
            case GET_ALL_POLICY_HOLDER_OF_POLICY_OWNER ->
                    policyHolderList = repository.getAllPolicyHoldersOfPolicyOwner((PolicyOwner) GlobalVariable.getUser());
        }
        ObservableList<PolicyHolder> data = FXCollections.observableArrayList(policyHolderList);
        customerTableView.setItems(data);
        repository.close();
    }

    @Override
    public void refreshTable() {
        populateTableView(queryType);
    }
}
