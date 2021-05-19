package com.SalarJavaDevGroup.LogicalAgent;

import com.SalarJavaDevGroup.FileHandling.Delete;
import com.SalarJavaDevGroup.FileHandling.FileHandler;
import com.SalarJavaDevGroup.FileHandling.Load;
import com.SalarJavaDevGroup.FileHandling.Save;
import com.SalarJavaDevGroup.Models.*;
import com.SalarJavaDevGroup.util.Compare;
import com.SalarJavaDevGroup.util.Convertor;
import com.SalarJavaDevGroup.util.DateFormatter;
import com.SalarJavaDevGroup.util.Filter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class ServerAgent {
    private static final Logger logger = LogManager.getLogger(ServerAgent.class);
    public TweetAgent tweetAgent = new TweetAgent();
    public ProfileAgent profileAgent = new ProfileAgent();
    public PersonalAgent personalAgent = new PersonalAgent();
    public NotificationAgent notificationAgent = new NotificationAgent();
    public MessengerAgent messengerAgent = new MessengerAgent();
    public GroupAgent groupAgent = new GroupAgent();
    public AuthAgent authAgent = new AuthAgent();
    public void setOnline(String username, String password) {
        if (!"success".equals(authAgent.login(username, password)))
            return;
        Load.LoadUser(username).setLastOnline(LocalDateTime.now());
        Load.LoadUser(username).save();
    }
}
