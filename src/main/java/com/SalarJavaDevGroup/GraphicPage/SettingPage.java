package com.SalarJavaDevGroup.GraphicPage;

import com.SalarJavaDevGroup.FileHandling.FileHandler;
import com.SalarJavaDevGroup.FileHandling.Properties;
import com.SalarJavaDevGroup.GraphicAgent;
import com.SalarJavaDevGroup.GraphicComponents.GraphicComponents;
import com.SalarJavaDevGroup.GraphicComponents.GraphicHeaderFooter;
import com.SalarJavaDevGroup.GraphicComponents.GraphicMenu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DisplacementMap;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class SettingPage {
    public void main() {

        BorderPane borderPane = new BorderPane();
        StackPane mainPane = new StackPane();
        mainPane.getChildren().add(borderPane);
        borderPane.setTop(GraphicHeaderFooter.header());
        borderPane.setLeft(GraphicMenu.Menu(GraphicAgent.stage));
        TilePane tilePane = new TilePane();
        tilePane.setId("grey-border");
        borderPane.setCenter(tilePane);
        ImageView edit_profile = FileHandler.getImage("edit-profile");
        ImageView edit_privacy = FileHandler.getImage("edit-privacy");
        ImageView delete_account = FileHandler.getImage("delete-account");
        ImageView black_list = FileHandler.getImage("black-list");
        ImageView requests_status = FileHandler.getImage("requests-status");
        ImageView logout = FileHandler.getImage("logout");
        addHoverShadow(edit_profile);
        addHoverShadow(edit_privacy);
        addHoverShadow(delete_account);
        addHoverShadow(black_list);
        addHoverShadow(requests_status);
        addHoverShadow(logout);
        edit_profile.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicAgent.editProfilePage.main();
            }
        });
        edit_privacy.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicAgent.editPrivacy.main();
            }
        });
        delete_account.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                BorderPane whitePane = new BorderPane();
                whitePane.setId("white-fade");
                mainPane.getChildren().add(whitePane);
                Label confirmLabel = new Label(Properties.loadDialog("delete-confirm-dialog"));
                Button yes = new Button(Properties.loadDialog("yes")),
                        no = new Button(Properties.loadDialog("no"));
                HBox yesNoBox = new HBox(Properties.loadSize("big-spacing"), yes, no);
                VBox confirmVBox = new VBox(Properties.loadSize("medium-spacing"),confirmLabel, yesNoBox);
                confirmVBox.setId("white");
                confirmVBox.setAlignment(Pos.CENTER);
                confirmVBox.setPadding(new Insets(Properties.loadSize("big-indent")));
                HBox sizingConfBox = new HBox(confirmVBox);
                VBox sizingConFVBox = new VBox(sizingConfBox);
                yesNoBox.setAlignment(Pos.CENTER);
                mainPane.getChildren().add(sizingConFVBox);
                sizingConFVBox.setAlignment(Pos.CENTER);
                sizingConfBox.setAlignment(Pos.CENTER);
                confirmVBox.setId("white-box");
                StackPane.setAlignment(sizingConFVBox, Pos.CENTER);
                yes.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        GraphicAgent.serverAgent.personalAgent.deleteAccount(GraphicAgent.username, GraphicAgent.password);
                        GraphicAgent.authPage.login(GraphicAgent.stage);
                    }
                });
                no.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        sizingConFVBox.setVisible(false);
                        whitePane.setVisible(false);
                    }
                });

            }
        });
        black_list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });
        tilePane.getChildren().add(edit_profile);
        tilePane.getChildren().add(edit_privacy);
        tilePane.getChildren().add(delete_account);
        tilePane.getChildren().add(black_list);
        tilePane.getChildren().add(requests_status);
        tilePane.getChildren().add(logout);
        tilePane.setPadding(new Insets(Properties.loadSize("big-indent")));
        tilePane.setVgap(Properties.loadSize("setting-page-tile-v-gap"));
        tilePane.setHgap(Properties.loadSize("setting-page-tile-h-gap"));
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add("style.css");
        GraphicAgent.stage.setScene(scene);
        GraphicAgent.scene = scene;
    }



    public void addHoverShadow(ImageView imageView) {
        imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                imageView.setEffect(new DropShadow(Properties.loadSize("small-shadow"), Color.BLACK));
            }
        });
        imageView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                imageView.setEffect(null);
            }
        });
    }
}
