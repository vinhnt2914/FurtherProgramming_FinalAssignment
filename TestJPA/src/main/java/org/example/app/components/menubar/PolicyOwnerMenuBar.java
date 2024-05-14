package org.example.app.components.menubar;

public class PolicyOwnerMenuBar extends GenericMenuBar{
    @Override
    void modifyMenuBar() {
        menuBar.getChildren().removeAll(requestMenu, proposalMenu, insuranceCardMenu, claimMenu, customerMenu);
    }
}
