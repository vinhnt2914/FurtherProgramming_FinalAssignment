package org.example.app.components.table;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import org.example.app.components.form.ProposalForm;
import org.example.model.provider.InsuranceManager;

public class SelectManagerTable extends ManagerTable{
    private TableColumn<InsuranceManager, String> actionCol;
    private ProposalForm proposalForm;

    public SelectManagerTable(ProposalForm proposalForm) {
        super();
        this.proposalForm = proposalForm;
        setUpStage();
    }

    private void setUpStage() {
        Stage primaryStage = new Stage();

        // Create a scene and set the root node
        Scene scene = new Scene(this);

        // Set the scene onto the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("InsuredPersonTable test");
        primaryStage.show();
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
        proposalForm.setManager(manager);
    }

    private Button createSelectButton() {
        Button button = new Button("Select");
        button.setPadding(new Insets(10,20,10,20));
        return button;
    }
}
