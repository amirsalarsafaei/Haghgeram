package com.SalarJavaDevGroup.GraphicComponents;

import com.SalarJavaDevGroup.FileHandling.FileHandler;
import com.SalarJavaDevGroup.FileHandling.Properties;
import com.SalarJavaDevGroup.GraphicAgent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

public class GraphicHeaderFooter {
    static public VBox header () {
        Line line = new Line();
        line.setStartX(0);
        line.setEndX(Properties.loadSize("line-frame"));
        HBox hBox = new HBox(FileHandler.getImage("smallLogo"));
        hBox.setAlignment(Pos.CENTER);
        hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicAgent.mainPage.main(GraphicAgent.stage);
            }
        });
        VBox vBox = new VBox(hBox, line);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    static public VBox footer () {
        Line line = new Line();
        line.setStartX(0);
        line.setEndX(Properties.loadSize("line-frame"));
        HBox hBox = new HBox(FileHandler.getImage("smallLogo"));
        hBox.setAlignment(Pos.CENTER);
        hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicAgent.mainPage.main(GraphicAgent.stage);
            }
        });
        VBox vBox = new VBox(line, hBox);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }
    static public VBox headerToSetting () {
        Line line = new Line();
        line.setStartX(0);
        line.setEndX(Properties.loadSize("line-frame"));
        ImageView logo = FileHandler.getImage("smallLogo");
        ImageView back = FileHandler.getImage("back");
        StackPane stackPane = new StackPane();
        HBox hBox = new HBox(logo);
        hBox.setAlignment(Pos.CENTER);
        stackPane.getChildren().add(hBox);
        hBox = new HBox(back);
        hBox.setAlignment(Pos.CENTER_LEFT);
        stackPane.getChildren().add(hBox);

        logo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicAgent.mainPage.main(GraphicAgent.stage);
            }
        });

        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicAgent.settingPage.main();
            }
        });
        VBox vBox = new VBox(stackPane, line);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

}
