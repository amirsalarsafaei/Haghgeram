package com.SalarJavaDevGroup;

import com.SalarJavaDevGroup.GraphicPage.AuthPage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;

import static com.SalarJavaDevGroup.GraphicAgent.authPage;


public class Main extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setTitle("HaghGeram");
        stage.setHeight(1000);
        stage.setWidth(1600);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.show();
        GraphicAgent.stage = stage;
        authPage.login(stage);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }


}