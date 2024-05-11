package org.example.app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import org.example.app.components.ClaimTable;

import java.net.URL;
import java.util.ResourceBundle;

public class DependantController implements Initializable {
    @FXML
    private HBox tableViewContainer;
    @Override
    public synchronized void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewContainer.getChildren().add(new ClaimTable());
    }


}
