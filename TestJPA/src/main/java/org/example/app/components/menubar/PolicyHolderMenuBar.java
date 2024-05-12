package org.example.app.components.menubar;

public class PolicyHolderMenuBar extends GenericMenuBar{
    public PolicyHolderMenuBar() {
        super();
    }

    @Override
    void modifyMenuBar() {
        menuBar.getChildren().removeAll(requestMenu, proposalMenu, insuranceCardMenu, claimMenu, customerMenu);
    }

}
