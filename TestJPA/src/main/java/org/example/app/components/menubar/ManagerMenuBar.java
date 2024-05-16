package org.example.app.components.menubar;

public class ManagerMenuBar extends GenericMenuBar{
    @Override
    void modifyMenuBar() {
        // Leave out proposal, customer and claim menu item
        menuBar.getChildren().removeAll(dashboardMenu, providerMenu, insuranceCardMenu, requestMenu);
    }
}
