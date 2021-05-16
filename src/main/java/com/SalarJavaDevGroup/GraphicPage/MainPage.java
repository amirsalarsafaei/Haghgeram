package com.SalarJavaDevGroup.GraphicPage;

import com.SalarJavaDevGroup.FileHandling.FileHandler;
import com.SalarJavaDevGroup.FileHandling.Properties;
import com.SalarJavaDevGroup.GraphicAgent;
import com.SalarJavaDevGroup.GraphicComponents.GraphicComponents;
import com.SalarJavaDevGroup.GraphicComponents.GraphicHeaderFooter;
import com.SalarJavaDevGroup.GraphicComponents.GraphicMenu;
import com.SalarJavaDevGroup.GraphicComponents.GraphicTweet;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainPage {
    public void main(Stage stage) {
        BorderPane pane = new BorderPane();
        StackPane stackPane = new StackPane(pane);
        Scene scene = new Scene(stackPane);
        GraphicAgent.scene = scene;
        scene.getStylesheets().add("style.css");
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.fitToWidthProperty().set(true);
        FileChooser fileChooser = new FileChooser();

        pane.setTop(GraphicHeaderFooter.header());
        pane.setLeft(GraphicMenu.Menu(stage));

        scrollPane.setId("scrollPane");
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(scrollPane);
        TextArea tweet = new TextArea();
        tweet.setWrapText(true);
        tweet.setPrefRowCount(3);
        ImageView tweetButton = FileHandler.getImage("hagh");
        ImageView imageButton = FileHandler.getImage("image");
        VBox tweetButtonBox = new VBox(tweetButton, imageButton);
        HBox tweetBox = new HBox(Properties.loadSize("small-spacing"), tweet, tweetButtonBox);
        tweetBox.setPadding(new Insets(Properties.loadSize("medium-indent")));
        tweetBox.setAlignment(Pos.CENTER);
        tweetBox.setId("white-box");
        borderPane.setBottom(tweetBox);
        final boolean[] hasImage = {false};
        final File[] selectedFile = new File[1];
        tweetButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!hasImage[0])
                    GraphicAgent.serverAgent.tweetAgent.sendTweet(GraphicAgent.username, GraphicAgent.password, tweet.getText());
                else
                    GraphicAgent.serverAgent.tweetAgent.sendTweet(GraphicAgent.username, GraphicAgent.password, tweet.getText(), selectedFile[0]);
                main(stage);
            }
        });
        imageButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectedFile[0] = fileChooser.showOpenDialog(stage);
                hasImage[0] = true;
            }
        });

        pane.setCenter(borderPane);

        pane.setRight(GraphicComponents.RightPanel());
        scrollPane.setContent(GraphicTweet.tweets( GraphicAgent.serverAgent.personalAgent.TimeLine
                (GraphicAgent.username, GraphicAgent.password), stackPane));
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        stage.setScene(scene);
    }
}
