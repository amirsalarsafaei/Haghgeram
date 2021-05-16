package com.SalarJavaDevGroup.Models;

import com.SalarJavaDevGroup.FileHandling.Load;
import com.SalarJavaDevGroup.FileHandling.Save;
import com.SalarJavaDevGroup.util.Filter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;

//Conversations which holds messages
public class Conversation {
    int id;
    private static final Logger logger = LogManager.getLogger(Conversation.class);
    ArrayList<String> users;
    ArrayList<Message> messages;
    String Name;
    public Conversation(ArrayList<String> users) {
        messages = new ArrayList<>();
        this.users = users;
        id = Load.nextId("conversations");
        Name = "";
        save();

    }

    public Conversation(ArrayList<String> users, String name) {
        messages = new ArrayList<>();
        this.users = users;
        id = Load.nextId("conversations");
        Name = name;
        save();
    }

    //adds a user to the conversation that user can see all chats
    public void addUser(String user) {
        logger.info("user " + user + " added to conv ");
        if (!Filter.boolFind(users, user))
            users.add(user);
    }

    //checks if a user is in chat
    public Boolean inChat(String person) {
        return Filter.boolFind(users, person);
    }


    //returns number of unread message for a user
    public int unread(String person) {
        int cnt = 0;
        for (Message message:messages)
            if (!message.hasRead(person))
                cnt++;
        return cnt;
    }

    //gets The other user name to show
    public String getName(String username) {
        if (!Name.equals(""))
            return Name;
        if (users.size() == 1)
            return "Saved Messages";
        if (users.get(0).equals(username))
            return users.get(1);
        return users.get(0);
    }
    //get last date of any  message sent
    public LocalDateTime getLastDate() {
        if (messages.size() == 0) {
            logger.error("Conversation is empty");//add users later
            return LocalDateTime.now();
        }
        LocalDateTime res = messages.get(0).getDate();
        for (int i = 1; i < messages.size(); i++)
            if (res.compareTo(messages.get(i).getDate()) < 0)
                res = messages.get(i).getDate();
        return res;
    }


    public int getId() {
        return id;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    //adds message
    public void addMessage(Message message) {
        messages.add(message);
        save();
    }
    //saves conversation
    public void save() {
        Save.save(this);
    }
    //deletes conversation
    public String getLastMessage() {
        if (messages.size() == 0)
            return "";
        return messages.get(messages.size()-1).getPreview();
    }

    public boolean isGroup() {
        return users.size() > 2;
    }

    public boolean isSavedMessage() {
        return users.size() == 1;
    }


    public ArrayList<String> getUsers() {
        return users;
    }

}
