package org.example.app.components.buttonSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import org.example.app.components.alert.ErrorAlert;
import org.example.app.components.form.AddDependantForm;
import org.example.app.components.form.FileClaimForm;
import org.example.app.components.form.UpdateDependantForm;
import org.example.app.components.table.ClaimTable;
import org.example.app.components.table.DependantTable;
import org.example.model.customer.Dependant;
import org.example.model.items.Claim;
import org.example.repository.impl.ClaimRepository;
import org.example.repository.impl.CustomerRepository;

import java.io.IOException;


public class ClaimButtonSet extends HBox {
    @FXML
    private HBox claimButtonSet;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button updateButton;
    private ClaimTable claimTable;

    public ClaimButtonSet(ClaimTable claimTable) {
        this.claimTable = claimTable;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/claimButtonSet.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setUpButtonSet();
    }

    private void setUpButtonSet() {
        this.addButton.setOnAction(this::add);
        this.deleteButton.setOnAction(this::delete);
        this.updateButton.setOnAction(this::update);
    }

    private void update(ActionEvent actionEvent) {
        Claim selectedClaim = claimTable.getSelectionModel().getSelectedItem();
        if (selectedClaim != null) {
            // Call update claim form
        }
        else new ErrorAlert("Please select a dependant");
    }

    private void delete(ActionEvent actionEvent) {
        ClaimRepository repository = new ClaimRepository();
        Claim claim = claimTable.getSelectionModel().getSelectedItem();
        repository.removeByID(claim.getId());
        repository.close();
    }

    private void add(ActionEvent actionEvent) {
        new FileClaimForm();
    }
}
