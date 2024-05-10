package project.datacollecting.twitter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

import twitter4j.v1.Paging;
import twitter4j.v1.Query;
import twitter4j.v1.QueryResult;
import twitter4j.v1.StallWarning;
import twitter4j.v1.StatusDeletionNotice;
import twitter4j.v1.StatusListener;
import twitter4j.v1.StreamListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;
import twitter4j.TwitterV2;
import twitter4j.TwitterV2ExKt;


public class twitterAPITest2 {

    // public static void main(String[] args) throws TwitterException {

    //     Twitter twitter = new TwitterObjectFactory().getInstance();
    //     final TwitterV2 v2 = TwitterV2ExKt.getV2(twitter);
        
    //     int pageno = 1;
    //     String user = "cnn";
    //     List statuses = new ArrayList();
    
    //     while (true) {
    
    //       try {
    
    //         int size = statuses.size(); 
    //         Paging page = new Paging(pageno++, 100);
    //         statuses.addAll(getUserTweets());
    //         if (statuses.size() == size)
    //           break;
    //       }
    //       catch(TwitterException e) {
    
    //         e.printStackTrace();
    //       }
    //     }
    
    //     System.out.println("Total: "+statuses.size());
 
    // }
}

