package main;

import java.util.*;
import model.*;
import java.io.*;
import java.util.concurrent.*;

public class Graph {
    public static final int NUM_YEARS = 4;
    public static final int TWEETS_PER_MONTH = 100;
    private static final int BASE_YEAR = 2012;
    private static final ForkJoinPool POOL = new ForkJoinPool();

    public static void main(String[] args) throws IOException {
        Set<Tweet> tweets = new HashSet<>();
        Set<TweetFetcher> threads = new HashSet<>();

        for (int year = BASE_YEAR; year < BASE_YEAR + NUM_YEARS; year++) {
            for (int month = 1; month <= 12; month++) {
                String date = year + "-" + month;

                String startDate = date + "-01";
                String endDate = date + (month == 2 ? "-28" : "-30");

                TweetFetcher tf = new TweetFetcher(startDate, endDate, TWEETS_PER_MONTH, "muslim OR Islam -ban", tweets);

                threads.add(tf);

                POOL.execute(tf);
            }
        }

        try (PrintStream ps = new PrintStream(new File("massive.tsv"))) {
            for (TweetFetcher tf : threads) {
                ps.println(tf.getStartDate() + "\t" + tf.join());
            }
        }

        Exporter.export("massive_raw.tsv", tweets);
        System.out.println(tweets.size());
    }
}
