package org.example.app.components.menubar;

public class PolicyOwnerMenuBar extends GenericMenuBar{
    @Override
    void modifyMenuBar() {
        menuBar.getChildren().removeAll(providerMenu, insuranceCardMenu, claimMenu, customerMenu, requestMenu, proposalMenu);
    }
}
