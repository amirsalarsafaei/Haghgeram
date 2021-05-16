package com.SalarJavaDevGroup.GraphicPage;

import com.SalarJavaDevGroup.FileHandling.FileHandler;
import com.SalarJavaDevGroup.FileHandling.Load;
import com.SalarJavaDevGroup.FileHandling.Properties;
import com.SalarJavaDevGroup.GraphicAgent;
import com.SalarJavaDevGroup.GraphicComponents.GraphicComponents;
import com.SalarJavaDevGroup.GraphicComponents.GraphicHeaderFooter;
import com.SalarJavaDevGroup.GraphicComponents.GraphicMenu;
import com.SalarJavaDevGroup.GraphicComponents.GraphicTweet;
import com.SalarJavaDevGroup.Listeners.BlockButtonListener;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Explorer {
    StackPane stackPane;
    public void main() {
        Stage stage = GraphicAgent.stage;
        stackPane = new StackPane();
        Scene scene = new Scene(stackPane);
        scene.getStylesheets().add("style.css");
        BorderPane borderPane = new BorderPane();
        stackPane.getChildren().add(borderPane);
        borderPane.setLeft(GraphicMenu.Menu(stage));
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setContent(GraphicTweet.tweets(GraphicAgent.serverAgent.personalAgent.Discovery(GraphicAgent.username, GraphicAgent.password), stackPane));
        borderPane.setCenter(scrollPane);
        borderPane.setRight(GraphicComponents.RightPanel());
        borderPane.setBottom(GraphicHeaderFooter.footer());
        TextField searchBar = new TextField();
        ImageView searchButton = FileHandler.getImage("search-icon");
        HBox searchBox = new HBox(Properties.loadSize("small-spacing"), searchBar, searchButton);
        Line line = new Line();
        line.setStartX(0);
        line.setEndX(Properties.loadSize("line-frame"));
        line.setOpacity(0.5);
        searchBox.setPadding(new Insets(Properties.loadSize("big-indent")));
        VBox tmp = new VBox(searchBox, line);
        searchBox.setAlignment(Pos.CENTER);
        tmp.setAlignment(Pos.CENTER);
        searchBar.setId("search-bar");
        borderPane.setTop(tmp);
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        searchButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Profile(searchBar.getText());
            }
        });
        stage.setScene(scene);
        GraphicAgent.scene = scene;
    }



    public void Profile(String targetUser) {
        if (targetUser.equals(GraphicAgent.username)) {
            GraphicAgent.personalPage.main();
            return;
        }
        Scene BackScene = GraphicAgent.scene;

        BorderPane borderPane = new BorderPane();
        StackPane stackPane = new StackPane(borderPane);
        Scene scene = new Scene(stackPane);
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
        Label date = new Label(GraphicAgent.serverAgent.profileAgent.getStatus(GraphicAgent.username, GraphicAgent.password, targetUser));
        Label bio = new Label(GraphicAgent.serverAgent.profileAgent.getBio(GraphicAgent.username, GraphicAgent.password, targetUser));
        Label birthday = new Label(GraphicAgent.serverAgent.profileAgent.getBirthDate(GraphicAgent.username, GraphicAgent.password, targetUser));
        name.setId("subtitle");
        date.setId("subtitle");
        bio.setId("subtitle-wrap");
        birthday.setId("subtitle-wrap");
        HBox nameBox = new HBox(name);
        HBox dateBox = new HBox(date);
        HBox bioBox = new HBox(Properties.loadSize("small-spacing"), FileHandler.getImage("bio"), bio);
        HBox birthBox = new HBox(Properties.loadSize("small-spacing"), FileHandler.getImage("birthday"), birthday);
        dateBox.setPadding(infoInsets());
        nameBox.setPadding(infoInsets());
        bioBox.setPadding(infoInsets());
        birthBox.setPadding(infoInsets());
        profileBox.getChildren().add(nameBox);
        profileBox.getChildren().add(dateBox);
        profileBox.getChildren().add(bioBox);
        profileBox.getChildren().add(birthBox);
        ImageView followButton = new ImageView();
        if (!GraphicAgent.serverAgent.profileAgent.Blocked(GraphicAgent.username, GraphicAgent.password, targetUser)) {
            if (GraphicAgent.serverAgent.profileAgent.Follows(GraphicAgent.username, GraphicAgent.password, targetUser))
                followButton = FileHandler.getImage("unfollow");
            else if (GraphicAgent.serverAgent.profileAgent.Pending(GraphicAgent.username, GraphicAgent.password, targetUser))
                followButton = FileHandler.getImage("pending");
            else
                followButton = FileHandler.getImage("follow");
        }
        followButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicAgent.serverAgent.profileAgent.toggleFollow(GraphicAgent.username, GraphicAgent.password, targetUser);
                Profile(targetUser);
            }
        });
        HBox tools = new HBox(Properties.loadSize("medium-spacing"));
        tools.setPadding(new Insets(Properties.loadSize("big-indent")));
        tools.setAlignment(Pos.CENTER);
        ImageView mute = muteButton(targetUser);
        ImageView block = blockButton(targetUser);
        ImageView message = new ImageView();
        if (GraphicAgent.serverAgent.profileAgent.Follows(GraphicAgent.username, GraphicAgent.password, targetUser))
            message = FileHandler.getImage("forward");

        block.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicAgent.serverAgent.profileAgent.toggleBlock(GraphicAgent.username, GraphicAgent.password, targetUser);
                Profile(targetUser);
            }
        });
        message.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                TextArea messageArea = new TextArea();
                messageArea.setPrefRowCount(Properties.loadNumbers("message-area-row"));
                messageArea.setPrefColumnCount(Properties.loadNumbers("message-area-column"));
                ImageView sendMessage = FileHandler.getImage("forward");
                HBox messageBox = new HBox(Properties.loadSize("small-spacing"),messageArea, sendMessage);
                messageBox.setPadding(new Insets(Properties.loadSize("message-box-insets")));
                messageArea.setStyle("-fx-wrap-text: true;");
                messageBox.setAlignment(Pos.CENTER);
                Pane BlackPane = new Pane();
                BorderPane pane = new BorderPane();
                pane.setCenter(messageBox);
                BlackPane.setId("black-fade");
                stackPane.getChildren().addAll(BlackPane, pane);
                StackPane.setAlignment(pane, Pos.CENTER);
                sendMessage.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        GraphicAgent.serverAgent.profileAgent.sendMessageFromProfile(GraphicAgent.username, GraphicAgent.password, targetUser, messageArea.getText());
                        BlackPane.setVisible(false);
                        pane.setVisible(false);
                    }
                });
            }
        });
        tools.getChildren().addAll(followButton ,mute, block, message);
        profileBox.getChildren().add(tools);
        Line line = new Line();
        line.setStartX(0);
        line.setEndX(Properties.loadSize("line-frame"));
        profileBox.getChildren().add(line);
        GraphicAgent.stage.setScene(scene);
        backButtonBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicAgent.stage.setScene(BackScene);
                GraphicAgent.scene = BackScene;
            }
        });

    }


    public ImageView muteButton(String target) {
        ImageView mute;
        if (GraphicAgent.serverAgent.profileAgent.Muted(GraphicAgent.username, GraphicAgent.password, target))
            mute = FileHandler.getImage("unmute");
        else
            mute = FileHandler.getImage("mute");
        mute.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicAgent.serverAgent.profileAgent.toggleMute(GraphicAgent.username, GraphicAgent.password, target);
                if (GraphicAgent.serverAgent.profileAgent.Muted(GraphicAgent.username, GraphicAgent.password, target))
                    mute.setImage(FileHandler.getImage("unmute").getImage());
                else
                    mute.setImage(FileHandler.getImage("mute").getImage());
            }
        });
        return mute;
    }

    public ImageView blockButton(String target) {
        ImageView block;
        if (GraphicAgent.serverAgent.profileAgent.Blocked(GraphicAgent.username, GraphicAgent.password, target))
            block = FileHandler.getImage("unblock");
        else
            block = FileHandler.getImage("block");
        block.setOnMouseClicked(new BlockButtonListener(target));
        return block;
    }

    public Insets infoInsets() {
        return new Insets(Properties.loadSize("tiny-indent"), Properties.loadSize("big-indent")
                , Properties.loadSize("tiny-indent"), Properties.loadSize("big-indent"));
    }
}

