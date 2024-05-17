package org.example.app.components.menubar;

import javafx.scene.control.Hyperlink;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.example.app.controllers.CustomerAdminController;

public class AdminMenuBar extends GenericMenuBar {
    private Hyperlink calculateClaimAmountMenu;

    public AdminMenuBar() {
        super();
    }

    @Override
    void modifyMenuBar() {
        menuBar.getChildren().removeAll(infoMenu, dashboardMenu);

        // Add the "Calculate Claim Amount" button
        calculateClaimAmountMenu = new Hyperlink("Calculate Claim Amount");
        calculateClaimAmountMenu.setFont(new Font(16));
        calculateClaimAmountMenu.setOnAction(event -> showCalculateClaimAmount());

        menuBar.getChildren().add(calculateClaimAmountMenu);
    }

    private void showCalculateClaimAmount() {
        CustomerAdminController controller = new CustomerAdminController();
        controller.showCalculateClaimAmount();
    }
}
