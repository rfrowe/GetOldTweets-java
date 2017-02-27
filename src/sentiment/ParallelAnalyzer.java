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

    public static void analyze(List<Tweet> tweets) {
        POOL.invoke(new AnalyzeAction(new Analyzer(), tweets, 0, tweets.size()));
    }

    private static class AnalyzeAction extends RecursiveAction {
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
        protected void compute() {
            if (hi - lo <= CUTOFF) {
                for (int i = lo; i < hi; i++) {
                    tweets.get(i).setSentiment(analyzer.analyze(tweets.get(i)));
                }
            } else {
                int mid = lo + (hi - lo) / 2;

                AnalyzeAction left = new AnalyzeAction(analyzer, tweets, lo, mid);
                AnalyzeAction right = new AnalyzeAction(analyzer, tweets, mid, hi);
                right.fork();

                left.compute();
                right.join();
            }
        }
    }
}
