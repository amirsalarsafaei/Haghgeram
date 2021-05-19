package com.SalarJavaDevGroup;

import com.SalarJavaDevGroup.FileHandling.Properties;
import com.SalarJavaDevGroup.GraphicPage.AuthPage;
import com.SalarJavaDevGroup.LogicalAgent.NotificationAgent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import static com.SalarJavaDevGroup.GraphicAgent.authPage;


public class Main extends Application {
    private static final Logger logger = LogManager.getLogger(NotificationAgent.class);
    @Override
    public void start(Stage stage) {
        stage.setTitle("HaghGeram");
        stage.setHeight(Properties.loadSize("frame-height"));
        stage.setWidth(Properties.loadSize("frame-width"));
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.show();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                GraphicAgent.serverAgent.setOnline(GraphicAgent.username, GraphicAgent.password);
            }
        }, 0, 1000);
        GraphicAgent.stage = stage;
        authPage.login(stage);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }


}