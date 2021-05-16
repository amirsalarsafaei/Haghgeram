package com.SalarJavaDevGroup.util;

import com.SalarJavaDevGroup.FileHandling.Load;
import com.SalarJavaDevGroup.Models.Comment;
import com.SalarJavaDevGroup.Models.Conversation;
import com.SalarJavaDevGroup.Models.Tweet;

import java.util.ArrayList;

public abstract class Convertor {
    public static ArrayList<Tweet> ConvertTwitter(ArrayList<Integer> tweets) {
        ArrayList<Tweet> res = new ArrayList<>();
        for (int tmp : tweets) {
            Tweet tweet = Load.LoadTweet(tmp);
            if (tweet != null) {
                res.add(tweet);
            }
        }
        return res;
    }

    public static ArrayList<Comment> ConvertComment(ArrayList<Integer> comments) {
        ArrayList<Comment> res = new ArrayList<>();
        for (int tmp : comments) {
            Comment comment = Load.LoadComment(tmp);
            if (comment != null) {
                res.add(comment);
            }
        }
        return res;
    }

    public static ArrayList<Conversation> ConvertConversations(ArrayList<Integer> convs) {
        ArrayList<Conversation> res = new ArrayList<>();
        for (int tmp : convs) {
            Conversation conversation = Load.LoadConversation(tmp);
            if (conversation != null) {
                res.add(conversation);
            }
        }
        return res;
    }

}
