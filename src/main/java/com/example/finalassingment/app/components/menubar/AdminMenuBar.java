package com.example.finalassingment.app.components.menubar;
/**
 * @author Group 11
 */
public class AdminMenuBar extends GenericMenuBar{
    @Override
    void modifyMenuBar() {
        menuBar.getChildren().removeAll(infoMenu, dashboardMenu);
    }
}
