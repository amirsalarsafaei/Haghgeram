package com.SalarJavaDevGroup.Models;

import com.SalarJavaDevGroup.util.ConsoleColors;
import com.SalarJavaDevGroup.util.DateFormatter;
import com.SalarJavaDevGroup.FileHandling.FileHandler;
import com.SalarJavaDevGroup.FileHandling.Load;
import com.SalarJavaDevGroup.FileHandling.Save;
import com.SalarJavaDevGroup.util.Filter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.SalarJavaDevGroup.FileHandling.Load.LoadTweet;
import static com.SalarJavaDevGroup.FileHandling.Load.LoadUser;

public class User {
    private static final Logger logger = LogManager.getLogger(User.class);
    private String Password;
    public String Name;
    public String FamilyName;
    public String Username;
    private final int id;
    private String Email;
    private String PhoneNumber;
    private LocalDate BirthDate;
    public String Bio;
    private Boolean isActive;
    public LocalDateTime LastOnline;
    private final Boolean isOnline;
    private final ArrayList<Integer> Likes;
    private final ArrayList<Integer> tweets;
    private final ArrayList<Integer> comments;
    private final int SavedMessage;
    private final ArrayList<String> Following;
    private final ArrayList<String> Followers;
    private final ArrayList<String> Blocked;
    private final ArrayList<String> Muted;
    private final ArrayList<String> FollowRequests;
    private final ArrayList<String> Events;
    private final ArrayList<String> BlockedBy;
    private final ArrayList<Group> groups;
    public ArrayList<String> Pending, Denied, Accepted;
    private final ArrayList<Integer> Conversations;
    public Boolean privateAccount;
    public LastSeen lastSeenSetting;
    //Creates new User
    public User(
            String UserName,
            String Password,
            String Name,
            String FamilyName,
            LocalDate BirthDate,
            String Email,
            String PhoneNumber,
            String Bio,
            LocalDateTime lastOnline,
            Boolean isOnline,
            Boolean isActive
    ) {
        this.Email = Email;
        this.PhoneNumber = PhoneNumber;
        this.BirthDate = BirthDate;
        this.Name = Name;
        this.Password = Password;
        this.FamilyName = FamilyName;
        this.Username = UserName;
        this.Bio = Bio;
        this.LastOnline = lastOnline;
        this.isActive = isActive;
        this.isOnline = isOnline;
        comments = new ArrayList<>();
        tweets = new ArrayList<>();
        Likes = new ArrayList<>();
        Followers = new ArrayList<>();
        Following = new ArrayList<>();
        Blocked = new ArrayList<>();
        Muted = new ArrayList<>();
        ArrayList<String> tmp = new ArrayList<>();
        tmp.add(UserName);
        SavedMessage = new Conversation(tmp).id;
        Conversations = new ArrayList<>();
        FollowRequests = new ArrayList<>();
        groups = new ArrayList<>();
        Events = new ArrayList<>();
        BlockedBy = new ArrayList<>();
        Pending = new ArrayList<>();
        Accepted = new ArrayList<>();
        Denied = new ArrayList<>();
        id = Load.nextId("users");
        privateAccount = false;
        Save.saveUsername(UserName);
        if (Email.length() > 0)
            Save.saveEmail(Email);
        if (PhoneNumber.length() > 0)
            Save.savePhone(PhoneNumber);
        lastSeenSetting = LastSeen.Everyone;
        Save.save(this);
        logger.info("User " + id + " Created");
    }
    //Changing Password
    public void setPassword(String password) {
        logger.info("User " + getUsername() + " Password changed");
        Password = password;
        save();
    }
    //changing username
    public void setUsername(String username) {
        Username = username;
        logger.info("User " + getUsername() + " Username changed");
        save();
    }
    //changing name
    public void setName(String name) {
        logger.info("User " + getUsername() + " Name changed");
        this.Name = name;
        save();
    }
    //changing family name
    public void setFamilyName(String familyName) {
        logger.info("User " + getUsername() + " Family Name changed");
        this.FamilyName = familyName;
        save();
    }

    public String getEmail() {
        return Email;
    }
    //changing email
    public void setEmail(String email) {
        Email = email;
        logger.info("User " + getUsername() + " email changed");
        save();
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
        save();
    }

    public LocalDate getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(LocalDate date) {
        BirthDate = date;
        save();
    }
    public int getId() {
        return id;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public String getName() {
        return Name;
    }

    public String getFamilyName() {
        return FamilyName;
    }

    public String getBio() {
        return Bio;
    }

    public LocalDateTime getLastOnline() {
        return LastOnline;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public Boolean getActive() {
        return isActive;
    }


    public ArrayList<Integer> getTweets() {
        return tweets;
    }

    public void save() {
        Save.save(this);
        logger.info(Username + " updated (id : " + id + ")");
    }
    public void addTweet(Tweet tweet) {
        tweets.add(tweet.getId());
        logger.info("User " + id + " tweeted the tweet " + tweet.id);
        save();
    }

    public boolean hasMuted(String username) {
        return Filter.boolFind(Muted, username);
    }


    public Boolean isFollowing(String username) {
        return Filter.boolFind(Following, username);
    }


    public Boolean isPrivate() {
        return privateAccount;
    }


    public void setBio(String bio) {
        Bio = bio;
        logger.info(this.getUsername() + " changed his bio");
        save();
    }


    public ArrayList<Integer> getLikes() {
        return Likes;
    }

    public Boolean hasBlocked(String username) {
        return Filter.boolFind(Blocked, username);
    }

    public ArrayList<Integer> getConversations() {
        return Conversations;
    }

    public int getSavedMessage() {
        return SavedMessage;
    }

    public ArrayList<String> getBlackList() {
        return Blocked;
    }

    public ArrayList<String> getBlockedBy() {
        return BlockedBy;
    }

    public ArrayList<String> getFollowing() {
        return Following;
    }

    public ArrayList<String> getFollowers() {
        return Followers;
    }

    public void addEvent(String event) {
        Events.add(event);
        save();
    }

    public ArrayList<String> getFollowRequests() {
        return FollowRequests;
    }


    public ArrayList<String> getEvents() {
        return Events;
    }


    public ArrayList<String> getMuted() {return Muted; }


    public ArrayList<String> getPending() { return Pending; }

    public ArrayList<Integer> getComments() { return comments; }

    public ArrayList<Group> getGroups(){return groups;}

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public void setLastOnline(LocalDateTime now) {
        LastOnline = now;
    }
}
