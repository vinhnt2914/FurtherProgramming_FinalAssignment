package org.example.app.components.table;
/**
 * @author Group 11
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import org.example.app.components.form.AddPolicyHolderForm;
import org.example.global.CustomerQueryType;
import org.example.model.customer.PolicyOwner;
import org.example.repository.impl.CustomerRepository;

import java.util.List;

public class SelectPolicyOwnerTable extends GenericCustomerTable<PolicyOwner> {
    private TableColumn<PolicyOwner, String> actionCol;
    private AddPolicyHolderForm form;
    private Stage stage;
    public SelectPolicyOwnerTable(CustomerQueryType.QueryType queryType, AddPolicyHolderForm form) {
        super(queryType);
        this.form = form;
        setUpStage();
    }

    private void setUpStage() {
        stage = new Stage();

        // Create a scene and set the root node
        Scene scene = new Scene(this);

        // Set the scene onto the stage
        stage.setScene(scene);
        stage.setTitle("Select Policy Owner Table");
        stage.show();
    }

    @Override
    void modifyTableView() {
        // Create the action col with a select button
        actionCol = new TableColumn<>("Action");
        actionCol.setCellFactory(param -> new TableCell<>() {
            final Button btn = createSelectButton();

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    btn.setOnAction(event -> setSelectedPolicyOwner(getTableRow().getItem()));
                    setGraphic(btn);
                }
            }
        });

        customerTableView.getColumns().add(actionCol);
    }

    private Button createSelectButton() {
        Button button = new Button("Select");
        button.setPadding(new Insets(10,20,10,20));
        return button;
    }

    private void setSelectedPolicyOwner(PolicyOwner policyOwner) {
        System.out.println("Selected: " + policyOwner);
        form.setPolicyOwner(policyOwner);
        // Close the table view
        close();
    }

    private void close() {
        stage.close();
    }

    @Override
    public void populateTableView(CustomerQueryType.QueryType queryType) {
        CustomerRepository repository = new CustomerRepository();
        List<PolicyOwner> policyOwnerList = repository.getAllPolicyOwners();
        ObservableList<PolicyOwner> data = FXCollections.observableArrayList(policyOwnerList);
        customerTableView.setItems(data);
        repository.close();
    }

    @Override
    public void refreshTable() {
        populateTableView(queryType);
    }


}
