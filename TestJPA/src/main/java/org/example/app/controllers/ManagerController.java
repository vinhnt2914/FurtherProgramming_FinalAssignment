package org.example.app.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerController implements Initializable {
    public HBox tableViewContainer;
    public Button acceptButton;
    public Button rejectButton;
    public ChoiceBox<String> swapTableChoiceBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void setUpPage() {
        ObservableList<String> options = FXCollections.observableArrayList("Claim", "Customer", "Insurance Surveyor");
        swapTableChoiceBox.setItems(options);

    }
}
