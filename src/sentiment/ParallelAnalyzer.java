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

    public static Map<Tweet, Double> analyze(List<Tweet> tweets) {
        Map<Tweet, Double> map = new HashMap<>();

        POOL.invoke(new AnalyzeAction(new Analyzer(), tweets, map, 0, tweets.size()));

        return map;
    }

    private static class AnalyzeAction extends RecursiveAction {
        private final Analyzer analyzer;
        private final List<Tweet> tweets;
        private final Map<Tweet, Double> map;
        private final int lo, hi;
        private static final int CUTOFF = 5;

        public AnalyzeAction(Analyzer analyzer, List<Tweet> tweets, Map<Tweet, Double> map, int lo, int hi) {
            this.analyzer = analyzer;
            this.tweets = tweets;
            this.map = map;
            this.lo = lo;
            this.hi = hi;
        }

        /**
         * The main computation performed by this task.
         */
        @Override
        protected void compute() {
            if (hi - lo <= CUTOFF) {
                for (int i = lo; i < hi; i++) {
                    map.put(tweets.get(i), analyzer.analyze(tweets.get(i)));
                }
            } else {
                int mid = lo + (hi - lo) / 2;

                AnalyzeAction left = new AnalyzeAction(analyzer, tweets, map, lo, mid);
                AnalyzeAction right = new AnalyzeAction(analyzer, tweets, map, mid, hi);
                right.fork();

                left.compute();
                right.join();
            }
        }
    }
}
