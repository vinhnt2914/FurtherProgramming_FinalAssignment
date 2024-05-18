package org.example.app.components.menubar;

public class AdminMenuBar extends GenericMenuBar{
    @Override
    void modifyMenuBar() {
        menuBar.getChildren().removeAll(infoMenu, dashboardMenu);
    }
}
