package com.SalarJavaDevGroup.LogicalAgent;

import com.SalarJavaDevGroup.FileHandling.Delete;
import com.SalarJavaDevGroup.FileHandling.Load;
import com.SalarJavaDevGroup.FileHandling.Save;
import com.SalarJavaDevGroup.GraphicAgent;
import com.SalarJavaDevGroup.Models.*;
import com.SalarJavaDevGroup.util.Compare;
import com.SalarJavaDevGroup.util.Convertor;
import com.SalarJavaDevGroup.util.Filter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class PersonalAgent {
    private static final Logger logger = LogManager.getLogger(PersonalAgent.class);
    public ArrayList<Tweet> TimeLine(String username, String password) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return null;
        }
        User user = Load.LoadUser(username);
        ArrayList<Integer> hp = new ArrayList<>();
        for (String tmp : user.getFollowing()) {
            User tmpUser = Load.LoadUser(tmp);
            if (tmpUser == null) {
                continue;
            }
            for (int tmp2 : tmpUser.getLikes())
                if (!Filter.boolFind(hp, tmp2) && canSeeTweet(user ,tmp2))
                    hp.add(tmp2);
            for (int tmp2 : tmpUser.getTweets())
                if (!Filter.boolFind(hp, tmp2) && canSeeTweet(user, tmp2))
                    hp.add(tmp2);
        }
        hp.addAll(user.getTweets());
        ArrayList<Tweet> tmp = Convertor.ConvertTwitter(hp);
        Collections.sort(tmp, Compare.compare_tweet_by_date.reversed());
        return tmp;
    }

    public ArrayList<String> getFollowing(String username, String password) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return new ArrayList<>();
        }
        return Load.LoadUser(username).getFollowing();
    }
    public ArrayList<Tweet> Discovery(String username, String password) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return new ArrayList<>();
        }
        User user = Load.LoadUser(username);
        ArrayList<Tweet> res = new ArrayList<>();
        for (int i = Load.Id("tweets") ; i >= 0; i--) {
            if (Load.LoadTweet(i) != null && canSeeTweet(user, i))
                res.add(Load.LoadTweet(i));
        }
        return res;
    }

    private boolean canSeeTweet(User user, int id) {
        Tweet tweet = Load.LoadTweet(id);
        if (tweet == null)
            return false;
        if (tweet.getReportedBy().size() > 5)
            return false;
        User user2 = Load.LoadUser(tweet.getUser());
        if (user2 == user)
            return true;
        if (user2 == null)
            return false;
        if (!user2.getActive())
            return false;
        if (user2.hasBlocked(user.getUsername()))
            return false;
        if (user2.isPrivate() && !user.isFollowing(user2.getUsername()))
            return false;
        if (user.hasMuted(user2.getUsername()))
            return false;
        if (tweet.Retweeted != -1)
            return canSeeTweet(user, tweet.Retweeted);
        return true;
    }
    public ArrayList<Tweet> selfTweets(String username, String password) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return new ArrayList<>();
        }
        ArrayList<Tweet> tmp = Convertor.ConvertTwitter(Load.LoadUser(username).getTweets());
        Collections.sort(tmp, Compare.compare_tweet_by_date.reversed());
        return tmp;
    }

    public ArrayList<String> getFollowers(String username, String password) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return new ArrayList<>();
        }
        return Load.LoadUser(username).getFollowers();
    }
    public Tweet LoadTweet(String username, String password, int tweetId) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return null;
        }
        if (Load.LoadTweet(tweetId) == null)
            return null;
        if (canSeeTweet(Load.LoadUser(username), tweetId))
            return Load.LoadTweet(tweetId);
        return null;
    }
    public String getName(String username, String password) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return "";
        }
        return Load.LoadUser(username).getName();
    }


    public String getFamilyName(String username, String password) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return "";
        }
        return Load.LoadUser(username).getFamilyName();
    }

    public String getBio(String username, String password) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return "";
        }
        return Load.LoadUser(username).getBio();
    }


    public LocalDate getBirthDate(String username, String password) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return null;
        }
        return Load.LoadUser(username).getBirthDate();
    }

    public String getEmail(String username, String password) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return "";
        }
        return Load.LoadUser(username).getEmail();
    }

    public String getPhone(String username, String password) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return "";
        }
        return Load.LoadUser(username).getPhoneNumber();
    }

    public void updateName(String username, String password, String name) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        User user = Load.LoadUser(username);
        logger.info(username + " updated name ");
        user.setName(name);
        user.save();
    }

    public void updateFamilyName(String username, String password, String FamilyName) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        User user = Load.LoadUser(username);
        user.setFamilyName(FamilyName);
        logger.info(username + " updated family name ");
        user.save();
    }

    public void updateEmail(String username, String password, String email) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        User user = Load.LoadUser(username);
        logger.info(username + " updated email ");
        user.setEmail(email);
        user.save();
    }

    public void updatePhone(String username, String password, String phone) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        User user = Load.LoadUser(username);
        logger.info(username + " updated phone ");
        user.setPhoneNumber(phone);
        user.save();
    }

    public void updatePassword(String username, String password, String ToPassword) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        User user = Load.LoadUser(username);
        logger.info(username + " updated password ");
        user.setPassword(ToPassword);
        user.save();
    }

    public void updateBio(String username, String password, String Bio) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        User user = Load.LoadUser(username);
        user.setBio(Bio);
        logger.info(username + " updated bio ");
        user.save();
    }
    public void updateBirthDate(String username, String password,LocalDate localDate) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        logger.info(username + " updated birthdate");
        User user = Load.LoadUser(username);
        user.setBirthDate(localDate);
        user.save();
    }

    public void updateProfile(String username, String password, File file) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        if (file == null)
            return;
        User user = Load.LoadUser(username);
        logger.info(username + " updated profile pic ");
        Save.saveProfilePicture(file, user.getUsername());
        user.save();
    }
    public LastSeen getLastSeen(String username, String password) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return null;
        }
        return Load.LoadUser(username).lastSeenSetting;
    }

    public void setLastSeen(String username, String password, LastSeen lastSeen) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        logger.info(username + " change last seen settings");
        Load.LoadUser(username).lastSeenSetting = lastSeen;
        Load.LoadUser(username).save();
    }

    public boolean getPrivate(String username, String password) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return false;
        }
        return Load.LoadUser(username).privateAccount;
    }

    public void setPrivate(String username, String password, boolean isPrivate) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        logger.info(username + " updated private ");
        Load.LoadUser(username).privateAccount = isPrivate;
        Load.LoadUser(username).save();
    }

    public void deleteAccount(String username, String password) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        User user = Load.LoadUser(username);
        for (String target: user.getFollowing()) {
            User targetUser = Load.LoadUser(target);
            if (targetUser == null) {
                continue;
            }
            Filter.boolFind(targetUser.getFollowers(), username);
            targetUser.save();
        }
        for (String target: user.getFollowers()) {
            User targetUser = Load.LoadUser(target);
            if (targetUser == null) {
                continue;
            }
            Filter.boolFind(targetUser.getFollowing(), username);
            for (Group group : targetUser.getGroups())
                Filter.delFind(group.getUsers(), username);
            targetUser.save();
        }
        for (int conv : user.getConversations()) {
            Conversation conversation = Load.LoadConversation(conv);
            if (conversation == null) {
                continue;
            }
            Filter.delFind(conversation.getUsers(), username);
            conversation.save();
        }

        for (int t : user.getTweets()) {
            Tweet tweet = Load.LoadTweet(t);
            if (tweet == null)
                continue;
            tweet.Content = "This tweet has been deleted";
            tweet.hasImage = false;
            tweet.Retweeted = -1;
            tweet.from = "Deleted Account";
            tweet.save();
        }

        for (String target : user.getBlockedBy()) {
            User targetUser = Load.LoadUser(target);
            if (targetUser == null)
                continue;
            Filter.delFind(targetUser.getBlackList(), username);
            targetUser.save();
        }

        for (String target : user.getBlackList()) {
            User targetUser = Load.LoadUser(target);
            if (targetUser == null)
                continue;
            Filter.delFind(targetUser.getBlockedBy(), username);
            targetUser.save();
        }

        for (int c : user.getComments()) {
            Comment comment = Load.LoadComment(c);
            comment.Content = "This Comment has been deleted";
            comment.from = "Deleted Account";
            comment.hasImage = false;
            comment.Retweeted = -1;
            comment.save();
        }

        for (int t : user.getLikes()) {
            Tweet tweet = Load.LoadTweet(t);
            if (tweet == null)
                continue;
            Filter.delFind(tweet.Likes, username);
            tweet.save();
        }
        user.save();
        Delete.delUser(user);
        logger.info(username + " deleted account.");
    }

    public ArrayList<String> getRequests(String username, String password) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return new ArrayList<>();
        }
        return Load.LoadUser(username).getFollowRequests();
    }
    public void saveProfilePicture(File selectedFile, String username) {
        saveProfilePicture(selectedFile, username);
    }

    public void acceptAllRequests(String username, String password) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        User user = Load.LoadUser(username);
        for (String target: user.getFollowRequests())
            GraphicAgent.serverAgent.notificationAgent.accept_request(username, password,target);
        user.save();
    }

    public void setActivity(String username, String password, boolean activity) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        User user = Load.LoadUser(username);
        user.setIsActive(activity);
        user.save();
    }

    public ArrayList<String> getBlocked(String username, String password) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return new ArrayList<>();
        }
        return Load.LoadUser(username).getBlackList();
    }

    public ArrayList<String> getAccepted(String username, String password) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return new ArrayList<>();
        }
        return Load.LoadUser(username).Accepted;
    }

    public ArrayList<String> getRejected(String username, String password) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return new ArrayList<>();
        }
        return Load.LoadUser(username).Denied;
    }

    public ArrayList<String> getPending(String username, String password) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return new ArrayList<>();
        }
        return Load.LoadUser(username).Pending;
    }

}
