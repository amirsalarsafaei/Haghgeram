package com.SalarJavaDevGroup.GraphicPage;

import com.SalarJavaDevGroup.FileHandling.FileHandler;
import com.SalarJavaDevGroup.FileHandling.Load;
import com.SalarJavaDevGroup.FileHandling.Properties;
import com.SalarJavaDevGroup.GraphicAgent;
import com.SalarJavaDevGroup.GraphicComponents.GraphicComponents;
import com.SalarJavaDevGroup.GraphicComponents.GraphicHeaderFooter;
import com.SalarJavaDevGroup.GraphicComponents.GraphicMenu;
import com.SalarJavaDevGroup.GraphicComponents.GraphicTweet;
import com.SalarJavaDevGroup.Models.Conversation;
import com.SalarJavaDevGroup.Models.Group;
import com.SalarJavaDevGroup.Models.Message;
import com.SalarJavaDevGroup.Models.Tweet;
import com.SalarJavaDevGroup.util.Filter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MessengerPage {

    public VBox lastChecked;
    public Label lastPreview;
    StackPane stackPane;


    public static void setGroupMessage(StackPane Pane) {
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
        TextArea messageArea = new TextArea();
        messageArea.setPrefColumnCount(Properties.loadNumbers("setGroupMessageColumn"));
        HBox addUsersBox = new HBox(Properties.loadSize("medium-spacing"), messageArea, addUsers);
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
                WhitePane.setVisible(false);
                vBox.setVisible(false);
                for (Group group : toGroups) {
                    for (String user : group.getUsers()) {
                        if (!Filter.boolFind(toUsers, user))
                            toUsers.add(user);
                    }
                }
                for (String user: toUsers)
                    GraphicAgent.serverAgent.profileAgent.sendMessageFromProfile(GraphicAgent.username,
                            GraphicAgent.password, user, messageArea.getText());
                for (Conversation conv : toConv) {
                    GraphicAgent.serverAgent.messengerAgent.sendMessage(GraphicAgent.username, GraphicAgent.password
                            ,messageArea.getText(),conv.getId());
                }
                GraphicAgent.messengerPage.conversations(GraphicAgent.stage);
            }
        });
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPadding(new Insets(0));
        userList.setPrefWidth(Properties.loadSize("forward-user-list-width"));
    }
    public void setGroup() {
        ArrayList<String> res = new ArrayList<>();
        res.add(GraphicAgent.username);
        BorderPane WhitePane = new BorderPane();
        stackPane.getChildren().add(WhitePane);
        WhitePane.setId("whitePane");
        VBox vBox = new VBox();
        vBox.setMaxWidth(Properties.loadSize("set-group-chat-box-width"));
        vBox.setMaxHeight(Properties.loadSize("set-group-chat-box-height"));
        vBox.setAlignment(Pos.CENTER);
        BorderPane borderPane = new BorderPane();
        vBox.getChildren().add(borderPane);
        vBox.setId("white-box");
        DropShadow dropShadow = new DropShadow(Properties.loadSize("small-shadow"), Color.BLACK);
        vBox.setEffect(dropShadow);
        stackPane.getChildren().add(vBox);
        StackPane.setAlignment(vBox, Pos.CENTER);
        ScrollPane scrollPane = new ScrollPane();
        borderPane.setCenter(scrollPane);
        VBox userList = new VBox();
        scrollPane.setContent(userList);
        ImageView addUsers = FileHandler.getImage("add-group-chat");
        TextField groupName = new TextField();
        HBox tmpBox = new HBox(Properties.loadSize("small-spacing"), groupName,addUsers);
        tmpBox.setAlignment(Pos.CENTER);
        Label group_name_error = new Label(Properties.loadDialog("empty-field"));
        group_name_error.setId("error");
        group_name_error.setVisible(false);
        VBox addUsersBox = new VBox(tmpBox);
        addUsersBox.setAlignment(Pos.CENTER);
        addUsersBox.setPadding(new Insets(Properties.loadSize("small-indent")));
        borderPane.setTop(addUsersBox);
        borderPane.setPrefSize(Properties.loadSize("set-group-chat-box-width"),
                Properties.loadSize("set-group-chat-box-height") - 1);
        scrollPane.setPadding(new Insets(0));
        userList.setPrefWidth(Properties.loadSize("set-group-chat-box-width") - 4);
        for (String following : GraphicAgent.serverAgent.personalAgent.getFollowing(GraphicAgent.username, GraphicAgent.password)) {
            StackPane stackPane = new StackPane();
            HBox hBox = new HBox(stackPane);
            stackPane.setPrefWidth(Properties.loadSize("set-group-chat-inbox-width"));
            userList.getChildren().add(hBox);
            hBox.setPadding(new Insets(Properties.loadSize("small-indent")));
            Label name = new Label(following);
            stackPane.getChildren().add(name);
            StackPane.setAlignment(name, Pos.CENTER_LEFT);
            final boolean[] inList = {false};
            ImageView check = FileHandler.getImage("check-" + inList[0]);
            stackPane.getChildren().add(check);
            StackPane.setAlignment(check, Pos.CENTER_RIGHT);
            hBox.setId("grey-border");

            check.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    inList[0] = !inList[0];
                    check.setImage(FileHandler.getImage("check-" + inList[0]).getImage());

                    if (inList[0])
                        res.add(following);
                    else
                        Filter.delFind(res, following);
                }
            });
            addUsers.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (groupName.getText().equals("")) {
                        group_name_error.setVisible(true);
                        return;
                    }
                    WhitePane.setVisible(false);
                    vBox.setVisible(false);
                    GraphicAgent.serverAgent.messengerAgent.makeGroup(GraphicAgent.username, GraphicAgent.password,res,
                            groupName.getText());

                }
            });
        }
    }

    public void conversations(Stage stage) {
        BorderPane borderPane = new BorderPane();
        stackPane = new StackPane(borderPane);
        Scene scene = new Scene(stackPane);
        GraphicAgent.scene = scene;
        borderPane.setTop(GraphicHeaderFooter.header());
        scene.getStylesheets().add("style.css");
        BorderPane tmp = new BorderPane();
        BorderPane messagePane = new BorderPane();
        borderPane.setCenter(messagePane);
        ImageView add_group = FileHandler.getImage("add-group-chat");
        ImageView forward = FileHandler.getImage("forward");
        HBox hBox = new HBox(10, add_group, forward);
        forward.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setGroupMessage(stackPane);
            }
        });
        hBox.setAlignment(Pos.CENTER);
        messagePane.setTop(hBox);
        add_group.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setGroup();
            }
        });
        hBox.setId("black-border");
        messagePane.setCenter(Conversations
                (GraphicAgent.serverAgent.messengerAgent.getConversation(GraphicAgent.username, GraphicAgent.password), scene, tmp, stackPane));
        borderPane.setLeft(GraphicMenu.Menu(stage));
        borderPane.setRight(tmp);
        VBox vBox = new VBox(new Label(Properties.loadDialog("no-conv-selected")));
        vBox.setAlignment(Pos.CENTER);
        vBox.setId("white");
        tmp.setCenter(vBox);
        vBox.setPrefWidth(Properties.loadSize("conversation-width"));
        stage.setScene(scene);

    }

    public void conversation(BorderPane borderPane, Conversation conversation, StackPane stackPane) {
        VBox chats = new VBox(Properties.loadSize("small-spacing"));
        ScrollPane scrollPane = new ScrollPane(chats);
        borderPane.setCenter(scrollPane);
        Label convName = new Label(conversation.getName(GraphicAgent.username));
        convName.setId("subtitle");
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVvalue(scrollPane.getVmax());
        TextArea textArea = new TextArea();
        textArea.setPrefRowCount(Properties.loadNumbers("message-text-area-row"));
        ImageView sendButton = FileHandler.getImage("forward");
        ImageView imageButton = FileHandler.getImage("image");
        VBox sendButtonBox = new VBox(Properties.loadSize("medium-spacing"), sendButton, imageButton);
        sendButtonBox.setAlignment(Pos.CENTER);
        HBox sendMessageBox = new HBox(Properties.loadSize("big-spacing"), textArea, sendButtonBox);
        sendMessageBox.setPadding(new Insets(Properties.loadSize("big-indent")));
        sendMessageBox.setAlignment(Pos.CENTER);
        final boolean[] hasImage = {false};
        final File[] selectedFile = new File[1];
        FileChooser fileChooser = new FileChooser();
        imageButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectedFile[0] = fileChooser.showOpenDialog(GraphicAgent.stage);
                hasImage[0] = true;
            }
        });
        borderPane.setBottom(sendMessageBox);
        sendButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!hasImage[0])
                    GraphicAgent.serverAgent.messengerAgent.sendMessage(GraphicAgent.username, GraphicAgent.password,
                            textArea.getText(), conversation.getId());
                else
                    GraphicAgent.serverAgent.messengerAgent.sendMessage(GraphicAgent.username, GraphicAgent.password,
                            textArea.getText(), conversation.getId(), selectedFile[0]);
                conversation(borderPane, conversation, stackPane);
            }
        });
        scrollPane.setContent(chats);
        LocalDateTime tmp = null;
        if (conversation.getMessages().size() != 0) {
            chats.getChildren().add(GraphicComponents.date(conversation.getMessages().get(0).timeSent));
            tmp = conversation.getMessages().get(0).timeSent;
        }
        for (Message message:conversation.getMessages()) {
            if (tmp.getYear() != message.timeSent.getYear() || tmp.getDayOfYear() != message.timeSent.getDayOfYear()) {
                chats.getChildren().add(GraphicComponents.date(message.timeSent ));
                tmp = message.timeSent;
            }

            if (message.sender.equals(GraphicAgent.username)) {
                String hp = message.timeSent.getHour() + ":" + message.timeSent.getMinute();
                Label time = new Label(hp);
                time.setId("small-text");

                HBox timeBox = new HBox(time);
                timeBox.setAlignment(Pos.CENTER_RIGHT);
                VBox content = new VBox();
                Label label = new Label(message.Content);
                HBox hBox = new HBox(label);

                getMessageImage(message, content);
                getMessageTweet(message, content, stackPane);
                content.getChildren().add(hBox);
                label.setId("medium-text");
                hBox.setAlignment(Pos.CENTER_LEFT);
                VBox messageBox= new VBox(content,timeBox);
                messageBox.setId("message-box-user");
                HBox salar = new HBox(messageBox);
                salar.setAlignment(Pos.CENTER_RIGHT);
                chats.setId("white");
                Circle circle = new Circle(0, 0, Properties.loadSize("tiny-profile-radius"));
                ImageView profilePic = Load.LoadUserProfileImage(message.sender);
                circle.setFill(new ImagePattern(profilePic.getImage()));
                DropShadow dropShadow = new DropShadow(Properties.loadSize("tiny-shadow"), Color.BLACK);
                circle.setEffect(dropShadow);
                MenuItem edit = new MenuItem("edit");
                MenuItem delete = new MenuItem("delete");
                ContextMenu contextMenu = new ContextMenu(edit, delete);
                salar.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                    @Override
                    public void handle(ContextMenuEvent contextMenuEvent) {
                        contextMenu.show(salar, contextMenuEvent.getSceneX(), contextMenuEvent.getSceneY());
                    }
                });
                delete.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        GraphicAgent.serverAgent.messengerAgent.deleteMessage(GraphicAgent.username, GraphicAgent.password,
                                message.getId(), conversation.getId());
                        conversation(borderPane, conversation, stackPane);
                    }
                });
                edit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        TextArea messageEditArea = new TextArea();
                        ImageView messageEditButton = FileHandler.getImage("forward");
                        messageEditArea.setText(message.Content);
                        BorderPane whitePane = new BorderPane();
                        whitePane.setStyle("-fx-background-color: #ffffff;");
                        whitePane.setOpacity(0.7);
                        stackPane.getChildren().add(whitePane);
                        HBox messageEditBox = new HBox(20, messageEditArea, messageEditButton);
                        messageEditBox.setPrefWidth(450);
                        messageEditBox.setAlignment(Pos.CENTER);
                        VBox vBox = new VBox(messageEditBox);
                        vBox.setPrefHeight(150);
                        VBox vBox1 = new VBox(vBox);
                        vBox1.setAlignment(Pos.CENTER);
                        HBox hBox1 = new HBox(vBox1);
                        hBox1.setAlignment(Pos.CENTER);
                        stackPane.getChildren().add(hBox1);
                        StackPane.setAlignment(hBox1, Pos.CENTER);
                        messageEditButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                GraphicAgent.serverAgent.messengerAgent.editMessage(GraphicAgent.username, GraphicAgent.password,
                                        message.getId(), conversation.getId(), messageEditArea.getText());
                                whitePane.setVisible(false);
                                hBox1.setVisible(false);
                                conversation(borderPane, conversation, stackPane);
                            }
                        });
                    }
                });
                HBox PicAndMessage = new HBox(Properties.loadSize("small-spacing"), salar, circle);
                PicAndMessage.setAlignment(Pos.CENTER_RIGHT);
                chats.getChildren().add(PicAndMessage);
            }
            else{
                String hp = message.timeSent.getHour() + ":" + message.timeSent.getMinute();
                Label time = new Label(hp);
                time.setId("small-text");

                HBox timeBox = new HBox(time);
                timeBox.setAlignment(Pos.CENTER_RIGHT);
                Label label = new Label(message.Content);
                HBox hBox = new HBox(label);
                VBox content = new VBox();
                getMessageImage(message, content);
                getMessageTweet(message, content, stackPane);
                content.getChildren().add(hBox);
                content.setAlignment(Pos.CENTER_LEFT);
                label.setId("medium-text");
                hBox.setAlignment(Pos.CENTER_RIGHT);
                Label userLabel = new Label(message.sender);
                userLabel.setId("message-sender");
                HBox userBox = new HBox(userLabel);
                userBox.setAlignment(Pos.CENTER_LEFT);
                VBox messageBox= new VBox(userBox,content,timeBox);
                messageBox.setId("message-box-target");
                HBox salar = new HBox(messageBox);
                salar.setAlignment(Pos.CENTER_LEFT);
                chats.setId("white");
                Circle circle = new Circle(0, 0, Properties.loadSize("tiny-profile-radius"));
                ImageView profilePic = Load.LoadUserProfileImage(message.sender);
                circle.setFill(new ImagePattern(profilePic.getImage()));
                DropShadow dropShadow = new DropShadow(Properties.loadSize("small-shadow"), Color.BLACK);
                circle.setEffect(dropShadow);

                HBox PicAndMessage = new HBox(Properties.loadSize("small-indent"), circle, salar);
                PicAndMessage.setAlignment(Pos.CENTER_LEFT);
                chats.getChildren().add(PicAndMessage);
                message.read(GraphicAgent.username);
            }
            conversation.save();
        }
        chats.setPrefWidth(Properties.loadSize("conversation-in-width"));
        borderPane.setPrefWidth(Properties.loadSize("conversation-width"));
        conversation.save();
        scrollPane.setId("white");
    }

    private void getMessageImage(Message message, VBox content) {
        if (message.hasImage) {
            Image image = FileHandler.loadImage(message.getId(), "messagesImage").getImage();
            int widthSize = Properties.loadSize("message-pic-width");
            ImagePattern pattern = new ImagePattern(
                    image, 0, 0, widthSize, image.getHeight() * widthSize / image.getWidth() , false
            );
            Rectangle rectangle = new Rectangle(0, 0, widthSize, image.getHeight() * widthSize / image.getWidth());
            rectangle.setArcWidth(Properties.loadSize("message-pic-radius"));   // Corner radius
            rectangle.setArcHeight(Properties.loadSize("message-pic-radius"));
            rectangle.setFill(pattern);
            rectangle.setEffect(new DropShadow(Properties.loadSize("tiny-shadow"), Color.BLACK));  // Shadow
            HBox hp2 = new HBox(rectangle);
            hp2.setAlignment(Pos.CENTER);
            hp2.setPadding(new Insets(Properties.loadSize("big-indent")));
            content.getChildren().add(hp2);
        }
    }


    private void getMessageTweet(Message message, VBox content, StackPane stackPane) {
        if (message.tweet != -1) {
            VBox vBox = new VBox(GraphicTweet.tweet(Load.LoadTweet(message.tweet), stackPane,
                    Properties.loadSize("message-tweet-width"), false));
            vBox.setPadding(new Insets(Properties.loadSize("medium-indent")));
            content.getChildren().add(vBox);
        }
    }

    public static VBox ConvBox(Conversation conversation, Scene scene, BorderPane borderPane, StackPane stackPane) {
        VBox vBox = new VBox();
        Label nameLabel = new Label(conversation.getName(GraphicAgent.username));
        vBox.getChildren().add(new HBox(Properties.loadSize("small-spacing"), nameLabel, unread(conversation)));
        vBox.setPadding(new Insets(Properties.loadSize("medium-indent")));
        Label preview = new Label(conversation.getLastMessage());
        preview.setId("preview-grey");
        HBox hBox = new HBox(preview);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(10));
        vBox.getChildren().add(hBox);
        vBox.setPrefWidth(360);
        vBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (GraphicAgent.messengerPage.lastChecked != null) {
                    GraphicAgent.messengerPage.lastChecked.setId("white");
                    GraphicAgent.messengerPage.lastPreview.setId("preview-grey");
                }
                vBox.setId("blue");
                preview.setId("preview-white");
                GraphicAgent.messengerPage.lastChecked = vBox;
                GraphicAgent.messengerPage.lastPreview = preview;
                GraphicAgent.messengerPage.conversation(borderPane, conversation, stackPane);
            }
        });
        return vBox;
    }

    public static ScrollPane Conversations(ArrayList<Conversation> conversations, Scene scene, BorderPane borderPane,
                                           StackPane stackPane) {
        VBox vBox = new VBox();
        ScrollPane scrollPane = new ScrollPane();
        for (Conversation conversation:conversations) {
            Line line = new Line();
            line.setOpacity(0.3);
            line.setStartX(0);
            line.setEndX(Properties.loadSize("conversation-name-list-width"));
            vBox.getChildren().add(ConvBox(conversation, scene, borderPane, stackPane));
            vBox.getChildren().add(line);

        }
        scrollPane.setContent(vBox);
        vBox.setId("white");
        return scrollPane;
    }

    public static StackPane unread(Conversation conversation) {
        StackPane stackPane = new StackPane();
        Circle circle = new Circle();
        Label label = new Label(String.valueOf(conversation.unread(GraphicAgent.username)));
        label.setId("white-text");
        circle.setFill(Color.BLUE);
        circle.radiusProperty().bind(label.widthProperty());
        stackPane.getChildren().addAll(circle, label);
        return stackPane;
    }
}
