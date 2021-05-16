package com.SalarJavaDevGroup.LogicalAgent;

import com.SalarJavaDevGroup.FileHandling.FileHandler;
import com.SalarJavaDevGroup.FileHandling.Load;
import com.SalarJavaDevGroup.GraphicAgent;
import com.SalarJavaDevGroup.Models.Comment;
import com.SalarJavaDevGroup.Models.Tweet;
import com.SalarJavaDevGroup.Models.User;
import com.SalarJavaDevGroup.util.Filter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class TweetAgent {
    private static final Logger logger = LogManager.getLogger(TweetAgent.class);
    public void toggleLike(String username, String password, Tweet tweet) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return;
        }
        User user = Load.LoadUser(username);
        for (int i = 0; i < tweet.Likes.size(); i++) {
            if (tweet.Likes.get(i).equals(user.getUsername())) {
                tweet.Likes.remove(i);
                Filter.delFind(user.getLikes(), tweet.getId());
                user.save();
                tweet.save();
                logger.info("User " + user.getId() + " disliked tweet " + tweet.getId() );
                return;
            }
        }
        user.getLikes().add(tweet.getId());
        tweet.Likes.add(user.getUsername());
        user.save();
        tweet.save();
        logger.info("User " + user.getId() + " and tweet " + tweet.getId() + " Likes updated");
        logger.info("User " + user.getId() + " Liked tweet " + tweet.getId());
    }

    public void sendTweet(String username, String password, String content) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        logger.info(username + "sent a tweet");
        Tweet tweet = new Tweet(username, content, true);
        Load.LoadUser(username).getTweets().add(tweet.getId());
        Load.LoadUser(username).save();
    }

    public void sendTweet(String username, String password, String content, File file) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        Tweet tweet = new Tweet(username, content, true);
        if (file != null) {
            tweet.hasImage = true;
            tweet.save();
            FileHandler.saveImage(file, tweet.getId(), "tweetsImage");
        }
        logger.info(username + "sent a tweet");
        Load.LoadUser(username).getTweets().add(tweet.getId());
        Load.LoadUser(username).save();
    }
    public void sendComment(String username, String password, String content, int tweetId) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        Tweet backTweet = Load.LoadTweet(tweetId);
        if (backTweet == null) {
            logger.error("back tweet invalid");
            return;
        }
        logger.info(username + "sent a comment on tweet " + tweetId );
        Comment tweet = new Comment(username, content, tweetId);
        backTweet.getComments().add(tweet.getId());
        tweet.save();
        User user = Load.LoadUser(username);
        user.getComments().add(tweet.getId());
        user.save();
        backTweet.save();
    }

    public void sendComment(String username, String password, String content, File file, int tweetId) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        Tweet backTweet = Load.LoadTweet(tweetId);
        if (backTweet == null) {
            logger.error("back tweet invalid");
            return;
        }
        Comment tweet = new Comment(username, content, tweetId);
        if (file != null) {
            tweet.hasImage = true;
            tweet.save();
            FileHandler.saveImage(file, tweet.getId(), "tweetsImage");
        }
        logger.info(username + "sent a comment on tweet " + tweetId );
        backTweet.getComments().add(tweet.getId());
        tweet.save();
        backTweet.save();
    }
    public void sendReTweet(String username, String password, int tweetId, String content) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        if (Load.LoadTweet(tweetId) == null)
            return ;
        logger.info(username + " sent retweet from " + tweetId);
        Tweet tweet = new Tweet(username, content, tweetId);
        Load.LoadUser(username).getTweets().add(tweet.getId());
        Load.LoadUser(username).save();
    }
    public void reportTweet(String username, String password, int tweetId) {
        if (!"success".equals(GraphicAgent.serverAgent.authAgent.login(username, password))) {
            return ;
        }
        Tweet tweet = Load.LoadTweet(tweetId);
        if (!Filter.boolFind(tweet.getReportedBy(), username))
            tweet.getReportedBy().add(username);
        logger.info(username + " reported tweet " + tweetId);
        tweet.save();
    }

}
