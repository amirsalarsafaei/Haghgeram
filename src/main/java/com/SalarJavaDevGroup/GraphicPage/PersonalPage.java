package com.SalarJavaDevGroup.GraphicPage;

import com.SalarJavaDevGroup.FileHandling.FileHandler;
import com.SalarJavaDevGroup.FileHandling.Load;
import com.SalarJavaDevGroup.FileHandling.Properties;
import com.SalarJavaDevGroup.GraphicAgent;
import com.SalarJavaDevGroup.GraphicComponents.GraphicComponents;
import com.SalarJavaDevGroup.GraphicComponents.GraphicTweet;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class PersonalPage {
    public void main() {
        String targetUser = GraphicAgent.username;
        Scene BackScene = GraphicAgent.scene;

        BorderPane borderPane = new BorderPane();
        StackPane stackPane = new StackPane(borderPane);
        Scene scene = new Scene(stackPane);
        GraphicAgent.scene = scene;
        scene.getStylesheets().add("style.css");
        ImageView backButton = FileHandler.getImage("back");
        HBox backButtonBox = new HBox(backButton);
        backButtonBox.setAlignment(Pos.CENTER_LEFT);
        VBox vBox = new VBox(backButtonBox);
        borderPane.setTop(vBox);
        VBox profileBox = new VBox();
        Circle circle = new Circle(0, 0, Properties.loadSize("big-profile-radius"));
        circle.setFill(Color.BLUEVIOLET);
        ImageView profilePic = Load.LoadUserProfileImage(targetUser);
        circle.setFill(new ImagePattern(profilePic.getImage()));
        DropShadow dropShadow = new DropShadow(Properties.loadSize("small-shadow"), Color.BLACK);
        circle.setEffect(dropShadow);
        profileBox.setAlignment(Pos.TOP_LEFT);
        borderPane.setCenter(profileBox);
        StackPane CircleAndLine = new StackPane();
        HBox CircleAndLineBox = new HBox(CircleAndLine);
        Line line2 = new Line();
        line2.setStartX(0);
        line2.setEndX(Properties.loadSize("line-frame"));
        line2.setOpacity(0.4);
        CircleAndLine.getChildren().add(line2);
        StackPane.setAlignment(line2, Pos.CENTER);
        CircleAndLine.getChildren().add(circle);
        StackPane.setAlignment(circle, Pos.CENTER);
        profileBox.getChildren().add(CircleAndLineBox);
        Label name = new Label(GraphicAgent.serverAgent.profileAgent.getProfileName(GraphicAgent.username, GraphicAgent.password, targetUser));
        Label date = new Label("Online");
        Label bio = new Label(GraphicAgent.serverAgent.profileAgent.getBio(GraphicAgent.username, GraphicAgent.password, targetUser));
        Label birthday = new Label(GraphicAgent.serverAgent.profileAgent.getBirthDate(GraphicAgent.username, GraphicAgent.password, targetUser));
        name.setId("subtitle");
        date.setId("subtitle");
        bio.setId("subtitle-wrap");
        birthday.setId("subtitle-wrap");
        HBox nameBox = new HBox(name);
        HBox dateBox = new HBox(date);
        HBox bioBox = new HBox(7, FileHandler.getImage("bio"), bio);
        HBox birthBox = new HBox(7, FileHandler.getImage("birthday"), birthday);
        dateBox.setPadding(GraphicAgent.explorer.infoInsets());
        nameBox.setPadding(GraphicAgent.explorer.infoInsets());
        bioBox.setPadding(GraphicAgent.explorer.infoInsets());
        birthBox.setPadding(GraphicAgent.explorer.infoInsets());
        profileBox.getChildren().add(nameBox);
        profileBox.getChildren().add(dateBox);
        profileBox.getChildren().add(bioBox);
        profileBox.getChildren().add(birthBox);
        Line line = new Line();
        line.setStartX(0);
        line.setEndX(Properties.loadSize("line-frame"));
        profileBox.getChildren().add(line);
        BorderPane tweetPane = new BorderPane();
        borderPane.setBottom(tweetPane);
        ScrollPane scrollPane = new ScrollPane(GraphicTweet.tweets( GraphicAgent.serverAgent.personalAgent.selfTweets
                (GraphicAgent.username, GraphicAgent.password), stackPane));
        scrollPane.setMaxHeight(Properties.loadSize("personal-page-tweet-height"));
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        tweetPane.setCenter(scrollPane);
        tweetPane.setLeft(Followers());
        tweetPane.setRight(Following());
        GraphicAgent.stage.setScene(scene);
        backButtonBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicAgent.stage.setScene(BackScene);
                GraphicAgent.scene = BackScene;
            }
        });

    }
    public static ScrollPane Followers() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefHeight(Properties.loadSize("personal-page-follow-height"));
        scrollPane.setPrefWidth(Properties.loadSize("personal-page-follow-width"));
        VBox vBox = new VBox();
        HBox title = new HBox(new Label(GraphicAgent.serverAgent.personalAgent.getFollowers
                (GraphicAgent.username, GraphicAgent.password).size() + " " + Properties.loadDialog("followers")));
        title.setPadding(new Insets(Properties.loadSize("big-indent")));
        title.setAlignment(Pos.CENTER);
        vBox.getChildren().add(title);
        Line line = new Line();
        line.setStartX(0);
        line.setEndX(Properties.loadSize("personal-page-follow-width"));

        vBox.getChildren().add(line);
        for (String followers: GraphicAgent.serverAgent.personalAgent.getFollowers(GraphicAgent.username, GraphicAgent.password)) {
            HBox hBox = new HBox(new Label(followers));
            hBox.setAlignment(Pos.CENTER);
            vBox.getChildren().add(hBox);
            hBox.setPadding(new Insets(Properties.loadSize("small-indent")));
            line = new Line();
            line.setStartX(0);
            line.setEndX(Properties.loadSize("personal-page-follow-width"));
            line.setOpacity(0.3);
            vBox.getChildren().add(line);
        }
        scrollPane.setContent(vBox);
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        return scrollPane;
    }


    public static ScrollPane Following() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefHeight(Properties.loadSize("personal-page-follow-height"));
        scrollPane.setPrefWidth(Properties.loadSize("personal-page-follow-width"));
        VBox vBox = new VBox();
        HBox title = new HBox(new Label(GraphicAgent.serverAgent.personalAgent.getFollowing
                (GraphicAgent.username, GraphicAgent.password).size() + " " + Properties.loadDialog("followings")));
        title.setPadding(new Insets(Properties.loadSize("big-indent")));
        title.setAlignment(Pos.CENTER);
        vBox.getChildren().add(title);
        Line line = new Line();
        line.setStartX(0);
        line.setEndX(Properties.loadSize("personal-page-follow-width"));

        vBox.getChildren().add(line);
        for (String followers: GraphicAgent.serverAgent.personalAgent.getFollowing(GraphicAgent.username, GraphicAgent.password)) {
            HBox hBox = new HBox(new Label(followers));
            hBox.setAlignment(Pos.CENTER);
            vBox.getChildren().add(hBox);
            hBox.setPadding(new Insets(Properties.loadSize("small-indent")));
            line = new Line();
            line.setStartX(0);
            line.setEndX(Properties.loadSize("personal-page-follow-width"));
            line.setOpacity(0.3);
            vBox.getChildren().add(line);
        }
        scrollPane.setContent(vBox);
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        return scrollPane;
    }
}
