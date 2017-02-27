package main;

import java.util.*;
import model.*;
import java.io.*;
import java.util.concurrent.*;

public class Yearly {
    private static final int TWEETS_PER_DAY = 1000;
    private static final int YEAR = 2015;
    private static final ForkJoinPool POOL = new ForkJoinPool();

    public static void main(String[] args) throws IOException {
        Set<TweetFetcher> threads = new HashSet<>();
        String query = "muslim OR Islam";

        PrintStream ps = new PrintStream(new File("2015.tsv"));

        for (int month = 1; month <= 12; month++) {
            Set<Tweet> tweets = new HashSet<>();

            for (int day = 1; validDay(month, day); day++) {
                if (month == 2 && day > 27 || (month == 9 || month == 4 || month == 6 || month == 11) && day > 29) {
                    break;
                }

                String startDate = YEAR + "-" + month + "-" + day;
                String endDate = YEAR + "-" + month + "-" + (day + 1);

                TweetFetcher tf = new TweetFetcher(startDate, endDate, TWEETS_PER_DAY, query, tweets);

                threads.add(tf);

                POOL.execute(tf);
            }

            for (TweetFetcher tf : threads) {
                ps.println(tf.getStartDate() + "\t" + tf.join());
            }

            threads.clear();

            Exporter.export("2015_raw.tsv", tweets, month != 1);
        }

        ps.close();
    }

    private static boolean validDay(int month, int day) {
        switch (month) {
            case 2:
                return day <= 27;
            case 9:
            case 4:
            case 6:
            case 11:
                return day <= 29;
            default:
                return day <= 30;
        }
    }
}
