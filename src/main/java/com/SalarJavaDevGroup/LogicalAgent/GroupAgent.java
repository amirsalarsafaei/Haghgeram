package com.SalarJavaDevGroup.LogicalAgent;

import com.SalarJavaDevGroup.FileHandling.Load;
import com.SalarJavaDevGroup.GraphicAgent;
import com.SalarJavaDevGroup.Models.Group;
import com.SalarJavaDevGroup.Models.User;
import com.SalarJavaDevGroup.util.Filter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class GroupAgent {
    private static final Logger logger = LogManager.getLogger(GroupAgent.class);
    public ArrayList<Group> getGroups(String username, String password) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            logger.error("UnAuthorized " + username);
            return null;
        }
        return Load.LoadUser(username).getGroups();
    }

    public void deleteGroup(String username, String password, String targetGroup) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            logger.error("UnAuthorized " + username);
            return ;
        }

        User user = Load.LoadUser(username);
        for (Group group: user.getGroups()) {
            if (group.getName().equals(targetGroup)) {
                user.getGroups().remove(group);
                user.save();
                logger.info(username + " removed list " + targetGroup);
                return;
            }
        }
        logger.error(username+ " invalid request to delete group " + targetGroup);
    }

    public boolean existGroup(String username, String password, String name) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            logger.error("UnAuthorized " + username);
            return true;
        }
        for (Group group:getGroups(username, password))
            if (group.getName().equals(name))
                return true;
        return false;
    }

    public void addGroup(String username, String password, String name) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            logger.error("UnAuthorized " + username);
            return ;
        }
        User user = Load.LoadUser(username);
        if (existGroup(username, password, name)) {
            logger.error(username + " sent a invalid request to add a group that already exist group " + name);
        }
        user.getGroups().add(new Group(name));
        user.save();
    }
    public void AddToGroup(String username, String password, String groupName, ArrayList<String> res) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            logger.error("UnAuthorized " + username);
            return ;
        }
        ArrayList<Group> groups = Load.LoadUser(username).getGroups();
        for (Group group: groups)
            if (group.getName().equals(groupName)) {
                group.setUsers(res);
                Load.LoadUser(username).save();
                logger.info("Group updated : " + groupName);
                return;
            }
        logger.error("group " + groupName + " not Found");
    }

    public void deleteUserFromGroup(String username, String password, String groupName, String user) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            logger.error("UnAuthorized " + username);
            return ;
        }
        ArrayList<Group> groups = Load.LoadUser(username).getGroups();
        for (Group group: groups)
            if (group.getName().equals(groupName)) {
                Filter.delFind(group.getUsers(), user);
                Load.LoadUser(username).save();
                return;
            }

        logger.error("User " + user + " in group " + groupName + " not found to remove ");
    }
}
