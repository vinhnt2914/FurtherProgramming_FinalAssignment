package org.example.app.controllers;
/**
 * @author Group 11
 */
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import org.example.app.components.table.ClaimTable;
import org.example.global.ClaimQueryType;

import java.net.URL;
import java.util.ResourceBundle;

public class DependantController implements Initializable {
    @FXML
    private HBox tableViewContainer;
    @Override
    public synchronized void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewContainer.getChildren().add(new ClaimTable(ClaimQueryType.QueryType.GET_ALL_OF_DEPENDANT));
    }


}
