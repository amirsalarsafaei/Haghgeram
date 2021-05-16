package com.SalarJavaDevGroup.GraphicComponents;

import com.SalarJavaDevGroup.FileHandling.FileHandler;
import com.SalarJavaDevGroup.FileHandling.Load;
import com.SalarJavaDevGroup.FileHandling.Properties;
import com.SalarJavaDevGroup.GraphicAgent;
import com.SalarJavaDevGroup.Models.Comment;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class GraphicComponents {

    public static HBox date(LocalDateTime time) {
        String res = "";
        Label label = new Label();
        HBox hBox = new HBox(label);
        hBox.setAlignment(Pos.CENTER);
        label.setId("date-in-chat");
        label.setPadding(new Insets(Properties.loadSize("tiny-indent"),Properties.loadSize("medium-indent")
                , Properties.loadSize("tiny-indent"), Properties.loadSize("medium-indent")));
        if (time.getYear() == LocalDateTime.now().getYear() && time.getDayOfYear() == LocalDateTime.now().getDayOfYear()) {
            label.setText(Properties.loadDialog("today"));
            return hBox;
        }
        if (time.getYear() != LocalDateTime.now().getYear())
            res += time.getYear() + " ";
        res += time.getMonth() + " " + time.getDayOfMonth();
        label.setText(res);
        return hBox;
    }


    public static VBox RightPanel() {
        VBox rightPanel = new VBox();
        rightPanel.setPrefWidth(Properties.loadSize("right-panel"));
        return rightPanel;
    }
}