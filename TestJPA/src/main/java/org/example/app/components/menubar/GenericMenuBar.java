package org.example.app.components.menubar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.app.App;

import java.io.IOException;

public abstract class GenericMenuBar extends HBox {
    @FXML
    protected HBox menuBar;
    @FXML
    protected Hyperlink infoMenu;
    @FXML
    protected Hyperlink dashboardMenu;
    @FXML
    protected Hyperlink claimMenu;
    @FXML
    protected Hyperlink insuranceCardMenu;
    @FXML
    protected Hyperlink customerMenu;
    @FXML
    protected Hyperlink requestMenu;
    @FXML
    protected Hyperlink proposalMenu;
    @FXML
    protected Hyperlink logoutMenu;

    public GenericMenuBar() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/menuBar.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setUpMenuBar();
        modifyMenuBar();
    }

    abstract void modifyMenuBar();

    private void setUpMenuBar() {
        infoMenu.setOnAction(this::openInfo);
        dashboardMenu.setOnAction(this::openDashboard);
        claimMenu.setOnAction(this::openClaim);
        insuranceCardMenu.setOnAction(this::openInsuranceCard);
        customerMenu.setOnAction(this::openCustomer);
        requestMenu.setOnAction(this::openRequest);
        proposalMenu.setOnAction(this::openProposal);
        logoutMenu.setOnAction(this::handleLogout);
        System.out.println("Menubar set up complete");
    }

    private void openInfo(ActionEvent actionEvent) {
        App.getInstance().switchScene("info");
    }

    private void openProposal(ActionEvent actionEvent) {
        App.getInstance().switchScene("proposal");
    }

    private void openRequest(ActionEvent actionEvent) {
        App.getInstance().switchScene("request");
    }

    private void openCustomer(ActionEvent actionEvent) {
        App.getInstance().switchScene("customer");
        System.out.println("Switching scenes");
    }

    private void openInsuranceCard(ActionEvent actionEvent) {
        App.getInstance().switchScene("insuranceCard");
    }

    private void openClaim(ActionEvent actionEvent) {
        App.getInstance().switchScene("claim");
    }

    private void openDashboard(ActionEvent actionEvent) {
        App.getInstance().switchScene("dashboard");
    }

    public HBox getMenuBar() {
        return menuBar;
    }
    private void handleLogout(ActionEvent actionEvent) {

        System.out.println("Logout button clicked!");


        showLoginScreen();
    }
    private void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) logoutMenu.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
