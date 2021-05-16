package com.SalarJavaDevGroup.Listeners;

import com.SalarJavaDevGroup.FileHandling.Properties;
import com.SalarJavaDevGroup.FileHandling.Save;
import com.SalarJavaDevGroup.GraphicAgent;
import com.SalarJavaDevGroup.util.Validate;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.File;

public class RegisterButtonListener implements EventHandler {
    Label requiredError, passwordError, phoneError, usernameError, emailError;
    TextField passField, passField2, phoneField, emailField, usernameField, nameField, FamilyNameField;
    TextArea bio;
    File[] selectedFile;
    DatePicker birthdateField;
    public RegisterButtonListener(Label requiredError,
                                  Label passwordError,
                                  Label PhoneError,
                                  Label usernameError,
                                  Label emailError,
                                  TextField passField,
                                  TextField passField2,
                                  TextField phoneField,
                                  TextField emailField,
                                  TextField nameField,
                                  TextField usernameField,
                                  DatePicker birthdateField,
                                  TextArea bio,
                                  File[] selectedFile,
                                  TextField FamilyNameField) {
        super();
        this.passwordError = passwordError;
        this.phoneError = PhoneError;
        this.usernameError = usernameError;
        this.passField = passField;
        this.passField2 = passField2;
        this.phoneField = phoneField;
        this.emailField = emailField;
        this.nameField  = nameField;
        this.FamilyNameField = FamilyNameField;
        this.selectedFile = selectedFile;
        this.bio = bio;
        this.usernameField = usernameField;
        this.emailError = emailError;
        this.requiredError = requiredError;
        this.birthdateField = birthdateField;
    }

    @Override
    public void handle(Event event) {
        requiredError.setText("");
        passwordError.setText("");
        phoneError.setText("");
        usernameError.setText("");
        emailError.setText("");
        boolean isok = true;
        if (!passField.getText().equals(passField2.getText())) {
            isok = false;
            passwordError.setText(Properties.loadDialog("mismatch-password"));
        }
        if (phoneField.getText().length() != 0 && !Validate.validPhone(phoneField.getText())) {
            isok = false;
            phoneError.setText(Properties.loadDialog("invalid-phone"));
        }
        if (emailField.getText().length() != 0 && !Validate.validEmail(emailField.getText())) {
            isok = false;
            emailError.setText(Properties.loadDialog("invalid-email"));
        }
        if (nameField.getText().length() == 0 || FamilyNameField.getText().length() == 0 ||
                usernameField.getText().length() == 0 || passField.getText().length() == 0) {
            isok = false;
            requiredError.setText(Properties.loadDialog("star-field-empty"));
        }
        if (!isok)
            return;
        if (phoneField.getText().length() != 0 && GraphicAgent.serverAgent.authAgent.PhoneExists(phoneField.getText())) {
            phoneError.setText(Properties.loadDialog("phone-exists"));
            isok = false;
        }
        if (GraphicAgent.serverAgent.authAgent.UserExists(usernameField.getText())) {
            usernameError.setText(Properties.loadDialog("username-exists"));
            isok = false;
        }
        if (!isok)
            return;
        GraphicAgent.serverAgent.authAgent.register(nameField.getText(),
                FamilyNameField.getText(),
                usernameField.getText(),
                passField.getText(),
                emailField.getText(),
                phoneField.getText(),
                birthdateField.getValue(),
                bio.getText()
        );
        if (selectedFile[0] != null) {
            GraphicAgent.serverAgent.personalAgent.saveProfilePicture(selectedFile[0], usernameField.getText());
        }
        GraphicAgent.authPage.login(GraphicAgent.stage);
    }
}
