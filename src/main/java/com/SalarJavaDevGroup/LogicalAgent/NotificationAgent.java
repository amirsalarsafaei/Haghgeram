package com.SalarJavaDevGroup.LogicalAgent;

import com.SalarJavaDevGroup.FileHandling.Load;
import com.SalarJavaDevGroup.FileHandling.Properties;
import com.SalarJavaDevGroup.GraphicAgent;
import com.SalarJavaDevGroup.Models.User;
import com.SalarJavaDevGroup.util.Filter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class NotificationAgent {
    private static final Logger logger = LogManager.getLogger(NotificationAgent.class);
    public void accept_request(String username, String password, String target) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        if (Load.LoadUser(target) == null)
            return;
        User user = Load.LoadUser(username), targetUser = Load.LoadUser(target);

        if (!Filter.boolFind(user.getFollowRequests(), target)) {
            logger.error(username + " sent invalid accept request on " + target);
            return;
        }
        Filter.delFind(user.getFollowRequests(), target);
        Filter.delFind(targetUser.getPending(), username);
        user.getFollowers().add(target);
        targetUser.getEvents().add(username + " " + Properties.loadDialog("accepted-request"));
        user.getEvents().add(username + " " + Properties.loadDialog("start-follow"));
        logger.info(username + " accepted " +target + " request");
        targetUser.getFollowing().add(target);
        targetUser.Accepted.add(username);
        targetUser.save();
        user.save();
    }


    public void reject_request(String username, String password, String target, boolean mute) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        if (Load.LoadUser(target) == null)
            return;
        User user = Load.LoadUser(username), targetUser = Load.LoadUser(target);

        if (!Filter.boolFind(user.getFollowRequests(), target)) {
            logger.error(username + " sent invalid reject request on " + target);
            return;
        }
        Filter.delFind(user.getFollowRequests(), target);
        Filter.delFind(targetUser.getPending(), username);
        if (!mute)
            targetUser.getEvents().add(username + " " + Properties.loadDialog("rejected-request"));
        targetUser.Denied.add(username);
        targetUser.save();
        logger.info(username + " rejected " +target + " request");
        user.save();
    }

    public ArrayList<String> getEvents(String username, String password) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return new ArrayList<>();
        }
        return Load.LoadUser(username).getEvents();
    }
}
