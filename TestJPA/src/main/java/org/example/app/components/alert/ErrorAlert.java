package org.example.app.components.alert;

public class ErrorAlert extends CustomAlert{
    public ErrorAlert(String message) {
        super(message);
    }

    @Override
    void modifyAlert() {
        getStylesheets().add(getClass().getResource("/static/failAlert.css").toExternalForm());
    }
}
