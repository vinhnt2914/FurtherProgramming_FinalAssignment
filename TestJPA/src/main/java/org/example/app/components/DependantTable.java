package org.example.app.components;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.model.customer.Customer;
import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyHolder;
import org.example.repository.impl.CustomerRepository;
import org.example.utility.GUIUtils;

import java.net.URL;

public class DependantTable extends CustomerTable{
    private TableColumn<Customer, Integer> policyHolderCol;
    private TableColumn<Customer, Integer> policyOwnerCol;
    public DependantTable(CustomerRepository repository) {
        super(repository);
        policyHolderCol = new TableColumn<>("Policy Holder");
        policyOwnerCol = new TableColumn<>("Policy Owner");
        policyHolderCol.setCellValueFactory(cellData -> {
            Dependant dependant = (Dependant) cellData.getValue();
            return new ReadOnlyObjectWrapper<>(dependant.getPolicyHolder().getId());
        });
        policyOwnerCol.setCellValueFactory(cellData -> {
            Dependant dependant = (Dependant) cellData.getValue();
            return new ReadOnlyObjectWrapper<>(dependant.getPolicyOwner().getId());
        });

        policyHolderCol.setMinWidth(100);
        policyOwnerCol.setMinWidth(100);

        double newWidth = this.getWidth() + policyHolderCol.getWidth() + policyOwnerCol.getWidth();

        this.getColumns().add(policyHolderCol);
        this.resize(newWidth, this.getHeight());
//        GUIUtils.autoResizeColumns(this);
    }
}
