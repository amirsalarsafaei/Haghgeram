package com.SalarJavaDevGroup.GraphicPage;

import com.SalarJavaDevGroup.FileHandling.Properties;
import com.SalarJavaDevGroup.GraphicAgent;
import com.SalarJavaDevGroup.GraphicComponents.GraphicHeaderFooter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class RequestsPage {
    public void main() {
        BorderPane mainPane = new BorderPane();
        Scene scene = new Scene(mainPane);
        GraphicAgent.stage.setScene(scene);
        scene.getStylesheets().add("style.css");
        mainPane.setTop(GraphicHeaderFooter.headerToSetting());
        VBox requestList = new VBox();
        ScrollPane scrollPane = new ScrollPane(requestList);
        mainPane.setCenter(scrollPane);
        addRequest(Properties.loadDialog("pending"),
                GraphicAgent.serverAgent.personalAgent.getPending(GraphicAgent.username, GraphicAgent.password),
                requestList,
                scrollPane);
        addRequest(Properties.loadDialog("accepted"),
                GraphicAgent.serverAgent.personalAgent.getAccepted(GraphicAgent.username, GraphicAgent.password),
                requestList,
                scrollPane);
        addRequest(Properties.loadDialog("rejected"),
                GraphicAgent.serverAgent.personalAgent.getRejected(GraphicAgent.username, GraphicAgent.password),
                requestList,
                scrollPane);
    }

    public void addRequest(String type, ArrayList<String> arrayList, VBox list, ScrollPane scrollPane) {
        Label typeLabel = new Label(type);
        typeLabel.setId("subtitle-blue");
        typeLabel.setAlignment(Pos.CENTER);
        HBox typeBox = new HBox(typeLabel);
        typeBox.setAlignment(Pos.CENTER);
        typeBox.setId("down-line-black");
        scrollPane.widthProperty().addListener(event -> {
            typeLabel.setPrefWidth(scrollPane.getWidth()- 2 * (Properties.loadSize("scroll-border") + Properties.loadSize("big-indent")));
        });
        typeBox.setPadding(new Insets(Properties.loadSize("big-indent")));
        list.getChildren().add(typeBox);
        for (String user:arrayList) {
            Label userLabel = new Label(user);
            HBox userBox = new HBox(userLabel);
            userBox.setAlignment(Pos.CENTER);
            userBox.setPadding(new Insets(Properties.loadSize("medium-indent")));
            userBox.setId("down-line-grey");
            list.getChildren().add(userBox);
            userLabel.setAlignment(Pos.CENTER);
            scrollPane.widthProperty().addListener(event -> {
                userLabel.setPrefWidth(scrollPane.getWidth() - 2 * (Properties.loadSize("scroll-border") +
                        Properties.loadSize("medium-indent")));
            });
        }
    }
}
