package com.SalarJavaDevGroup.GraphicPage;

import com.SalarJavaDevGroup.FileHandling.Properties;
import com.SalarJavaDevGroup.GraphicAgent;
import com.SalarJavaDevGroup.GraphicComponents.GraphicComponents;
import com.SalarJavaDevGroup.GraphicComponents.GraphicHeaderFooter;
import com.SalarJavaDevGroup.util.Validate;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;


public class EditProfilePage {
    public void main() {
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(GraphicHeaderFooter.headerToSetting());
        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add("style.css");
        GraphicAgent.scene = scene;
        TextField name = new TextField(GraphicAgent.serverAgent.personalAgent.getName(GraphicAgent.username, GraphicAgent.password));
        TextField familyName = new TextField(GraphicAgent.serverAgent.personalAgent.getFamilyName
                (GraphicAgent.username, GraphicAgent.password));
        TextField email = new TextField(GraphicAgent.serverAgent.personalAgent.getEmail
                (GraphicAgent.username, GraphicAgent.password));
        TextField phone = new TextField(GraphicAgent.serverAgent.personalAgent.getPhone
                (GraphicAgent.username, GraphicAgent.password));
        PasswordField passwordField = new PasswordField();
        TextArea bio = new TextArea(GraphicAgent.serverAgent.personalAgent.getBio
                (GraphicAgent.username, GraphicAgent.password));
        DatePicker birthDate = new DatePicker(GraphicAgent.serverAgent.
                personalAgent.getBirthDate(GraphicAgent.username, GraphicAgent.password));
        Button submit = new Button(Properties.loadDialog("submit"));
        Label nameError = new Label();
        nameError.setId("error");
        Label FamilyNameError = new Label();
        FamilyNameError.setId("error");
        Label emailError = new Label();
        emailError.setId("error");
        Label phoneError = new Label();
        phoneError.setId("error");
        Label passwordError = new Label();
        phoneError.setId("error");
        HBox nameBox = new HBox(Properties.loadSize("small-spacing"), new Label(
                Properties.loadDialog("name")), name, nameError);
        HBox familyNameBox = new HBox(Properties.loadSize("small-spacing"), new Label(
                Properties.loadDialog("surname")), familyName, FamilyNameError);
        HBox emailBox = new HBox(Properties.loadSize("small-spacing"), new Label(
                Properties.loadDialog("Email")), email, emailError);
        HBox phoneBox = new HBox(Properties.loadSize("small-spacing"), new Label(
                Properties.loadDialog("phone")), phone, phoneError);
        HBox passwordBox = new HBox(Properties.loadSize("small-spacing"), new Label(
                Properties.loadDialog("password")), passwordField, passwordError);
        HBox bioBox = new HBox(Properties.loadSize("small-spacing"), new Label(
                Properties.loadDialog("bio")), bio);
        HBox birthdateBox = new HBox(Properties.loadSize("small-spacing"), new Label(
                Properties.loadDialog("birthday")), birthDate);
        HBox submitBox = new HBox(submit);
        bio.setPrefRowCount(3);
        submitBox.setAlignment(Pos.CENTER);
        passwordBox.setAlignment(Pos.CENTER);
        nameBox.setAlignment(Pos.CENTER);
        familyNameBox.setAlignment(Pos.CENTER);
        emailBox.setAlignment(Pos.CENTER);
        phoneBox.setAlignment(Pos.CENTER);
        bioBox.setAlignment(Pos.CENTER);
        birthdateBox.setAlignment(Pos.CENTER);
        Button uploadPhoto = new Button(Properties.loadDialog("upload-photo-button"));
        HBox uploadPhotoBox = new HBox(uploadPhoto);
        uploadPhotoBox.setAlignment(Pos.CENTER);
        VBox editBox = new VBox(Properties.loadSize("medium-spacing"),nameBox, familyNameBox, emailBox, phoneBox, passwordBox,bioBox, birthdateBox,
                uploadPhotoBox, submitBox);
        editBox.setPadding(new Insets(Properties.loadSize("big-indent")));
        borderPane.setCenter(editBox);
        FileChooser fileChooser = new FileChooser();
        final File[] selectedFile = {null};

        uploadPhoto.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                selectedFile[0] = fileChooser.showOpenDialog(GraphicAgent.stage);
            }
        });
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                boolean isok = true;
                if (phone.getText().length() != 0 && !Validate.validPhone(phone.getText())) {
                    isok = false;
                    phoneError.setText(Properties.loadDialog("invalid-phone"));
                }
                if (email.getText().length() != 0 && !Validate.validEmail(email.getText())) {
                    isok = false;
                    emailError.setText(Properties.loadDialog("invalid-email"));
                }
                if (name.getText().length() == 0) {
                    isok = false;
                    nameError.setText(Properties.loadDialog("empty-field"));
                }
                if (familyName.getText().length() == 0) {
                    isok = false;
                    FamilyNameError.setText(Properties.loadDialog("empty-field"));
                }
                if (phone.getText().length() != 0 && GraphicAgent.serverAgent.authAgent.PhoneExists(phone.getText())) {
                    phoneError.setText(Properties.loadDialog("phone-exists"));
                    isok = false;
                }
                if (!isok)
                    return;
                GraphicAgent.serverAgent.personalAgent.updateName(GraphicAgent.username, GraphicAgent.password,name.getText());
                GraphicAgent.serverAgent.personalAgent.updateFamilyName(GraphicAgent.username, GraphicAgent.password,familyName.getText());
                GraphicAgent.serverAgent.personalAgent.updateEmail(GraphicAgent.username, GraphicAgent.password,email.getText());
                GraphicAgent.serverAgent.personalAgent.updatePhone(GraphicAgent.username, GraphicAgent.password,phone.getText());
                GraphicAgent.serverAgent.personalAgent.updateBio(GraphicAgent.username, GraphicAgent.password,bio.getText());
                GraphicAgent.serverAgent.personalAgent.updateBirthDate(GraphicAgent.username, GraphicAgent.password,birthDate.getValue());
                GraphicAgent.serverAgent.personalAgent.updateProfile(GraphicAgent.username, GraphicAgent.password, selectedFile[0]);
                if (passwordField.getText().length() != 0) {
                    GraphicAgent.serverAgent.personalAgent.updatePassword(GraphicAgent.username, GraphicAgent.password, passwordField.getText());
                    GraphicAgent.password = passwordField.getText();
                }
                GraphicAgent.settingPage.main();
            }
        });
        GraphicAgent.stage.setScene(scene);
    }
}
