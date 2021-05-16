package com.SalarJavaDevGroup.GraphicComponents;

import com.SalarJavaDevGroup.FileHandling.Properties;
import com.SalarJavaDevGroup.Models.Comment;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class GraphicComment {
    public static VBox comment(Comment comment, String source, StackPane stackPane) {
        VBox res = new VBox();
        HBox reply = new HBox(new Label(Properties.loadDialog("replying-to") + "@" + source));
        res.getChildren().add(reply);
        res.getChildren().add(GraphicTweet.tweet(comment, stackPane, Properties.loadSize("normal-tweet-pic-size"), false));
        res.setId("white-down-line-grey");
        return res;
    }
    public static ScrollPane Comments(ArrayList<Comment> comments, String source, StackPane stackPane) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setId("white");
        VBox vBox = new VBox();

        for (Comment comment: comments) {
            vBox.getChildren().add(comment(comment, source, stackPane));
        }
        scrollPane.setContent(vBox);
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        return scrollPane;
    }

}
