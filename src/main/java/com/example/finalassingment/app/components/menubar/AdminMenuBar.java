package com.example.finalassingment.app.components.menubar;

public class AdminMenuBar extends GenericMenuBar{
    @Override
    void modifyMenuBar() {
        menuBar.getChildren().removeAll(infoMenu, dashboardMenu);
    }
}
