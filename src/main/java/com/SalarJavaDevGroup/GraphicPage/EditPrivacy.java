package com.SalarJavaDevGroup.GraphicPage;

import com.SalarJavaDevGroup.FileHandling.Properties;
import com.SalarJavaDevGroup.GraphicAgent;
import com.SalarJavaDevGroup.GraphicComponents.GraphicComponents;
import com.SalarJavaDevGroup.GraphicComponents.GraphicHeaderFooter;
import com.SalarJavaDevGroup.Models.LastSeen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EditPrivacy {

    public void main() {
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(GraphicHeaderFooter.headerToSetting());
        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add("style.css");
        VBox vBox = new VBox(Properties.loadSize("edit-privacy-spacing"));
        Label lastSeenLabel = new Label(Properties.loadDialog("lastSeen"));
        Label privateLabel = new Label(Properties.loadDialog("privateAccount"));
        ChoiceBox choiceBox = new ChoiceBox();
        choiceBox.getItems().add(LastSeen.Everyone);
        choiceBox.getItems().add(LastSeen.Followings);
        choiceBox.getItems().add(LastSeen.noOne);
        choiceBox.setValue(GraphicAgent.serverAgent.personalAgent.getLastSeen(GraphicAgent.username, GraphicAgent.password));
        CheckBox checkBox = new CheckBox(), deActive = new CheckBox();
        checkBox.setSelected(GraphicAgent.serverAgent.personalAgent.getPrivate(GraphicAgent.username, GraphicAgent.password));
        HBox lastSeenBox = new HBox(Properties.loadSize("big-spacing"), lastSeenLabel, choiceBox);
        HBox privateBox = new HBox(Properties.loadSize("big-spacing"), privateLabel, checkBox);
        HBox deActiveBox = new HBox(Properties.loadSize("big-spacing"), new Label("DeActive Account"), deActive);
        lastSeenBox.setAlignment(Pos.CENTER);
        vBox.setAlignment(Pos.CENTER);
        privateBox.setAlignment(Pos.CENTER);
        deActiveBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(lastSeenBox);
        vBox.getChildren().add(privateBox);
        vBox.getChildren().add(deActiveBox);
        borderPane.setCenter(vBox);
        GraphicAgent.stage.setScene(scene);
        choiceBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GraphicAgent.serverAgent.personalAgent.setLastSeen(GraphicAgent.username, GraphicAgent.password, IndexToLastSeen(choiceBox.getSelectionModel().getSelectedIndex()));
            }
        });
        checkBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicAgent.serverAgent.personalAgent.setPrivate(GraphicAgent.username, GraphicAgent.password, checkBox.isSelected());
                if (!checkBox.isSelected()) {
                    GraphicAgent.serverAgent.personalAgent.acceptAllRequests(GraphicAgent.username, GraphicAgent.password);
                }
            }
        });
        deActive.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicAgent.serverAgent.personalAgent.setActivity(GraphicAgent.username, GraphicAgent.password, deActive.isSelected());
            }
        });
    }

    public LastSeen IndexToLastSeen(int a) {
        if (a == 0)
            return LastSeen.Everyone;
        else if (a == 1)
            return LastSeen.Followings;
        return LastSeen.noOne;
    }
}
