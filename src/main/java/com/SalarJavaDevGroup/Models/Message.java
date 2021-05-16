package com.SalarJavaDevGroup.Models;

import com.SalarJavaDevGroup.FileHandling.Load;
import com.SalarJavaDevGroup.util.ConsoleColors;
import com.SalarJavaDevGroup.util.DateFormatter;
import com.SalarJavaDevGroup.util.Filter;
import javafx.scene.image.Image;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Message {
    public String sender;
    public String Content;
    public LocalDateTime timeSent;
    public int tweet, id;
    public ArrayList<String> Read;
    public String imageName;
    public boolean hasImage = false;
    //constructor if message includes tweet
    public Message(String sender, String Content, int tweet) {
        this.sender = sender;
        this.Content = Content;
        this.tweet = tweet;
        timeSent = LocalDateTime.now();
        Read = new ArrayList<>();
        Read.add(sender);
        id = Load.nextId("messages");
        System.out.println(id);
    }
    //constructor if it doesn't message includes tweet
    public Message(String sender, String Content) {
        this.sender = sender;
        this.Content = Content;
        this.tweet = -1;
        timeSent = LocalDateTime.now();
        Read = new ArrayList<>();
        Read.add(sender);
        id = Load.nextId("messages");
    }
    //makes a message from another message
    //shows message in cli
    public LocalDateTime getDate() {
        return timeSent;
    }

    //checks if a user has read this message
    public boolean hasRead(String Person) {
        return Filter.boolFind(Read, Person);
    }
    //adds a user to the readers in this message
    public void read(String username) {
        if (!Filter.boolFind(Read, username))
            Read.add(username);
    }

    public String getPreview() {
        if (Content.length() > 0)
            return sender + ": " + Content;
        if (imageName != null)
            return sender + " Sent a Photo";
        if (Load.LoadTweet(tweet) != null)
            return sender + " Sent a Tweet";
        return "";
    }

    public int getId() {
        return id;
    }

    public void setContent(String content) {
        this.Content = content;
    }
}

