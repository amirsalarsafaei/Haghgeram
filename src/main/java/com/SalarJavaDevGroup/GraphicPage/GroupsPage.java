package com.SalarJavaDevGroup.GraphicPage;

import com.SalarJavaDevGroup.FileHandling.FileHandler;
import com.SalarJavaDevGroup.FileHandling.Properties;
import com.SalarJavaDevGroup.GraphicAgent;
import com.SalarJavaDevGroup.GraphicComponents.GraphicComponents;
import com.SalarJavaDevGroup.GraphicComponents.GraphicHeaderFooter;
import com.SalarJavaDevGroup.GraphicComponents.GraphicMenu;
import com.SalarJavaDevGroup.Models.Group;
import com.SalarJavaDevGroup.util.Filter;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GroupsPage {

    HBox lastSelected;
    public StackPane Pane;
    void setGroup(Group group, BorderPane borderPane) {
        BorderPane addAndScrollBorderPane = new BorderPane();
        ScrollPane userScroll = new ScrollPane();
        addAndScrollBorderPane.setCenter(userScroll);
        ImageView addUserButton = FileHandler.getImage("add-user");
        HBox addUserBox = new HBox(addUserButton);
        addUserBox.setAlignment(Pos.CENTER);
        addUserBox.setPadding(new Insets(Properties.loadSize("medium-indent")));
        addAndScrollBorderPane.setTop(addUserBox);
        VBox vBox = new VBox();
        userScroll.setContent(vBox);
        vBox.setPrefWidth(Properties.loadSize("setGroup-width"));
        for (String user: group.getUsers()) {
            HBox hBox = new HBox();
            StackPane stackPane = new StackPane();
            Label name = new Label(user);

            ImageView trashIcon = FileHandler.getImage("trash");
            HBox nameBox = new HBox(name);
            nameBox.setAlignment(Pos.CENTER_LEFT);
            HBox trashBox = new HBox(trashIcon);
            trashBox.setAlignment(Pos.CENTER_RIGHT);
            stackPane.getChildren().add(nameBox);
            stackPane.getChildren().add(trashBox);
            stackPane.setPrefWidth(Properties.loadSize("setGroup-width"));
            hBox.getChildren().add(stackPane);
            hBox.setPadding(new Insets(Properties.loadSize("small-indent")));
            vBox.getChildren().add(hBox);
            Line line = new Line();
            line.setStartX(0);
            line.setEndX(Properties.loadSize("setGroup-width"));
            vBox.getChildren().add(line);
            trashIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    GraphicAgent.serverAgent.groupAgent.deleteUserFromGroup(GraphicAgent.username, GraphicAgent.password, group.getName(), user);
                    setGroup(group, borderPane);
                }
            });
        }
        userScroll.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        userScroll.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        addUserButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                addUserToGroup(group, borderPane);
            }
        });
        borderPane.setRight(addAndScrollBorderPane);
    }

    public void addUserToGroup(Group group, BorderPane borderPane1) {
        ArrayList<String> res = new ArrayList<>(group.getUsers());
        BorderPane WhitePane = new BorderPane();
        Pane.getChildren().add(WhitePane);
        WhitePane.setId("white-fate");
        VBox vBox = new VBox();
        vBox.setMaxWidth(Properties.loadSize("add-users-box-width"));
        vBox.setMaxHeight(Properties.loadSize("add-users-box-height"));
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
        ImageView addUsers = FileHandler.getImage("add-user");
        HBox addUsersBox = new HBox(addUsers);
        addUsersBox.setAlignment(Pos.CENTER);
        addUsersBox.setPadding(new Insets(Properties.loadSize("small-indent")));
        borderPane.setTop(addUsersBox);
        borderPane.setPrefSize(Properties.loadSize("add-users-box-width"),
                Properties.loadSize("add-users-box-height") - 1);

        scrollPane.setPadding(new Insets(0));
        userList.setPrefWidth(Properties.loadSize("add-users-box-width") - 4);
        for (String following : GraphicAgent.serverAgent.personalAgent.getFollowing(GraphicAgent.username, GraphicAgent.password)) {
            StackPane stackPane = new StackPane();
            HBox hBox = new HBox(stackPane);
            stackPane.setPrefWidth(Properties.loadSize("add-users-inbox-width"));
            userList.getChildren().add(hBox);
            hBox.setPadding(new Insets(Properties.loadSize("small-indent")));
            Label name = new Label(following);
            stackPane.getChildren().add(name);
            StackPane.setAlignment(name, Pos.CENTER_LEFT);
            final boolean[] inList = {Filter.boolFind(group.getUsers(), following)};
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
                        res.add(following);
                    else
                        Filter.delFind(res, following);
                }
            });
            addUsers.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    WhitePane.setVisible(false);
                    vBox.setVisible(false);
                    GraphicAgent.serverAgent.groupAgent.AddToGroup(GraphicAgent.username, GraphicAgent.password,group.getName(), res);
                    setGroup(group, borderPane1);
                }
            });
        }
    }
    public void main(Stage stage) {
        BorderPane borderPane = new BorderPane();
        Pane = new StackPane(borderPane);
        Scene scene = new Scene(Pane);
        scene.getStylesheets().add("style.css");
        borderPane.setTop(GraphicHeaderFooter.header());
        stage.setScene(scene);
        borderPane.setLeft(GraphicMenu.Menu(stage));
        VBox rightPanel = new VBox();
        rightPanel.setPrefWidth(Properties.loadSize("group-page-right-panel"));
        rightPanel.getChildren().add(new Label(Properties.loadDialog("no-group-selected")));
        rightPanel.setAlignment(Pos.CENTER);
        BorderPane rightPanelBorderPane = new BorderPane();
        rightPanelBorderPane.setCenter(rightPanel);
        borderPane.setRight(rightPanelBorderPane);
        ScrollPane scrollPane = new ScrollPane();
        BorderPane groupPane = new BorderPane();
        borderPane.setCenter(groupPane);
        VBox Groups = new VBox();
        scrollPane.setContent(Groups);
        BorderPane addAndScroll = new BorderPane();
        addAndScroll.setCenter(scrollPane);
        ImageView AddGroupButton = FileHandler.getImage("add-group");
        TextField addGroupField = new TextField();
        addGroupField.setPromptText(Properties.loadDialog("enter-list-name"));

        HBox addGroupHBox = new HBox(Properties.loadSize("medium-indent"), addGroupField,AddGroupButton);
        addGroupHBox.setAlignment(Pos.CENTER);


        Label AddGroupErrorLabel = new Label();
        AddGroupErrorLabel.setId("error");
        VBox addGroupBox = new VBox(Properties.loadSize("small-spacing"), addGroupHBox, AddGroupErrorLabel);
        addGroupBox.setId("grey-border");
        addGroupBox.setPadding(new Insets(Properties.loadSize("big-indent")));
        addGroupBox.setAlignment(Pos.CENTER);
        addAndScroll.setTop(addGroupBox);
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        groupPane.setCenter(addAndScroll);
        AddGroupButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (GraphicAgent.serverAgent.groupAgent.existGroup(GraphicAgent.username, GraphicAgent.password
                        ,addGroupField.getText()))
                    AddGroupErrorLabel.setText(Properties.loadDialog("list-exists"));
                else {
                    GraphicAgent.serverAgent.groupAgent.addGroup(GraphicAgent.username, GraphicAgent.password, addGroupField.getText());
                    AddGroupErrorLabel.setText("");
                    main(stage);
                }
            }
        });
        for (Group group: GraphicAgent.serverAgent.groupAgent.getGroups(GraphicAgent.username, GraphicAgent.password)) {
            StackPane stackPane = new StackPane();
            HBox hBox = new HBox(stackPane);
            Label name = new Label(group.getName());
            ImageView trashIcon = FileHandler.getImage("trash");
            HBox nameBox = new HBox(name);
            HBox trashBox = new HBox(trashIcon);
            nameBox.setAlignment(Pos.CENTER_LEFT);
            trashBox.setAlignment(Pos.CENTER_RIGHT);
            stackPane.getChildren().add(nameBox);
            stackPane.getChildren().add(trashBox);
            stackPane.setPrefWidth(Properties.loadSize("list-name-width"));
            hBox.setPrefWidth(Properties.loadSize("list-name-width"));
            hBox.setPadding(new Insets(Properties.loadSize("medium-indent")));
            Groups.getChildren().add(hBox);
            Line line = new Line();
            line.setStartX(0);
            line.setEndX(Properties.loadSize("list-name-width"));
            Groups.getChildren().add(line);
            hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (lastSelected != null)
                        lastSelected.setId("white");
                    lastSelected = hBox;
                    lastSelected.setId("blue");
                    setGroup(group, borderPane);
                }
            });
            trashIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    GraphicAgent.serverAgent.groupAgent.deleteGroup(GraphicAgent.username, GraphicAgent.password, group.getName());
                    main(stage);
                }
            });
        }

    }
}
