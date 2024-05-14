package org.example.app.components.table;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import org.example.global.CustomerQueryType;
import org.example.global.GlobalVariable;
import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyHolder;
import org.example.model.customer.PolicyOwner;
import org.example.repository.impl.CustomerRepository;

import java.util.List;

public class PolicyHolderTable extends GenericCustomerTable<PolicyHolder> {
    private TableColumn<PolicyHolder, List<Integer>> dependantsCol;
    public PolicyHolderTable(CustomerQueryType.QueryType queryType) {
        super(queryType);
    }

    @Override
    void modifyTableView() {
        dependantsCol = new TableColumn<>("Dependants");
        dependantsCol.setMinWidth(150);
        dependantsCol.setCellValueFactory(cellData -> {
            PolicyHolder policyHolder = cellData.getValue();
            return new ReadOnlyObjectWrapper<>(policyHolder.getDependantsIds());
        });
        customerTableView.getColumns().add(dependantsCol);
    }

    @Override
    void populateTableView(CustomerQueryType.QueryType queryType) {
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
}
