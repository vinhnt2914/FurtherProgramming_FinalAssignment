package com.example.finalassingment.app.components.form;

import javafx.event.ActionEvent;
import com.example.finalassingment.app.components.alert.ErrorAlert;
import com.example.finalassingment.app.controllers.RefreshableController;
import com.example.finalassingment.model.provider.InsuranceManager;
import com.example.finalassingment.repository.impl.ProviderRepository;
import com.example.finalassingment.repository.impl.UserRepository;
import com.example.finalassingment.service.ProviderService;
import com.example.finalassingment.utility.PasswordUtil;

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
