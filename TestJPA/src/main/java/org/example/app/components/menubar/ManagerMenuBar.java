package org.example.app.components.menubar;

public class ManagerMenuBar extends GenericMenuBar{
    @Override
    void modifyMenuBar() {
        menuBar.getChildren().removeAll(providerMenu, insuranceCardMenu);
    }
}
