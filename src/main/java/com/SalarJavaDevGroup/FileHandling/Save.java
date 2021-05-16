package com.SalarJavaDevGroup.FileHandling;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import com.SalarJavaDevGroup.Models.Conversation;
import com.SalarJavaDevGroup.Models.Tweet;
import com.SalarJavaDevGroup.Models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Save {
    private static final Logger logger = LogManager.getLogger(Save.class);

    //Save user into stream (usually the file)
    static void save(User user, PrintStream print) {
        GsonHandler.getGson().toJson(user, print);
        print.close();
    }

    //Finding and creating directory needed to save user
    public static void save(User user) {
        File dir = FileHandler.loadFile("users.directory");
        if (dir != null) {
            try {
                File userFile = new File(dir.getPath() + "/" + user.getUsername() + "/user.data");
                if (userFile.exists())
                    FileHandler.ClearFile(userFile);
                else {
                    if (!userFile.getParentFile().exists()) {
                        userFile.getParentFile().mkdir();
                        logger.info("user " + user.getId() + " folder Created");
                    }
                    userFile.createNewFile();
                    logger.info("User data file for user " + user.getId() + " created.");
                }
                save(user, new PrintStream(new FileOutputStream(userFile)));
            } catch (IOException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
                logger.error("couldn't save User " + user.getId() + " File!");
            }
        }
        else {
            logger.fatal("users Dir doesnt exist!");
        }
    }


    public static void save(Conversation conversation) {
        File dir = FileHandler.loadFile("conversations.directory");
        if (dir != null) {
            try {
                File userFile = new File(dir.getPath() + "/" + conversation.getId() + "/conv.data");
                if (userFile.exists())
                    FileHandler.ClearFile(userFile);
                else {
                    if (!userFile.getParentFile().exists()) {
                        userFile.getParentFile().mkdir();
                        logger.info("user " + conversation.getId() + " folder Created");
                    }
                    userFile.createNewFile();
                    logger.info("User data file for user " + conversation.getId() + " created.");
                }
                save(conversation, new PrintStream(new FileOutputStream(userFile)));
                logger.info("Saved Conversation" + conversation.getId());
            } catch (IOException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
                logger.error("couldn't save User " + conversation.getId() + " File!");
            }
        }
        else {
            logger.fatal("users Dir doesnt exist!");
        }
    }
    // saves any object
    static void save(Object obj, PrintStream print) {
        GsonHandler.getGson().toJson(obj, print);
        print.close();
    }

    //Finding and creating directory needed to save object
    public static void save(Object obj, String type, int id) {
        File dir = FileHandler.loadFile(type + ".directory");
        if (dir != null) {
            try {
                File userFile = new File(dir.getPath() + "/" + id);
                if (userFile.exists())
                    FileHandler.ClearFile(userFile);
                else {
                    if (!userFile.getParentFile().exists()) {
                        userFile.getParentFile().mkdir();
                        logger.info(type + " " + id + " folder Created");
                    }
                    userFile.createNewFile();
                    logger.info("User data file for " + type + " " + id + " created.");
                }
                save(obj, new PrintStream(new FileOutputStream(userFile)));
            } catch (IOException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
                logger.error("couldn't save " + type + " " + id + " File!");
            }
        }
        else {
            logger.fatal(type + " Dir doesnt exist!");
        }
    }

    //saving username to check in sign up
    public static void saveUsername(String username) {
        File usernameFile = FileHandler.loadFile("users.usernames");
        try {
            PrintStream print = new PrintStream(new FileOutputStream(usernameFile, true));
            print.println(username);
            print.close();
        } catch (IOException e) {
            logger.fatal("Usernames file not Found!");
            e.printStackTrace();
        }
    }
    //saving email to check in sign up
    public static void saveEmail(String email) {
        File emailFile = FileHandler.loadFile("users.emails");
        try {
            PrintStream print = new PrintStream(new FileOutputStream(emailFile, true));
            print.println(email);
            print.close();
        } catch (IOException e) {
            logger.fatal("emails file not Found!");
            e.printStackTrace();
        }
    }

    //saving phone to check in sign up
    public static void savePhone(String phone) {
        File phoneFile = FileHandler.loadFile("users.phones");
        try {
            PrintStream print = new PrintStream(new FileOutputStream(phoneFile, true));
            print.println(phone);
            print.close();
        } catch (IOException e) {
            logger.fatal("phones file not Found!");
            e.printStackTrace();
        }
    }

    //saves tweet
    public static void save(Tweet tweet) {
        File dir = FileHandler.loadFile("tweets.directory");
        if (dir != null) {
            try {
                File userFile = new File(dir.getPath() + "/" + tweet.getId());
                if (userFile.exists())
                    FileHandler.ClearFile(userFile);
                else {
                    userFile.createNewFile();
                    logger.info("tweet data file for tweet " + tweet.getId() + " created.");
                }
                save(tweet, new PrintStream(new FileOutputStream(userFile)));
            } catch (IOException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
                logger.error("couldn't save tweet " + tweet.getId() + " File!");
            }
        }
        else {
            logger.fatal("tweets Dir doesnt exist!");
        }
    }

    public static void saveProfilePicture(File file, String username) {
        try {
            File dest = new File(FileHandler.loadFile("users.directory").getPath() + "/" + username+
                    "/profile-image");
            Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            logger.info(username + " profile picture updated");
        } catch (IOException ioException) {
            ioException.printStackTrace();
            logger.error("couldn't save " + username + " profile pic");
        }
    }

}
