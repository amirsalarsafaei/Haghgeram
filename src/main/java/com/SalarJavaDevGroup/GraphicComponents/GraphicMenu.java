package com.SalarJavaDevGroup.GraphicComponents;

import com.SalarJavaDevGroup.FileHandling.FileHandler;
import com.SalarJavaDevGroup.FileHandling.Properties;
import com.SalarJavaDevGroup.GraphicAgent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GraphicMenu {

    public static Insets buttonInsets() {
        return new Insets(Properties.loadSize("tiny-indent"), Properties.loadSize("big-indent"),
                Properties.loadSize("tiny-indent"), Properties.loadSize("big-indent"));
    }

    public static VBox Menu(Stage stage) {
        ImageView HomeImage =  FileHandler.getImage("Home");
        HBox homeBox = new HBox(HomeImage);
        homeBox.setAlignment(Pos.CENTER);
        homeBox.setId("menu-button");
        homeBox.setPadding(buttonInsets());
        ImageView MessageImage = FileHandler.getImage("chats");
        HBox messengerBox = new HBox( MessageImage);
        messengerBox.setAlignment(Pos.CENTER);
        messengerBox.setId("menu-button");
        messengerBox.setPadding(buttonInsets());
        homeBox.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                HomeImage.setImage(FileHandler.getImage("home-hover").getImage());
            }
        });

        homeBox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                HomeImage.setImage(FileHandler.getImage("Home").getImage());
            }
        });
        homeBox.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicAgent.mainPage.main(stage);
            }
        });
        messengerBox.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                MessageImage.setImage(FileHandler.getImage("chats-hover").getImage());
            }
        });
        messengerBox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                MessageImage.setImage(FileHandler.getImage("chats").getImage());
            }
        });
        messengerBox.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicAgent.messengerPage.conversations(stage);
            }
        });
        ImageView listImage = FileHandler.getImage("list");
        HBox listBox = new HBox(listImage);
        listBox.setAlignment(Pos.CENTER);
        listBox.setId("menu-button");
        listBox.setPadding(buttonInsets());
        listBox.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                listImage.setImage(FileHandler.getImage("list-hover").getImage());
            }
        });
        listBox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                listImage.setImage(FileHandler.getImage("list").getImage());
            }
        });
        listBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicAgent.groupsPage.main(stage);
            }
        });
        ImageView searchImage = FileHandler.getImage("search");
        HBox searchBox = new HBox(searchImage);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.setId("menu-button");
        searchBox.setPadding(buttonInsets());
        searchBox.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                searchImage.setImage(FileHandler.getImage("search-hover").getImage());
            }
        });
        searchBox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                searchImage.setImage(FileHandler.getImage("search").getImage());
            }
        });
        searchBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicAgent.explorer.main();
            }
        });
        ImageView personalImage = FileHandler.getImage("personal");
        HBox personalBox = new HBox(personalImage);
        personalBox.setAlignment(Pos.CENTER);
        personalBox.setId("menu-button");
        personalBox.setPadding(buttonInsets());
        personalBox.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                personalImage.setImage(FileHandler.getImage("personal-hover").getImage());
            }
        });
        personalBox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                personalImage.setImage(FileHandler.getImage("personal").getImage());
            }
        });
        personalBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicAgent.personalPage.main();
            }
        });


        ImageView settingImage = FileHandler.getImage("setting");
        HBox settingBox = new HBox(settingImage);
        settingBox.setAlignment(Pos.CENTER);
        settingBox.setId("menu-button");
        settingBox.setPadding(buttonInsets());
        settingBox.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                settingImage.setImage(FileHandler.getImage("setting-hover").getImage());
            }
        });
        settingBox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                settingImage.setImage(FileHandler.getImage("setting").getImage());
            }
        });
        settingBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicAgent.settingPage.main();
            }
        });

        ImageView notificationImage = FileHandler.getImage("notification");
        HBox notificationBox = new HBox(notificationImage);
        notificationBox.setAlignment(Pos.CENTER);
        notificationBox.setId("menu-button");
        notificationBox.setPadding(buttonInsets());
        notificationBox.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                notificationImage.setImage(FileHandler.getImage("notification-hover").getImage());
            }
        });
        notificationBox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                notificationImage.setImage(FileHandler.getImage("notification").getImage());
            }
        });
        notificationBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GraphicAgent.notificationPage.main();
            }
        });

        VBox res = new VBox(Properties.loadSize("medium-spacing"),homeBox, messengerBox, listBox, searchBox, personalBox, notificationBox,settingBox);
        res.setPadding(new Insets(
                Properties.loadSize("small-indent"), Properties.loadSize("big-indent"),
                Properties.loadSize("small-indent"), Properties.loadSize("big-indent")));
        res.setPrefWidth(Properties.loadSize("menu-panel-size"));
        res.setAlignment(Pos.BASELINE_CENTER);
        return res;
    }
}
