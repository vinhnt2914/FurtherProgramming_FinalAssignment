package com.example.finalassingment.app.components.menubar;

public class PolicyHolderMenuBar extends GenericMenuBar{

    @Override
    void modifyMenuBar() {
        menuBar.getChildren().removeAll(providerMenu, insuranceCardMenu, claimMenu, customerMenu, requestMenu, proposalMenu);
    }

}
