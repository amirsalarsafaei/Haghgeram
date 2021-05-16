package com.SalarJavaDevGroup.GraphicPage;

import com.SalarJavaDevGroup.FileHandling.FileHandler;
import com.SalarJavaDevGroup.FileHandling.Properties;
import com.SalarJavaDevGroup.GraphicAgent;
import com.SalarJavaDevGroup.GraphicComponents.GraphicComment;
import com.SalarJavaDevGroup.GraphicComponents.GraphicComponents;
import com.SalarJavaDevGroup.GraphicComponents.GraphicMenu;
import com.SalarJavaDevGroup.Models.Comment;
import com.SalarJavaDevGroup.Models.Tweet;
import com.SalarJavaDevGroup.util.Convertor;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class CommentsPage {
    public void main(Stage stage, Scene back, String source, int tweetId, Tweet tweet) {
        ArrayList<Comment> comments = Convertor.ConvertComment(tweet.getComments());
        BorderPane borderPane = new BorderPane();
        StackPane stackPane = new StackPane(borderPane);
        Scene scene = new Scene(stackPane);
        GraphicAgent.scene = scene;
        scene.getStylesheets().add("style.css");
        ImageView backButton = FileHandler.getImage("back");
        HBox backButtonBox = new HBox(backButton);
        backButtonBox.setAlignment(Pos.CENTER_LEFT);
        backButtonBox.setId("down-line-black");
        VBox vBox = new VBox(backButtonBox);
        borderPane.setTop(vBox);
        borderPane.setLeft(GraphicMenu.Menu(stage));
        VBox rightPanel = new VBox();
        rightPanel.setPrefWidth(320);
        borderPane.setRight(rightPanel);
        BorderPane borderPane1 = new BorderPane();
        borderPane.setCenter(borderPane1);

        borderPane1.setCenter(GraphicComment.Comments(comments, source, stackPane));

        TextArea commentSender = new TextArea();
        commentSender.setWrapText(true);
        commentSender.setPrefRowCount(Properties.loadNumbers("comment-text-area-row"));
        ImageView tweetButton = FileHandler.getImage("hagh");
        ImageView imageButton = FileHandler.getImage("image");
        VBox tweetButtonBox = new VBox(tweetButton, imageButton);
        HBox tweetBox = new HBox(Properties.loadSize("small-spacing"), commentSender, tweetButtonBox);
        tweetBox.setPadding(new Insets(Properties.loadSize("medium-indent")));
        tweetBox.setAlignment(Pos.CENTER);
        tweetBox.setId("send-box");
        borderPane1.setBottom(tweetBox);
        FileChooser fileChooser = new FileChooser();
        final boolean[] hasImage = {false};
        final File[] selectedFile = new File[1];
        tweetButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!hasImage[0])
                    GraphicAgent.serverAgent.tweetAgent.sendComment(GraphicAgent.username, GraphicAgent.password,
                            commentSender.getText(), tweetId);
                else
                    GraphicAgent.serverAgent.tweetAgent.sendComment(GraphicAgent.username, GraphicAgent.password,
                            commentSender.getText(), selectedFile[0], tweetId);
                main(stage, back, source, tweetId, tweet);
            }
        });
        imageButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectedFile[0] = fileChooser.showOpenDialog(stage);
                hasImage[0] = true;
            }
        });



        backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.setScene(back);
            }
        });
        stage.setScene(scene);
    }
}
