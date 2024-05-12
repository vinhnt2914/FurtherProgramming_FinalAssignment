package org.example.app.components.table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import org.example.app.components.form.FileClaimForm;
import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyHolder;
import org.example.repository.impl.CustomerRepository;

import java.util.List;

public class SelectInsuredPersonTable extends GenericCustomerTable<Dependant> {
    private PolicyHolder policyHolder;
    private TableColumn<Dependant, String> actionCol;
    private FileClaimForm fileClaimForm;
    public SelectInsuredPersonTable(FileClaimForm fileClaimForm) {
        super();
        this.fileClaimForm = fileClaimForm;
        setUpStage();
    }

    private void setUpStage() {
        Stage primaryStage = new Stage();

        // Create a scene and set the root node
        Scene scene = new Scene(this);

        // Set the scene onto the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("InsuredPersonTable test");
        primaryStage.show();
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
                    btn.setOnAction(event -> setSelectedInsuredPerson(getTableRow().getItem()));
                    setGraphic(btn);
                }
            }
        });

        customerTableView.getColumns().add(actionCol);
    }

    @Override
    void populateTableView() {
        CustomerRepository repository = new CustomerRepository();
        List<Dependant> dependantList = repository.getAllDependant();
        ObservableList<Dependant> data = FXCollections.observableArrayList(dependantList);
        customerTableView.setItems(data);
        repository.close();
    }

    private Button createSelectButton() {
        Button button = new Button("Select");
        button.setPadding(new Insets(10,20,10,20));
        return button;
    }

    private void setSelectedInsuredPerson(Dependant dependant) {
        System.out.println("Selected: " + dependant);
        fileClaimForm.setInsuredPerson(dependant);
        // Close the table view
        Stage stage = (Stage) customerTableView.getScene().getWindow();
        stage.close();
    }
}
