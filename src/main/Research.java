package main;

import manager.TweetManager;
import manager.TwitterCriteria;
import model.Feeds;
import model.Tweet;
import sentiment.Analyzer;
import sentiment.ParallelAnalyzer;

import java.util.*;

/**
 * Description
 *
 * @author Ryan
 */

public class Research {
    public static void main(String[] args) {
        Analyzer analyzer = new Analyzer();
        TwitterCriteria criteria = TwitterCriteria.create()
                                                 .setMaxTweets(100)
                                                 .setQuerySearch("muslim OR Islam -ban")
                                                 .setEnglish(true)
                                                 .setFeedType(Feeds.TOP)
                                                 .setSince("2017-02-01")
                                                 .setUntil("2017-02-26")
                                                 .setGeocode("39.8,-95.583068847656," + "2500km");

        List<Tweet> tweets = TweetManager.getTweets(criteria);

        double sum = 0.0;

        System.out.println(tweets.size());
        System.out.println();

//        int i = 0;
//                for (Tweet t : tweets) {
//                    sum += analyzer.analyze(t);
//                    System.out.println(++i);
//                }
//
//                System.out.println(sum / tweets.size());

        Map<Tweet, Double> map = ParallelAnalyzer.analyze(tweets);

        for (Map.Entry<Tweet, Double> e : map.entrySet()) {
            System.out.println(e.getKey());
            sum += e.getValue();
            System.out.println(e.getValue());
            System.out.println();
        }

        System.out.println(sum / tweets.size());
    }
}
