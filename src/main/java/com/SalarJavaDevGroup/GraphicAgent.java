package com.SalarJavaDevGroup;

import com.SalarJavaDevGroup.GraphicPage.*;
import com.SalarJavaDevGroup.LogicalAgent.ServerAgent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class GraphicAgent {
    public static AuthPage authPage = new AuthPage();
    public static ServerAgent serverAgent = new ServerAgent();
    public static String username, password;
    public static MainPage mainPage = new MainPage();
    public static MessengerPage messengerPage = new MessengerPage();
    public static Stage stage;
    public static CommentsPage commentsPage = new CommentsPage();
    public static Scene scene;
    public static GroupsPage groupsPage = new GroupsPage();
    public static Explorer explorer = new Explorer();
    public static PersonalPage personalPage = new PersonalPage();
    public static SettingPage settingPage = new SettingPage();
    public static EditProfilePage editProfilePage = new EditProfilePage();
    public static EditPrivacy editPrivacy = new EditPrivacy();
    public static NotificationPage notificationPage = new NotificationPage();
    public static BlackList blackList = new BlackList();
}
