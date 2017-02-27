package sentiment;

import model.Tweet;

import java.util.*;
import java.util.concurrent.*;

/**
 * Description
 *
 * @author Ryan
 */

public class ParallelAnalyzer {
    private static final ForkJoinPool POOL = new ForkJoinPool();

    public static double analyze(Set<Tweet> tweets) {
        List<Tweet> list = new ArrayList<>(tweets);
        return analyze(list, 0, list.size());
    }

    public static double analyze(List<Tweet> list, int lo, int hi) {
        return POOL.invoke(new AnalyzeAction(new Analyzer(), list, lo, hi)) / (hi - lo);
    }

    private static class AnalyzeAction extends RecursiveTask<Double> {
        private final Analyzer analyzer;
        private final List<Tweet> tweets;
        private final int lo, hi;
        private static final int CUTOFF = 5;

        public AnalyzeAction(Analyzer analyzer, List<Tweet> tweets, int lo, int hi) {
            this.analyzer = analyzer;
            this.tweets = tweets;
            this.lo = lo;
            this.hi = hi;
        }

        /**
         * The main computation performed by this task.
         */
        @Override
        protected Double compute() {
            if (hi - lo <= CUTOFF) {
                double sum = 0.0;

                for (int i = lo; i < hi; i++) {
                    double sentiment = analyzer.analyze(tweets.get(i));
                    tweets.get(i).setSentiment(sentiment);
                    sum += sentiment;
                }

                return sum;
            } else {
                int mid = lo + (hi - lo) / 2;

                AnalyzeAction left = new AnalyzeAction(analyzer, tweets, lo, mid);
                AnalyzeAction right = new AnalyzeAction(analyzer, tweets, mid, hi);
                right.fork();

                double leftSum = left.compute();

                return right.join() + leftSum;
            }
        }
    }
}
