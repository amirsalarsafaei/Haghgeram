package com.SalarJavaDevGroup.Models;

import com.SalarJavaDevGroup.util.ConsoleColors;
import com.SalarJavaDevGroup.util.DateFormatter;
import com.SalarJavaDevGroup.FileHandling.Load;
import com.SalarJavaDevGroup.FileHandling.Save;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
//
public class Tweet {
    int id;
    ArrayList<String> reportedBy = new ArrayList<>();
    public String from;
    public String Content;
    public boolean hasImage = false;
    public ArrayList<String> Likes;
    public int Retweeted;
    ArrayList<Integer> Comments;
    ArrayList<Integer> Retweets;
    public LocalDateTime Created;
    Boolean deleted;
    //Makes a tweet
    public Tweet(String from, String Content, Boolean save) {
        this.Content = Content;
        this.from = from;
        Comments = new ArrayList<>();
        Retweets = new ArrayList<>();
        Likes = new ArrayList<>();
        id = Load.nextId("tweets");
        Retweeted = -1;
        Created = LocalDateTime.now();
        if (save)
            save();
        deleted = false;
    }
    //Makes a retweet
    public Tweet(String from, String Content, int tweet) {
        this.from = from;
        this.Content = Content;
        Comments = new ArrayList<>();
        Retweets = new ArrayList<>();
        Likes = new ArrayList<>();
        id = Load.nextId("tweets");
        Created = LocalDateTime.now();
        Retweeted = tweet;
        save();
        deleted = false;
    }
    public int getId() {
        return id;
    }

    //shows only the tweet itself and nothing else
    public void viewSelf(PrintStream print) {
        print.println(ConsoleColors.BLACK_BOLD_BRIGHT + from + ":");
        print.println(ConsoleColors.BLACK + Content);
    }

    public ArrayList<Integer> getComments() {
        return Comments;
    }

    //adds comment to tweet
    public void addComment(Comment comment) {
        Comments.add(comment.getId());
        save();
    }

    public String getUser() {
        return from;
    }
    //saves tweet
    public void save() {
        Save.save(this, "tweets", id);
    }

    public LocalDateTime getCreatedDate() {
        return Created;
    }
    //tweet got retweeted
    public void addRetweet(int id) {
        Retweets.add(id);save();
    }

    public ArrayList<String> getReportedBy() {
        return reportedBy;
    }
}
