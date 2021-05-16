package com.SalarJavaDevGroup.GraphicPage;

import com.SalarJavaDevGroup.FileHandling.FileHandler;
import com.SalarJavaDevGroup.FileHandling.Properties;
import com.SalarJavaDevGroup.GraphicAgent;
import com.SalarJavaDevGroup.GraphicComponents.GraphicComponents;
import com.SalarJavaDevGroup.GraphicComponents.GraphicHeaderFooter;
import com.SalarJavaDevGroup.GraphicComponents.GraphicMenu;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class NotificationPage {

    private void makeRequests(VBox requestBox, BorderPane centerPane) {
        Label title = new Label(Properties.loadDialog("follow-requests"));
        title.setId("subtitle");
        HBox titleBox = new HBox(title);
        titleBox.setId("down-line-black");
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(Properties.loadSize("medium-indent")));
        requestBox.getChildren().add(titleBox);

        for (String requestUser: GraphicAgent.serverAgent.personalAgent.getPending(GraphicAgent.username, GraphicAgent.password)) {
            StackPane stackPane = new StackPane();
            HBox requestUserBox = new HBox(stackPane);
            requestUserBox.setId("down-line-grey");
            requestUserBox.setPadding(new Insets(Properties.loadSize("medium-indent")));
            requestBox.getChildren().add(requestUserBox);
            Label requestUserLabel = new Label(requestUser);
            stackPane.getChildren().add(requestUserLabel);
            StackPane.setAlignment(requestUserLabel, Pos.CENTER_LEFT);
            ImageView accept = FileHandler.getImage("accept-request");
            ImageView reject = FileHandler.getImage("reject-request");
            ImageView reject_mute = FileHandler.getImage("reject-request-mute");
            HBox accept_rejectBox = new HBox(Properties.loadSize("medium-spacing"), accept, reject, reject_mute);
            accept_rejectBox.setAlignment(Pos.CENTER_RIGHT);
            stackPane.getChildren().add(accept_rejectBox);
            StackPane.setAlignment(accept_rejectBox, Pos.CENTER_RIGHT);
            centerPane.widthProperty().addListener(event -> {
                stackPane.setPrefWidth(centerPane.getWidth()/ 2);
            });
            accept.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    GraphicAgent.serverAgent.notificationAgent.accept_request(GraphicAgent.username, GraphicAgent.password, requestUser);
                    main();
                }
            });
            reject.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    GraphicAgent.serverAgent.notificationAgent.reject_request(GraphicAgent.username,
                            GraphicAgent.password, requestUser, false);
                    main();
                }
            });
            reject_mute.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    GraphicAgent.serverAgent.notificationAgent.reject_request(GraphicAgent.username,
                            GraphicAgent.password, requestUser, true);
                    main();
                }
            });
        }
    }


    private void makeEvents(VBox eventsBox, BorderPane centerPane) {
        Label title = new Label(Properties.loadDialog("events"));
        title.setId("subtitle");
        HBox titleBox = new HBox(title);
        titleBox.setId("down-line-black");
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(Properties.loadSize("medium-indent")));
        eventsBox.getChildren().add(titleBox);

        for (String event: GraphicAgent.serverAgent.notificationAgent.getEvents(GraphicAgent.username, GraphicAgent.password)) {
            Label eventLabel = new Label(event);
            eventLabel.setWrapText(true);
            HBox eventBox = new HBox(eventLabel);
            eventBox.setId("down-line-grey");
            eventBox.setPadding(new Insets(Properties.loadSize("medium-indent")));
            eventsBox.getChildren().add(eventBox);
        }
    }

    public void main() {
        BorderPane mainPane = new BorderPane();
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add("style.css");
        mainPane.setTop(GraphicHeaderFooter.header());
        BorderPane centerPane = new BorderPane();
        mainPane.setLeft(GraphicMenu.Menu(GraphicAgent.stage));
        mainPane.setCenter(centerPane);
        VBox requestsBox = new VBox();
        VBox eventBox = new VBox();

        ScrollPane requestScroll = new ScrollPane(requestsBox);
        ScrollPane eventScroll = new ScrollPane(eventBox);
        centerPane.setLeft(requestScroll);
        centerPane.setRight(eventScroll);
        makeRequests(requestsBox, centerPane);
        makeEvents(eventBox, centerPane);
        GraphicAgent.stage.setScene(scene);
        centerPane.widthProperty().addListener(event -> {
            requestsBox.setPrefWidth(centerPane.getWidth()/ 2 - 4);
            eventBox.setPrefWidth(centerPane.getWidth() / 2 - 4);
        });
    }
}
