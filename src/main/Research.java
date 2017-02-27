package main;

import java.util.*;
import model.*;
import java.io.*;
import java.util.concurrent.*;

public class Research {
    private static final int TWEETS_PER_DAY = 1000;
    private static final ForkJoinPool POOL = new ForkJoinPool();

    public static void main(String[] args) throws IOException {
        Set<Tweet> tweets = new HashSet<>();
        Set<TweetFetcher> threads = new HashSet<>();

        String query = "Islam OR muslim";

        //for (int day = 21; day < 31; day++) {
            String date = "2015-11";

            //String startDate = date + "-" + (day < 10 ? "0" : "") + day;
            //String endDate = date + "-" + (day + 1 < 10 ? "0" : "") + (day + 1);
            String startDate = "2015-11-30";
            String endDate = "2015-12-01";
            TweetFetcher tf = new TweetFetcher(startDate, endDate, TWEETS_PER_DAY, query, tweets);

            threads.add(tf);

            POOL.execute(tf);
        //}

        try (PrintStream ps = new PrintStream(new File("san_bernardino.tsv"))) {
            for (TweetFetcher tf2 : threads) {
                ps.println(tf2.getStartDate() + "\t" + tf2.join());
            }
        }

        Exporter.export("san_bernardino_raw.tsv", tweets);
        System.out.println(tweets.size());
    }
}
