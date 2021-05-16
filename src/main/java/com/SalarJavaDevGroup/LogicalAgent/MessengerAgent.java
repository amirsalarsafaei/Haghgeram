package com.SalarJavaDevGroup.LogicalAgent;

import com.SalarJavaDevGroup.FileHandling.FileHandler;
import com.SalarJavaDevGroup.FileHandling.Load;
import com.SalarJavaDevGroup.GraphicAgent;
import com.SalarJavaDevGroup.Models.Conversation;
import com.SalarJavaDevGroup.Models.Message;
import com.SalarJavaDevGroup.Models.Tweet;
import com.SalarJavaDevGroup.Models.User;
import com.SalarJavaDevGroup.util.Compare;
import com.SalarJavaDevGroup.util.Convertor;
import com.SalarJavaDevGroup.util.Filter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class MessengerAgent {
    private static final Logger logger = LogManager.getLogger(MessengerAgent.class);
    public ArrayList<Conversation> getConversation(String username, String password) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return null;
        }
        User user = Load.LoadUser(username);
        ArrayList<Conversation> conversations = Convertor.ConvertConversations(user.getConversations());
        Collections.sort(conversations, new Compare.compare_conversations(user.getUsername()).reversed());
        conversations.add(0, Load.LoadConversation(user.getSavedMessage()));
        return conversations;
    }

    public void sendMessage(String username, String password, String content, int conv ) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        User user = Load.LoadUser(username);
        if (!Filter.boolFind(user.getConversations(), conv) && conv != user.getSavedMessage()) {
            logger.error("user not in conversation");
            return;
        }
        Load.LoadConversation(conv).getMessages().add(new Message(username, content));
        Load.LoadConversation(conv).save();
    }


    public void sendMessage(String username, String password, Tweet tweet, int conv ) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        User user = Load.LoadUser(username);
        if (!Filter.boolFind(user.getConversations(), conv) && conv != user.getSavedMessage()) {
            logger.error("user not in conversation");
            return;
        }
        Load.LoadConversation(conv).getMessages().add(new Message(username, "",tweet.getId()));
        Load.LoadConversation(conv).save();
    }

    public void sendMessage(String username, String password, String content, int conv, File file ) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        User user = Load.LoadUser(username);
        if (!Filter.boolFind(user.getConversations(), conv) && conv != user.getSavedMessage()) {
            logger.error("user not in conversation");
            return;
        }
        Message message = new Message(username, content);
        if (file != null) {
            message.hasImage = true;
            FileHandler.saveImage(file, message.id, "messagesImage");
        }
        Load.LoadConversation(conv).getMessages().add(message);
        Load.LoadConversation(conv).save();
        logger.info("message from " + username + " to "+ conv );
    }

    public void sendMessage(String username, String password, String targetUser, Tweet tweet) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        if (!GraphicAgent.serverAgent.profileAgent.userExist(targetUser))
            return ;
        if (!GraphicAgent.serverAgent.profileAgent.Follows(username, password, targetUser))
            return ;
        User target = Load.LoadUser(targetUser);
        for (int convInd : target.getConversations()) {
            Conversation conv = Load.LoadConversation(convInd);
            if (conv == null) {
                logger.error("conversation not found");
            }
            if (conv.getName(username).equals(targetUser)) {
                conv.getMessages().add(new Message(username, "", tweet.getId()));
                conv.save();
                logger.info("message from " + username + " to "+ targetUser );
                return;
            }
        }
        ArrayList<String> users = new ArrayList<>();
        users.add(username);
        users.add(targetUser);
        Conversation conv = new Conversation(users);
        conv.addMessage(new Message(username, "", tweet.getId()));
        conv.save();
        User user = Load.LoadUser(username);
        user.getConversations().add(conv.getId());
        target.getConversations().add(conv.getId());
        user.save();
        target.save();
        logger.info("new conversation created " + conv.getId());
        logger.info("message from " + username + " to "+ targetUser );
    }


    public void deleteMessage(String username, String password, int MessageId, int ConvId) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        Conversation conversation = Load.LoadConversation(ConvId);
        if (!Filter.boolFind(conversation.getUsers(), username)) {
            logger.error("user " + username + " doesn't have access to conversation " + ConvId);
            return;
        }
        for (Message message: conversation.getMessages())
            if (message.getId() == MessageId) {
                conversation.getMessages().remove(message);
                conversation.save();
                logger.info("Message " + message.getId() + " deleted from conv " + conversation.getId());
                return;
            }
        logger.error("message " + MessageId + " not found in " + ConvId);
    }

    public void editMessage(String username, String password, int messageId, int convId, String content) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        Conversation conversation = Load.LoadConversation(convId);
        if (!Filter.boolFind(conversation.getUsers(), username)) {
            logger.error("user " + username + " doesn't have access to conversation " + convId);
            return;
        }
        for (Message message: conversation.getMessages())
            if (message.getId() == messageId) {
                message.setContent(content);
                conversation.save();
                logger.info("Message " + message.getId() + " edited from conv " + conversation.getId());
                return;
            }
        logger.error("message " + messageId + " not found in " + convId);
    }
    public void makeGroup(String username, String password, ArrayList<String> users, String name) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        for (String userInGroup : users) {
            if (Load.LoadUser(userInGroup) == null) {
                logger.error(username + " sent an invalid request to make group chat user in group" + userInGroup +
                        "doesn't exist");
                return;
            }
        }
        Conversation conversation = new Conversation(users, name);
        logger.info(username + " made group chat " + name);
        for (String userInGroup : users) {
            logger.info(userInGroup + " added to group chat " + name + " by " + username);
            User user = Load.LoadUser(userInGroup);
            user.getConversations().add(conversation.getId());
            user.save();
        }

    }

}
