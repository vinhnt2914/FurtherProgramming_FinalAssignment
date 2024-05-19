package org.example.app.controllers;
/**
 * @author Group 11
 */
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import org.example.app.components.table.ProposalTable;


import java.net.URL;
import java.util.ResourceBundle;

public class ProposalAdminController implements Initializable {
    public HBox tableViewContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpPage();
    }

    private void setUpPage() {
        ProposalTable proposalTable = new ProposalTable();
        this.tableViewContainer.getChildren().add(proposalTable);
    }
}
