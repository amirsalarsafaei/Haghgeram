package com.SalarJavaDevGroup.LogicalAgent;

import com.SalarJavaDevGroup.FileHandling.Load;
import com.SalarJavaDevGroup.FileHandling.Properties;
import com.SalarJavaDevGroup.GraphicAgent;
import com.SalarJavaDevGroup.Models.Conversation;
import com.SalarJavaDevGroup.Models.LastSeen;
import com.SalarJavaDevGroup.Models.Message;
import com.SalarJavaDevGroup.Models.User;
import com.SalarJavaDevGroup.util.DateFormatter;
import com.SalarJavaDevGroup.util.Filter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;

public class ProfileAgent {
    private static final Logger logger = LogManager.getLogger(ProfileAgent.class);
    public String getProfileName(String username, String password, String targetUser) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return "not Found";
        }
        if (!userExist(targetUser))
            return "not Found";
        User user = Load.LoadUser(targetUser);
        return "@" + user.getUsername() + "(" + user.getName() + " " + user.getFamilyName() + ")";
    }
    public String getStatus(String username, String password, String targetUser) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return "not Found";
        }
        if (!userExist(targetUser))
            return "not Found";
        User user = Load.LoadUser(username);
        User targrtuser = Load.LoadUser(targetUser);
        if (!Filter.boolFind(user.getFollowing(), targrtuser.getUsername()) || user.lastSeenSetting == LastSeen.noOne)
            return "Last seen recently";
        if (user.lastSeenSetting == LastSeen.Followings && !Filter.boolFind(user.getFollowers(), targrtuser.getUsername()))
            return "Last seen recently";
        return DateFormatter.getDateBasic(user.LastOnline);
    }
    protected boolean userExist(String user) {
        return Load.LoadUser(user) != null;
    }

    public String getBio(String username, String password, String targetUser) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return "not Found";
        }
        if (!userExist(targetUser))
            return "not Found";
        return Load.LoadUser(targetUser).getBio();
    }

    public String getBirthDate(String username, String password, String targetUser) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return "not Found";
        }
        if (!userExist(targetUser))
            return "not Found";
        User target = Load.LoadUser(targetUser);
        LocalDate time = target.getBirthDate();
        if (time == null)
            return "";
        return time.getMonth().toString().toLowerCase() + " " + time.getDayOfMonth();
    }

    public boolean Follows(String username, String password, String targetUser) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return false;
        }
        if (!userExist(targetUser)) {
            return false;
        }
        User user = Load.LoadUser(username);
        return Filter.boolFind(user.getFollowing(), targetUser);
    }

    public boolean Muted(String username, String password, String targetUser) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return false;
        }
        if (!userExist(targetUser))
            return false;
        User user = Load.LoadUser(username);
        logger.info(username + " muted " + targetUser);
        return Filter.boolFind(user.getMuted(), targetUser);
    }

    public boolean Blocked(String username, String password, String targetUser) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return false;
        }
        if (!userExist(targetUser))
            return false;
        User user = Load.LoadUser(username);
        logger.info(username + " blocked " + targetUser);
        return Filter.boolFind(user.getBlackList(), targetUser);
    }

    public void toggleFollow(String username, String password, String targetUser) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        if (!userExist(targetUser))
            return ;
        User user = Load.LoadUser(username), target = Load.LoadUser(targetUser);
        if (Follows(username, password, targetUser)) {
            Filter.delFind(user.getFollowing(), targetUser);
            Filter.delFind(target.getFollowers(), username);
            target.getEvents().add(username + " " + Properties.loadDialog("stop-follow"));
            logger.info(username + " stopped following " + targetUser);
        }
        else {
            if (!target.isPrivate()) {
                user.getFollowing().add(targetUser);
                target.getFollowers().add(username);
                target.getEvents().add(username + " " + Properties.loadDialog("start-follow"));
                logger.info(username + " started following " + targetUser);
            }
            else {
                if (!Pending(username, password, targetUser)) {
                    target.getFollowRequests().add(username);
                    user.getPending().add(targetUser);
                    logger.info(username + " sent a follow request " + targetUser);
                }
            }
        }
        user.save();
        target.save();
    }

    public void toggleMute(String username, String password, String targetUser) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        if (!userExist(targetUser))
            return ;
        User user = Load.LoadUser(username), target = Load.LoadUser(targetUser);
        if (Muted(username, password, targetUser)) {
            Filter.delFind(user.getMuted(), targetUser);
            logger.info(username + " stopped muting " + targetUser);
        }
        else {
            user.getMuted().add(targetUser);
            logger.info(username + " started muting " + targetUser);
        }
        user.save();
    }

    public void toggleBlock(String username, String password, String targetUser) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        if (!userExist(targetUser))
            return ;

        User user = Load.LoadUser(username), target = Load.LoadUser(targetUser);
        Filter.delFind(user.getFollowing(), targetUser);
        Filter.delFind(target.getFollowers(), username);
        if (Blocked(username, password, targetUser)) {
            Filter.delFind(user.getBlackList(), targetUser);
            Filter.delFind(target.getBlockedBy(), username);
            logger.info(username + " stopped blocking " + targetUser);
        }
        else {
            user.getBlackList().add(targetUser);
            target.getBlockedBy().add(username);
            logger.info(username + " started blocking " + targetUser);
        }
        user.save();
        target.save();
    }
    public void sendMessageFromProfile(String username, String password, String targetUser, String message) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        if (!userExist(targetUser))
            return ;
        if (!Follows(username, password, targetUser))
            return ;
        User target = Load.LoadUser(targetUser);
        for (int convInd : target.getConversations()) {
            Conversation conv = Load.LoadConversation(convInd);
            if (conv == null) {
                logger.error("conversation not found");
            }
            if (conv.getName(username).equals(targetUser)) {
                conv.getMessages().add(new Message(username, message));
                conv.save();
                logger.info("message from " + username + " to "+ targetUser );
                return;
            }
        }
        ArrayList<String> users = new ArrayList<>();
        users.add(username);
        users.add(targetUser);
        Conversation conv = new Conversation(users);
        conv.addMessage(new Message(username, message));
        conv.save();
        User user = Load.LoadUser(username);
        user.getConversations().add(conv.getId());
        target.getConversations().add(conv.getId());
        user.save();
        target.save();
        logger.info("new conversation created " + conv.getId());
        logger.info("message from " + username + " to "+ targetUser );
    }
    public boolean Pending(String username, String password, String target) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return false;
        }
        if (Load.LoadUser(target) == null)
            return false;
        return Filter.boolFind(Load.LoadUser(username).getPending(), target);
    }
}
