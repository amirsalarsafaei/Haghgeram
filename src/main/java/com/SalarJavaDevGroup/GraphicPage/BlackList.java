package com.SalarJavaDevGroup.GraphicPage;

import com.SalarJavaDevGroup.FileHandling.FileHandler;
import com.SalarJavaDevGroup.GraphicAgent;
import com.SalarJavaDevGroup.GraphicComponents.GraphicHeaderFooter;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class BlackList {
    public void main() {
        BorderPane mainPane = new BorderPane();
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add("style.css");
        mainPane.setTop(GraphicHeaderFooter.headerToSetting());
        ScrollPane blockScroll = new ScrollPane();
        VBox blockList = new VBox();
        mainPane.setCenter(blockScroll);
        for (String blockedUsername: GraphicAgent.serverAgent.personalAgent.getBlocked(GraphicAgent.username, GraphicAgent.password)) {
            StackPane userAndUnBlock = new StackPane();
            HBox userBox = new HBox(userAndUnBlock);
            Label blockedUserLabel = new Label(blockedUsername);
            userAndUnBlock.getChildren().add(blockedUserLabel);
            StackPane.setAlignment(blockedUserLabel, Pos.CENTER_LEFT);
            ImageView unblock = FileHandler.getImage("unblock-setting");
            userAndUnBlock.getChildren().add(unblock);
            StackPane.setAlignment(unblock, Pos.CENTER_RIGHT);
            userAndUnBlock.setPrefWidth(blockScroll.getPrefWidth());
            blockList.getChildren().add(userBox);
        }
        GraphicAgent.stage.setScene(scene);
    }
}
