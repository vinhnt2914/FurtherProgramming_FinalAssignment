package org.example.app.components.buttonSet;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.event.ActionEvent;

public class DependantButtonSet extends HBox {
    public Button addButton;
    public Button removeButton;
    public Button updateButton;

    public DependantButtonSet() {
        initializeButtons();
        setButtonStyles();
        getChildren().addAll(addButton, removeButton, updateButton);
    }

    private void initializeButtons() {
        addButton = new Button("Add Dependant");
        removeButton = new Button("Remove Dependant");
        updateButton = new Button("Update Dependant");

        // Event handlers will be set in the controller
    }

    private void setButtonStyles() {
        addButton.setStyle("-fx-border-radius: 5; -fx-background-radius: 5px; -fx-background-color: #98FB98; -fx-text-fill: #333;");
        removeButton.setStyle("-fx-border-radius: 5; -fx-background-radius: 5px; -fx-background-color: #FF6F61; -fx-text-fill: #333;");
        updateButton.setStyle("-fx-border-radius: 5; -fx-background-radius: 5px; -fx-background-color: #FFD700; -fx-text-fill: #333;");

        addButton.setMinSize(120, 40);
        removeButton.setMinSize(120, 40);
        updateButton.setMinSize(120, 40);

        addButton.setFont(new Font("Segoe UI", 12));
        removeButton.setFont(new Font("Segoe UI", 12));
        updateButton.setFont(new Font("Segoe UI", 12));
    }
}
