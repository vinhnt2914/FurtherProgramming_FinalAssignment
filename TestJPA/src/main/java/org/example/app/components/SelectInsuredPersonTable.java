package org.example.app.components;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.model.customer.Dependant;
import org.example.model.customer.PolicyHolder;
import org.example.repository.impl.CustomerRepository;

import java.util.List;

public class SelectInsuredPersonTable extends CustomerTable<Dependant>{
    private CustomerRepository repository;
    private PolicyHolder policyHolder;
    private TableColumn<Dependant, String> actionCol;
    private FileClaimForm fileClaimForm;
    public SelectInsuredPersonTable(FileClaimForm fileClaimForm) {
        this.repository = new CustomerRepository();
        this.fileClaimForm = fileClaimForm;
        setUpTableView();
        setUpStage();
        loadInsuredPerson();
        System.out.println("HELLO");
    }

    private void loadInsuredPerson() {
        List<Dependant> dependantList = repository.getAllDependant();
        ObservableList<Dependant> data = FXCollections.observableArrayList(dependantList);
        customerTableView.setItems(data);
        repository.close();
    }

    private void setUpStage() {
        Stage primaryStage = new Stage();

        // Create a scene and set the root node
        Scene scene = new Scene(this, 819, 551);

        // Set the scene onto the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("InsuredPersonTable test");
        primaryStage.show();
    }

    private void setUpTableView() {
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
