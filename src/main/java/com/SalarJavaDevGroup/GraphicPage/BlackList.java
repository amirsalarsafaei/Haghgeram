package com.SalarJavaDevGroup.GraphicPage;

import com.SalarJavaDevGroup.FileHandling.FileHandler;
import com.SalarJavaDevGroup.FileHandling.Properties;
import com.SalarJavaDevGroup.GraphicAgent;
import com.SalarJavaDevGroup.GraphicComponents.GraphicHeaderFooter;
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

public class BlackList {
    public void main() {
        BorderPane mainPane = new BorderPane();
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add("style.css");
        mainPane.setTop(GraphicHeaderFooter.headerToSetting());
        VBox blockList = new VBox();
        ScrollPane blockScroll = new ScrollPane(blockList);

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
            blockScroll.widthProperty().addListener(event -> {
                userAndUnBlock.setPrefWidth(blockScroll.getWidth()- 2 * (Properties.loadSize("scroll-border") +
                        Properties.loadSize("medium-indent")));
            });
            unblock.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    GraphicAgent.serverAgent.profileAgent.toggleBlock(GraphicAgent.username, GraphicAgent.password,
                            blockedUsername);
                    main();
                }
            });
            blockList.getChildren().add(userBox);
            userBox.setId("down-line-grey");
            userBox.setPadding(new Insets(Properties.loadSize("medium-indent")));
        }
        GraphicAgent.stage.setScene(scene);
    }
}
