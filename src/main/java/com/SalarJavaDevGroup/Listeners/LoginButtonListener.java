package com.SalarJavaDevGroup.Listeners;

import com.SalarJavaDevGroup.GraphicAgent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.event.ActionListener;

public class LoginButtonListener implements EventHandler {

    TextField username, password;
    Label responseMessage;
    public LoginButtonListener(TextField username, TextField password, Label response) {
        super();
        this.username = username;
        this.password = password;
        this.responseMessage = response;
    }

    @Override
    public void handle(Event event) {
        String response = GraphicAgent.serverAgent.authAgent.login(username.getText(), password.getText());

        if (response.equals("success")) {
            GraphicAgent.username = username.getText();
            GraphicAgent.password = password.getText();
            GraphicAgent.mainPage.main(GraphicAgent.stage);
        }
        else
            responseMessage.setText(response);
    }
}
