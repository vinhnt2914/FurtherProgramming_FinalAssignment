package org.example.app.components.alert;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public abstract class CustomAlert extends VBox {
    @FXML
    private VBox alertPane;
    @FXML
    protected HBox iconContainer;
    @FXML
    private Label messageLabel;
    @FXML
    private Button okButton;

    public CustomAlert(String message) {
        setUpAlert(message);
        modifyAlert();
    }

    private void close(ActionEvent actionEvent) {
        Stage stage = (Stage) alertPane.getScene().getWindow();
        stage.close();
    }

    abstract void modifyAlert();

    private void setUpAlert(String message) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/alert.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            VBox rootPane = fxmlLoader.load();
            Scene scene = new Scene(rootPane);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            this.messageLabel.setText(message);
            okButton.setOnAction(this::close);

            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
