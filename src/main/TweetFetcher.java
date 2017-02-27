package main;

import manager.*;
import model.*;
import java.util.*;
import sentiment.*;
import java.util.concurrent.*;

public class TweetFetcher extends RecursiveTask<Double> {
    private final String startDate, endDate;
    private final int numTweets;
    private final String query;
    private final Set<Tweet> tweets;

    public TweetFetcher(String startDate, String endDate, int numTweets, String query, Set<Tweet> tweets) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.numTweets = numTweets;
        this.query = query;
        this.tweets = tweets;
    }

    @Override
    protected Double compute() {
        TwitterCriteria criteria = TwitterCriteria.create()
                .setMaxTweets(numTweets)
                .setQuerySearch(query)
                .setEnglish(true)
                .setFeedType(Feeds.LATEST)
                .setSince(startDate)
                .setUntil(endDate);

        Set<Tweet> tweets = TweetManager.getTweets(criteria);
        this.tweets.addAll(tweets);

        System.out.println(startDate);

        return ParallelAnalyzer.analyze(tweets);
    }

    public String getStartDate() {
        return startDate;
    }
}
