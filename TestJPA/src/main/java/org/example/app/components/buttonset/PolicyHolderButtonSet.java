package org.example.app.components.buttonSet;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.event.ActionEvent;

public class PolicyHolderButtonSet extends HBox {
    public Button addButton;
    public Button editButton;
    public Button deleteButton;

    public PolicyHolderButtonSet() {
        initializeButtons();
        setButtonStyles();
        getChildren().addAll(addButton, editButton, deleteButton);
    }

    private void initializeButtons() {
        addButton = new Button("Add Policy Holder");
        editButton = new Button("Edit Policy Holder");
        deleteButton = new Button("Delete Policy Holder");

    }

    private void setButtonStyles() {
        addButton.setStyle("-fx-border-radius: 5; -fx-background-radius: 5px; -fx-background-color: #98FB98; -fx-text-fill: #333;");
        editButton.setStyle("-fx-border-radius: 5; -fx-background-radius: 5px; -fx-background-color: #FFD700; -fx-text-fill: #333;");
        deleteButton.setStyle("-fx-border-radius: 5; -fx-background-radius: 5px; -fx-background-color: #FF6F61; -fx-text-fill: #333;");

        addButton.setMinSize(120, 40);
        editButton.setMinSize(120, 40);
        deleteButton.setMinSize(120, 40);

        addButton.setFont(new Font("Segoe UI", 12));
        editButton.setFont(new Font("Segoe UI", 12));
        deleteButton.setFont(new Font("Segoe UI", 12));
    }
}
