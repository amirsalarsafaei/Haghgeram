package com.SalarJavaDevGroup.GraphicPage;

import com.SalarJavaDevGroup.FileHandling.FileHandler;
import com.SalarJavaDevGroup.FileHandling.Properties;
import com.SalarJavaDevGroup.FileHandling.Save;
import com.SalarJavaDevGroup.GraphicAgent;
import com.SalarJavaDevGroup.Listeners.LoginButtonListener;
import com.SalarJavaDevGroup.Listeners.RegisterButtonListener;
import com.SalarJavaDevGroup.util.Validate;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class AuthPage {

    public void login(Stage stage) {
        VBox vBox = new VBox(Properties.loadSize("big-spacing"));
        vBox.setId("loginPanel");
        Scene scene = new Scene(vBox);
        scene.getStylesheets().add("style.css");
        ImageView imageView = FileHandler.getImage("MediumLogo");
        Hyperlink hyperlink = new Hyperlink(Properties.loadDialog("register"));
        vBox.setAlignment(Pos.CENTER);
        TextField userField = new TextField();
        PasswordField passField = new PasswordField();
        Label responseMessage = new Label();
        responseMessage.setId("error");
        Button SubmitButton = new Button(Properties.loadDialog("login-button"));
        HBox userBox = new HBox(15, new Label(Properties.loadDialog("username")), userField);
        HBox passBox = new HBox(15, new Label(Properties.loadDialog("password")), passField);
        HBox submitBox = new HBox(SubmitButton);
        HBox responseBox = new HBox(responseMessage);
        userBox.setAlignment(Pos.CENTER);
        passBox.setAlignment(Pos.CENTER);
        submitBox.setAlignment(Pos.CENTER);
        responseBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(imageView);
        vBox.getChildren().add(userBox);
        vBox.getChildren().add(passBox);
        vBox.getChildren().add(submitBox);
        vBox.getChildren().add(responseBox);
        vBox.getChildren().add(hyperlink);
        stage.setScene(scene);
        SubmitButton.setOnAction(new LoginButtonListener(userField, passField, responseMessage));
        hyperlink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                register(stage);
            }
        });
    }

    public void register(Stage stage) {
        VBox vBox = new VBox(Properties.loadSize("small-spacing"));
        vBox.setAlignment(Pos.BASELINE_CENTER);
        Scene scene = new Scene(vBox);
        scene.getStylesheets().add("style.css");
        vBox.setPadding(new Insets(Properties.loadSize("huge-indent"), 0, 0, Properties.loadSize("small-indent")));
        vBox.setId("registerPanel");
        Label name = new Label(Properties.loadDialog("name")+"*");
        Label FamilyName = new Label(Properties.loadDialog("surname")+"*");
        Label username = new Label(Properties.loadDialog("username")+"*");
        Label password = new Label(Properties.loadDialog("password") + "*");
        Label password2 = new Label(Properties.loadDialog("repeat-password") + "*");
        Label Email = new Label(Properties.loadDialog("Email") );
        Label Phone = new Label(Properties.loadDialog("phone"));
        Label Birthdate = new Label(Properties.loadDialog("birthday"));
        Label Bio = new Label(Properties.loadDialog("bio"));
        Label usernameError = new Label();
        usernameError.setId("error");
        Label passwordError = new Label();
        passwordError.setId("error");
        Label emailError = new Label();
        emailError.setId("error");
        Label phoneError = new Label();
        phoneError.setId("error");
        TextField nameField = new TextField();
        TextField FamilyNameField = new TextField();
        TextField usernameField = new TextField();
        TextField passField = new TextField();
        TextField passField2 = new TextField();
        TextField emailField = new TextField();
        TextField phoneField = new TextField();
        Label requiredError = new Label();
        DatePicker birthdateField = new DatePicker();
        requiredError.setId("error");
        TextArea bio = new TextArea();
        bio.setPrefColumnCount(Properties.loadNumbers("register-bio-column-count"));
        bio.setPrefRowCount(Properties.loadNumbers("register-bio-row-count"));
        Button submitButton = new Button(Properties.loadDialog("register-button"));
        vBox.getChildren().add(new HBox(Properties.loadSize("small-spacing"), name, nameField));
        vBox.getChildren().add(new HBox(Properties.loadSize("small-spacing"), FamilyName, FamilyNameField));
        vBox.getChildren().add(new HBox(Properties.loadSize("small-spacing"), username, usernameField
                , usernameError));
        vBox.getChildren().add(new HBox(Properties.loadSize("small-spacing"), password, passField
                , passwordError));
        vBox.getChildren().add(new HBox(Properties.loadSize("small-spacing"), password2, passField2));
        vBox.getChildren().add(new HBox(Properties.loadSize("small-spacing"), Email, emailField, emailError));
        vBox.getChildren().add(new HBox(Properties.loadSize("small-spacing"), Phone, phoneField, phoneError));
        vBox.getChildren().add(new HBox(Properties.loadSize("small-spacing"), Birthdate, birthdateField));
        vBox.getChildren().add(new HBox(Properties.loadSize("small-spacing"), Bio, bio));
        Button uploadPhoto = new Button(Properties.loadDialog("upload-photo-button"));
        vBox.getChildren().add(uploadPhoto);
        FileChooser fileChooser = new FileChooser();
        final File[] selectedFile = {null};

        uploadPhoto.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                selectedFile[0] = fileChooser.showOpenDialog(stage);
            }
        });
        vBox.getChildren().add(requiredError);
        vBox.getChildren().add(submitButton);
        stage.setScene(scene);
        submitButton.setOnAction(new RegisterButtonListener(requiredError, passwordError,
                phoneError, usernameError, emailError, passField, passField2, phoneField, emailField, nameField,
                usernameField, birthdateField, bio, selectedFile, FamilyNameField));
    }
}
