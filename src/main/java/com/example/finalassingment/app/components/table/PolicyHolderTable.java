package com.example.finalassingment.app.components.table;
/**
 * @author Group 11
 */
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import com.example.finalassingment.global.CustomerQueryType;
import com.example.finalassingment.global.GlobalVariable;
import com.example.finalassingment.model.customer.PolicyHolder;
import com.example.finalassingment.model.customer.PolicyOwner;
import com.example.finalassingment.repository.impl.CustomerRepository;

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
