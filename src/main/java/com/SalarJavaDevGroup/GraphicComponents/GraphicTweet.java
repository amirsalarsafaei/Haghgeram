package com.SalarJavaDevGroup.GraphicComponents;

import com.SalarJavaDevGroup.FileHandling.FileHandler;
import com.SalarJavaDevGroup.FileHandling.Load;
import com.SalarJavaDevGroup.FileHandling.Properties;
import com.SalarJavaDevGroup.GraphicAgent;
import com.SalarJavaDevGroup.Models.Conversation;
import com.SalarJavaDevGroup.Models.Group;
import com.SalarJavaDevGroup.Models.Tweet;
import com.SalarJavaDevGroup.util.Filter;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class GraphicTweet {
    public static void setForward(StackPane Pane, Tweet tweet) {
        ArrayList<String> toUsers = new ArrayList<>();
        BorderPane WhitePane = new BorderPane();
        Pane.getChildren().add(WhitePane);
        WhitePane.setId("white-fade");
        VBox vBox = new VBox();
        vBox.setMaxWidth(Properties.loadSize("forward-box-max-width"));
        vBox.setMaxHeight(Properties.loadSize("forward-box-max-height"));
        vBox.setAlignment(Pos.CENTER);
        BorderPane borderPane = new BorderPane();
        vBox.getChildren().add(borderPane);
        vBox.setId("white-box");
        DropShadow dropShadow = new DropShadow(Properties.loadSize("small-shadow"), Color.BLACK);
        vBox.setEffect(dropShadow);
        Pane.getChildren().add(vBox);
        StackPane.setAlignment(vBox, Pos.CENTER);
        ScrollPane scrollPane = new ScrollPane();
        borderPane.setCenter(scrollPane);
        VBox userList = new VBox();
        scrollPane.setContent(userList);
        ImageView addUsers = FileHandler.getImage("forward");
        HBox addUsersBox = new HBox(addUsers);
        addUsersBox.setAlignment(Pos.CENTER);
        addUsersBox.setPadding(new Insets(Properties.loadSize("small-indent")));
        borderPane.setTop(addUsersBox);
        borderPane.setPrefSize(Properties.loadSize("forward-inbox-pref-width"),
                Properties.loadSize("forward-inbox-pref-height"));
        Label type = new Label(Properties.loadDialog("followings"));
        HBox typeBox = new HBox(type);
        typeBox.setId("down-line-black");
        typeBox.setAlignment(Pos.CENTER);
        userList.getChildren().add(typeBox);
        for (String following : GraphicAgent.serverAgent.personalAgent.getFollowing(GraphicAgent.username, GraphicAgent.password)) {
            StackPane stackPane = new StackPane();
            HBox hBox = new HBox(stackPane);
            stackPane.setPrefWidth(Properties.loadSize("forward-following-width"));
            userList.getChildren().add(hBox);
            hBox.setPadding(new Insets(Properties.loadSize("small-indent")));
            Label name = new Label(following);
            stackPane.getChildren().add(name);
            StackPane.setAlignment(name, Pos.CENTER_LEFT);
            final boolean[] inList = {false};
            ImageView check = FileHandler.getImage("check-" + inList[0]);
            stackPane.getChildren().add(check);
            StackPane.setAlignment(check, Pos.CENTER_RIGHT);
            hBox.setId("down-line-grey");

            check.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    inList[0] = !inList[0];
                    check.setImage(FileHandler.getImage("check-" + inList[0]).getImage());

                    if (inList[0])
                        toUsers.add(following);
                    else
                        Filter.delFind(toUsers, following);
                }
            });
        }

        type = new Label(Properties.loadDialog("groups"));

        typeBox = new HBox(type);
        typeBox.setId("down-line-black");
        typeBox.setAlignment(Pos.CENTER);
        userList.getChildren().add(typeBox);
        ArrayList<Group> toGroups = new ArrayList<>();
        for (Group group : GraphicAgent.serverAgent.groupAgent.getGroups(GraphicAgent.username,  GraphicAgent.password)) {
            StackPane stackPane = new StackPane();
            HBox hBox = new HBox(stackPane);
            stackPane.setPrefWidth(Properties.loadSize("forward-following-width"));
            userList.getChildren().add(hBox);
            hBox.setPadding(new Insets(Properties.loadSize("small-indent")));
            Label name = new Label(group.getName());
            stackPane.getChildren().add(name);
            StackPane.setAlignment(name, Pos.CENTER_LEFT);
            final boolean[] inList = {false};
            ImageView check = FileHandler.getImage("check-" + inList[0]);
            stackPane.getChildren().add(check);
            StackPane.setAlignment(check, Pos.CENTER_RIGHT);
            hBox.setId("down-line-grey");

            check.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    inList[0] = !inList[0];
                    check.setImage(FileHandler.getImage("check-" + inList[0]).getImage());

                    if (inList[0])
                        toGroups.add(group);
                    else
                        toGroups.remove(group);
                }
            });
        }

        type = new Label(Properties.loadDialog("conv-and-groups"));

        typeBox = new HBox(type);
        typeBox.setId("down-line-black");
        typeBox.setAlignment(Pos.CENTER);
        userList.getChildren().add(typeBox);
        ArrayList<Conversation> toConv = new ArrayList<>();
        for (Conversation conv : GraphicAgent.serverAgent.messengerAgent.getConversation(GraphicAgent.username,  GraphicAgent.password)) {
            if (!conv.isGroup() && !conv.isSavedMessage() && GraphicAgent.serverAgent.profileAgent.Follows(GraphicAgent.username,
                    GraphicAgent.password, conv.getName(GraphicAgent.username)))
                continue;


            StackPane stackPane = new StackPane();
            HBox hBox = new HBox(stackPane);
            stackPane.setPrefWidth(Properties.loadSize("forward-following-width"));
            userList.getChildren().add(hBox);
            hBox.setPadding(new Insets(Properties.loadSize("small-indent")));
            Label name = new Label(conv.getName(GraphicAgent.username));
            stackPane.getChildren().add(name);
            StackPane.setAlignment(name, Pos.CENTER_LEFT);
            final boolean[] inList = {false};
            ImageView check = FileHandler.getImage("check-" + inList[0]);
            stackPane.getChildren().add(check);
            StackPane.setAlignment(check, Pos.CENTER_RIGHT);
            hBox.setId("down-line-grey");

            check.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    inList[0] = !inList[0];
                    check.setImage(FileHandler.getImage("check-" + inList[0]).getImage());

                    if (inList[0])
                        toConv.add(conv);
                    else
                        toConv.remove(conv);
                }
            });
        }
        addUsers.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println(toUsers.size());
                WhitePane.setVisible(false);
                vBox.setVisible(false);
                for (Group group : toGroups) {
                    for (String user : group.getUsers()) {
                        if (!Filter.boolFind(toUsers, user))
                            toUsers.add(user);
                    }
                }
                for (String user: toUsers)
                    GraphicAgent.serverAgent.messengerAgent.sendMessage(GraphicAgent.username, GraphicAgent.password, user, tweet);
                for (Conversation conv : toConv) {
                    GraphicAgent.serverAgent.messengerAgent.sendMessage(GraphicAgent.username, GraphicAgent.password, tweet,conv.getId());
                }
            }
        });
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPadding(new Insets(0));
        userList.setPrefWidth(Properties.loadSize("forward-user-list-width"));
    }

    public static VBox tweet(Tweet tweet, StackPane stackPane, int widthSize, boolean isReTweeted) {
        VBox vBox = new VBox(Properties.loadSize("big-spacing"));
        vBox.setPrefWidth(Properties.loadSize("tweet-width"));
        Label user = new Label("@" + tweet.getUser());
        user.setAlignment(Pos.BASELINE_LEFT);
        Circle circle = new Circle(0, 0, Properties.loadSize("small-profile-radius"));
        ImageView profilePic = Load.LoadUserProfileImage(tweet.getUser());
        circle.setFill(new ImagePattern(profilePic.getImage()));
        DropShadow dropShadow = new DropShadow(Properties.loadSize("tiny-shadow"), Color.BLACK);
        circle.setEffect(dropShadow);
        circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicAgent.explorer.Profile(tweet.getUser());
            }
        });
        HBox userBox = new HBox(Properties.loadSize("small-spacing"),circle , user);
        userBox.setAlignment(Pos.CENTER_LEFT);
        vBox.getChildren().add(userBox);
        vBox.setId("white");
        Label content = new Label(tweet.Content);
        content.setPadding(new Insets(Properties.loadSize("small-indent")));
        content.setStyle("-fx-wrap-text: true");
        vBox.getChildren().add(content);
        if (tweet.hasImage) {
            Image image = FileHandler.loadImage(tweet.getId(), "tweetsImage").getImage();
            ImagePattern pattern = new ImagePattern(
                    image, 0, 0, widthSize, image.getHeight() * widthSize / image.getWidth() , false
            );
            Rectangle rectangle = new Rectangle(0, 0, widthSize, image.getHeight() * widthSize / image.getWidth());
            rectangle.setArcWidth(Properties.loadSize("tweet-image-radius"));
            rectangle.setArcHeight(Properties.loadSize("tweet-image-radius"));
            rectangle.setFill(pattern);
            rectangle.setEffect(new DropShadow(Properties.loadSize("medium-shadow"), Color.BLACK));  // Shadow
            HBox hp = new HBox(rectangle);
            hp.setAlignment(Pos.CENTER);
            hp.setPadding(new Insets(Properties.loadSize("big-indent")));
            vBox.getChildren().add(hp);
        }
        vBox.setPadding(new Insets(Properties.loadSize("big-indent")));
        if (isReTweeted) {
            vBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    BorderPane whitePane = new BorderPane();
                    whitePane.setId("white-fade");
                    stackPane.getChildren().add(whitePane);
                    VBox tmp = tweet(tweet, stackPane, widthSize, false);
                    tmp.setPrefWidth(Properties.loadSize("retweet-width"));
                    ScrollPane scrollPane = new ScrollPane(tmp);
                    VBox position = new VBox(scrollPane);
                    position.setAlignment(Pos.CENTER);
                    position.setPrefWidth(Properties.loadSize("retweet-width"));
                    HBox hPosition = new HBox(position);
                    hPosition.setAlignment(Pos.CENTER);
                    ImageView x_icon = FileHandler.getImage("x_icon");
                    stackPane.getChildren().add(hPosition);
                    stackPane.getChildren().add(x_icon);
                    StackPane.setAlignment(hPosition, Pos.CENTER_RIGHT);
                    StackPane.setAlignment(x_icon, Pos.TOP_RIGHT);
                    x_icon.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            whitePane.setVisible(false);
                            hPosition.setVisible(false);
                            x_icon.setVisible(false);
                        }
                    });
                    scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
                    scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
                }
            });
            return vBox;


        }
        if (tweet.Retweeted != -1) {
            System.out.println(tweet.getId());
            VBox tmp = tweet(GraphicAgent.serverAgent.personalAgent.LoadTweet(GraphicAgent.username, GraphicAgent.password,
                    tweet.Retweeted), stackPane, (int)(widthSize/1.1), true);
            tmp.setId("grey-round-border");
            vBox.getChildren().add(tmp);
        }
        ImageView like = FileHandler.getImage("like");
        final boolean[] isLiked = {Filter.boolFind(tweet.Likes, GraphicAgent.username)};
        ImageView Comment = FileHandler.getImage("comment");
        ImageView forward = FileHandler.getImage("forward");
        ImageView retweet = FileHandler.getImage("retweet");
        ImageView report = FileHandler.getImage("report");
        HBox toolBar;
        if (!GraphicAgent.username.equals(tweet.getUser()))
            toolBar = new HBox(Properties.loadSize("big-spacing"), like, Comment, retweet, forward, GraphicAgent.explorer.blockButton(tweet.getUser()),
                    GraphicAgent.explorer.muteButton(tweet.getUser()), report);
        else
            toolBar = new HBox(Properties.loadSize("big-spacing"), like, Comment, retweet, forward);
        forward.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setForward(stackPane, tweet);
            }
        });

        report.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicAgent.serverAgent.tweetAgent.reportTweet(GraphicAgent.username, GraphicAgent.password, tweet.getId());
            }
        });

        retweet.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                TextArea retweetArea = new TextArea();
                ImageView retweetButton = FileHandler.getImage("hagh");
                BorderPane whitePane = new BorderPane();
                whitePane.setId("white-fade");
                stackPane.getChildren().add(whitePane);
                HBox retweetBox = new HBox(Properties.loadSize("medium-spacing"), retweetArea, retweetButton);
                retweetBox.setPrefWidth(Properties.loadSize("quote-retweet-box-width"));
                retweetBox.setAlignment(Pos.CENTER);
                VBox vBox = new VBox(retweetBox);
                vBox.setPrefHeight(Properties.loadSize("quote-retweet-box-height"));
                vBox.setId("white-box");
                vBox.setPadding(new Insets(Properties.loadSize("big-spacing")));
                VBox vBox1 = new VBox(vBox);
                vBox1.setAlignment(Pos.CENTER);
                HBox hBox1 = new HBox(vBox1);
                hBox1.setAlignment(Pos.CENTER);
                stackPane.getChildren().add(hBox1);
                StackPane.setAlignment(hBox1, Pos.CENTER);
                retweetButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        GraphicAgent.serverAgent.tweetAgent.sendReTweet(GraphicAgent.username, GraphicAgent.password, tweet.getId(),
                                retweetArea.getText());
                        GraphicAgent.mainPage.main(GraphicAgent.stage);
                    }
                });
            }
        });
        toolBar.setAlignment(Pos.CENTER_LEFT);

        vBox.getChildren().add(toolBar);
        final Integer[] tmp = {tweet.Likes.size()};
        Label likeCnt = new Label(tmp[0].toString());
        HBox likeCounts = new HBox(Properties.loadSize("tiny-spacing") ,likeCnt, new Label(Properties.loadDialog("likes")));
        HBox counts = new HBox(likeCounts);
        vBox.getChildren().add(counts);

        if (isLiked[0])
            like.setImage(FileHandler.getImage("unlike").getImage());
        else
            like.setImage(FileHandler.getImage("like").getImage());
        like.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!isLiked[0])
                    like.setImage(FileHandler.getImage("unlike").getImage());
                else
                    like.setImage(FileHandler.getImage("like").getImage());
                GraphicAgent.serverAgent.tweetAgent.toggleLike(GraphicAgent.username, GraphicAgent.password, tweet);
                isLiked[0] = !isLiked[0];
                tmp[0] = tweet.Likes.size();
                likeCnt.setText(tmp[0].toString());
            }
        });
        Scene back = GraphicAgent.scene;
        Comment.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicAgent.commentsPage.main(GraphicAgent.stage, back, tweet.from, tweet.getId(), tweet);
            }
        });
        return vBox;
    }

    public static VBox tweets(ArrayList<Tweet> Tweets, StackPane stackPane) {
        VBox tweets = new VBox();
        for (Tweet tweet : Tweets) {
            VBox tweetBox = tweet(tweet, stackPane, Properties.loadSize("normal-tweet-pic-size"), false);
            tweetBox.setId("white-down-line-grey");
            tweets.getChildren().add(tweetBox);
        }
        return tweets;
    }
}
