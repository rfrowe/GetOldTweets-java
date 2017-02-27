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
        TwitterCriteria criteria = TwitterCriteria.create()
                                                 .setMaxTweets(1000)
                                                 .setQuerySearch("muslim OR Islam -ban")
                                                 .setEnglish(true)
                                                 .setFeedType(Feeds.LATEST)
                                                 .setSince("2017-02-01")
                                                 .setUntil("2017-02-26")
                                                 .setGeocode("39.8,-95.583068847656," + "2500km");

        Set<Tweet> tweets = TweetManager.getTweets(criteria);

        double sum = 0.0;

        System.out.println(tweets.size());
        System.out.println();

        ParallelAnalyzer.analyze(tweets);

        for (Tweet t : tweets) {
            sum += t.getSentiment();
        }

        System.out.println(sum / tweets.size());

        Exporter.export("data1.tsv", tweets);
    }
}
