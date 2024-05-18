package org.example.app.components.menubar;

public class DependantMenuBar extends GenericMenuBar{
    @Override
    void modifyMenuBar() {
        menuBar.getChildren().removeAll(providerMenu, insuranceCardMenu, claimMenu, customerMenu, requestMenu, proposalMenu);
    }
}
