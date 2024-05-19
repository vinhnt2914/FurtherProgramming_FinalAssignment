package com.example.finalassingment.app.components.table;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import com.example.finalassingment.app.components.form.AddSurveyorForm;
import com.example.finalassingment.global.ProviderQueryType;
import com.example.finalassingment.model.provider.InsuranceManager;

public class SelectManagerTable extends ManagerTable{
    private TableColumn<InsuranceManager, String> actionCol;
    private AddSurveyorForm form;
    private Stage stage;
    public SelectManagerTable(ProviderQueryType.QueryType queryType, AddSurveyorForm form) {
        super(queryType);
        this.form = form;
        setUpStage();
    }

    private void setUpStage() {
        stage = new Stage();

        // Create a scene and set the root node
        Scene scene = new Scene(this);

        // Set the scene onto the stage
        stage.setScene(scene);
        stage.setTitle("Select Manager Table");
        stage.show();
    }

    @Override
    void modifyTableView() {
        super.modifyTableView();
        actionCol = new TableColumn<>("Action");
        actionCol.setCellFactory(param -> new TableCell<>() {
            final Button btn = createSelectButton();

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    btn.setOnAction(event -> setSelectedManager(getTableRow().getItem()));
                    setGraphic(btn);
                }
            }
        });

        providerTableView.getColumns().add(actionCol);
    }

    private void setSelectedManager(InsuranceManager manager) {
        form.setManager(manager);
        close();
    }

    private void close() {
        stage.close();
    }

    private Button createSelectButton() {
        Button button = new Button("Select");
        button.setPadding(new Insets(10,20,10,20));
        return button;
    }
}
