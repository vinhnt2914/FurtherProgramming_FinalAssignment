package org.example.app.components.form;
/**
 * @author Group 11
 */

import javafx.event.ActionEvent;
import org.example.app.components.alert.ErrorAlert;
import org.example.app.controllers.RefreshableController;
import org.example.model.provider.InsuranceManager;
import org.example.repository.impl.ProviderRepository;
import org.example.repository.impl.UserRepository;
import org.example.service.ProviderService;
import org.example.utility.PasswordUtil;

public class AddManagerForm extends GenericAddForm {
    public AddManagerForm(RefreshableController controller) {
        super(controller);
    }

    @Override
    void modifyForm() {
        titleLabel.setText("Add Surveyor Form");
    }

    @Override
    void add(ActionEvent actionEvent) {
        if (validateInput()) {
            ProviderRepository repository = new ProviderRepository();
            ProviderService service = new ProviderService();
            UserRepository userRepository = new UserRepository();

            try {
                String username = usernameField.getText();
                String password = passwordField.getText();
                String hashedPassword = PasswordUtil.encrypt(password);

                if (userRepository.findUser(username, hashedPassword) != null) {
                    new ErrorAlert("This username is already taken!");
                    return;
                }

                InsuranceManager manager = service.makeManager()
                        .fullName(nameField.getText())
                        .username(username)
                        .address(addressField.getText())
                        .email(emailField.getText())
                        .phone(phoneField.getText())
                        .password(password)
                        .build();

                repository.add(manager);
                repository.close();
                close();
                controller.refresh(); // Refresh the table after adding
            } catch (NumberFormatException e) {
                new ErrorAlert("Please enter valid input values.");
            } finally {
                userRepository.close();
            }
        }
    }
}
