package com.example.finalassingment.utility;
/**
 * @author Group 11
 */
import javafx.scene.control.TextField;
import java.util.regex.Pattern;

public class InputValidator {
    public InputValidator() {
    }

    public boolean isNull(Object o) {
        return o == null;
    }

    public boolean isNull(Object... objects) {
        for (Object o : objects) {
            if (o == null) return false;
        }
        return true;
    }

    public boolean isEmpty(TextField field) {
        return field.getText() == null || field.getText().trim().isEmpty();
    }

    public boolean isEmpty(TextField... fields) {
        for (TextField f : fields) {
            if (f.getText() == null || f.getText().trim().isEmpty()) return true;
        }
        return false;
    }

    public boolean isDouble(TextField field) {
        try {
            Double.parseDouble(field.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public boolean validateEmail(TextField field) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(field.getText()).matches();
    }

    public boolean validatePhoneNumber(TextField field) {
        String regex = "\\d{10}";

        return field.getText().matches(regex);
    }


    public boolean validateCardNumber(TextField field) {
        String regex = "\\d{10}";

        return field.getText().matches(regex);
    }

    public boolean validateClaimId(TextField field) {
        String regex = "f-\\d{10}";

        return field.getText().matches(regex);
    }


}
