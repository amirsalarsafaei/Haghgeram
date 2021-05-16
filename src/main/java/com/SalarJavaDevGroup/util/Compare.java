package com.SalarJavaDevGroup.util;

import com.SalarJavaDevGroup.Models.Conversation;
import com.SalarJavaDevGroup.Models.Message;
import com.SalarJavaDevGroup.Models.Tweet;

import java.util.Comparator;

public abstract class Compare {

    public static Comparator<Tweet> compare_tweet_by_date = (Tweet t1, Tweet t2) -> t1.getCreatedDate().compareTo(t2.getCreatedDate());

    public static Comparator<Message> compare_message_by_date = (Message m1, Message m2) -> m1.getDate().compareTo(m2.getDate());

    public static class compare_conversations implements Comparator<Conversation> {
        String username;
        public compare_conversations(String username) {
            this.username = username;
        }
        @Override
        public int compare(Conversation o1, Conversation o2) {
            if (o1.unread(username) > 0 && o2.unread(username) > 0)
                return o1.getLastDate().compareTo(o2.getLastDate());
            if (o1.unread(username) > 0)
                return 1;
            if (o2.unread(username) > 0)
                return -1;
            return o1.getLastDate().compareTo(o2.getLastDate());
        }
    }
}
