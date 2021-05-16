package com.SalarJavaDevGroup.FileHandling;

import com.SalarJavaDevGroup.Models.Comment;
import com.SalarJavaDevGroup.Models.Conversation;
import com.SalarJavaDevGroup.Models.Tweet;
import com.SalarJavaDevGroup.Models.User;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;





public abstract class Load {
    private static final Logger logger = LogManager.getLogger(Load.class);

    public static ArrayList<User> openUsers = new ArrayList<>();
    public static ArrayList<Tweet> openTweets = new ArrayList<>();
    public static ArrayList<Comment> openComments = new ArrayList<>();
    public static ArrayList<Conversation> openConversations = new ArrayList<>();
    // getting the user or checking if it doesn't exist
    // if the user doesn't exist the return will be null
    public static User LoadUser(String username) {
        //if user is open it will just return it
        for (User user: openUsers)
            if (user.getUsername().equals(username))
                return user;
        File dir = FileHandler.loadFile("users.directory");

        if (dir != null) {
            try {
                File userFile = new File(dir.getPath() + "/" + username + "/user.data");
                if (!userFile.exists())
                    return null;
                Reader reader = new FileReader(userFile.getPath());
                User user = GsonHandler.getGson().fromJson(reader, User.class);
                reader.close();
                openUsers.add(user);
                return user;
            } catch (IOException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
                logger.error("couldn't load User" + username + " File!");
            }
        }
        else {
            logger.fatal("users Dir doesnt exist!");
        }
        return null;
    }
    // getting the Tweet or checking if it doesn't exist
    // if the Tweet doesn't exist the return will be null
    public static Tweet LoadTweet(int id) {
        //if tweet is open it will just return it
        for (Tweet tweet: openTweets)
            if (tweet.getId() == id)
                return tweet;
        if (LoadComment(id) != null)
            return LoadComment(id);
        File dir = FileHandler.loadFile(  "tweets.directory");
        if (dir != null) {
            try {
                File userFile = new File(dir.getPath() + "/" + id);
                if (!userFile.exists())
                    return null;
                Reader reader = new FileReader(userFile.getPath());
                Tweet tweet = GsonHandler.getGson().fromJson(reader, Tweet.class);
                openTweets.add(tweet);
                reader.close();
                return tweet;
            } catch (IOException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
                logger.error("couldn't load tweet " + id + " File!");
            }
        }
        else {
            logger.fatal("tweets Dir doesnt exist!");
        }
        return null;
    }
    // getting the Comment or checking if it doesn't exist
    // if the Comment doesn't exist the return will be null
    public static Comment LoadComment(int id) {
        //if comment is open it will just return it
        for (Comment comment: openComments)
            if (comment.getId() == id)
                return comment;
        File dir = FileHandler.loadFile(  "comments.directory");
        if (dir != null) {
            try {
                File commentFile = new File(dir.getPath() + "/" + id);
                if (!commentFile.exists())
                    return null;
                Reader reader = new FileReader(commentFile.getPath());
                Comment comment = GsonHandler.getGson().fromJson(reader, Comment.class);
                openComments.add(comment);
                reader.close();
                return comment;
            } catch (IOException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
                logger.error("couldn't load comment " + id + " File!");
            }
        }
        else {
            logger.fatal("comments Dir doesnt exist!");
        }
        return null;
    }

    public static Conversation LoadConversation(int id) {
        //if user is open it will just return it
        for (Conversation conv: openConversations)
            if (conv.getId() == id)
                return conv;
        File dir = FileHandler.loadFile("conversations.directory");

        if (dir != null) {
            try {
                File convFile = new File(dir.getPath() + "/" + id + "/conv.data");
                if (!convFile.exists())
                    return null;
                Reader reader = new FileReader(convFile.getPath());
                Conversation conv = GsonHandler.getGson().fromJson(reader, Conversation.class);
                reader.close();
                openConversations.add(conv);
                return conv;
            } catch (IOException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
                logger.error("couldn't load Conv " + id + " File!");
            }
        }
        else {
            logger.fatal("users Dir doesnt exist!");
        }
        return null;
    }


    //getting some property for new object
    public static int nextId(String key) {
        //Setting the id to number of users created + 1 and clearing id file
        try {
            File file = FileHandler.loadFile(key + ".id");
            InputStream input = new FileInputStream(file);
            Scanner scanner = new Scanner(input);
            int id = scanner.nextInt() + 1;
            scanner.close();
            input.close();
            FileHandler.ClearFile(file);
            //updating id file with new numbers
            PrintStream printStream = new PrintStream(new FileOutputStream(file));
            printStream.println(id);
            printStream.close();
            return id;
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            logger.fatal(key + "id file doesnt exist.");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("couldn't read from "  + key + " id file.");
        }
        return -1;
    }


    public static int Id(String key) {
        //Setting the id to number of users created + 1 and clearing id file
        try {
            File file = FileHandler.loadFile(key + ".id");
            InputStream input = new FileInputStream(Objects.requireNonNull(file));
            Scanner scanner = new Scanner(input);
            int id = scanner.nextInt();
            scanner.close();
            input.close();
            return id;
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            logger.fatal(key + "id file doesnt exist.");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("couldn't read from "  + key + " id file.");
        }
        return 0;
    }
    public static ImageView LoadUserProfileImage(String username) {
        File file = new File(FileHandler.loadFile("users.directory").getPath() + "/" + username +
                "/profile-image");
        if (!file.exists())
            return FileHandler.getImage("no-profile");
        FileInputStream inputImage = null;
        try {
            inputImage = new FileInputStream(file);
            Image image = new Image(inputImage);
            ImageView imageView = new ImageView(image);
            inputImage.close();
            return imageView;
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return null;
    }


}
