package sentiment;

import model.Tweet;

import java.util.*;

/**
 * Description
 *
 * @author Ryan
 */

public class Analyzer {
    public double analyze(Tweet tweet) {
        String text = tweet.getText();

        // Remove any links
        text = text.replaceAll("http[s]?:\\/\\/[\\S]+", " ");
        text = text.replaceAll("pic\\.twitter\\.com\\/\\w+", " ");

        // Strip mentions
        text = text.replaceAll("@[\\S]+", "");

        text = deHashtag(text);
        text = text.trim();

        tweet.setSentiment(analyze(text));
        return tweet.getSentiment();
    }

    public double analyze(String text) {
        NLPParser parser = NLPParser.getSingleton();

        List<SentiWord> words = parser.parse(text.toLowerCase());

        double sentiScore = 0.0;

        for (SentiWord word : words) {
            sentiScore += SentiWordNet.getSingleton().extract(word);
        }

        return sentiScore;
    }

    public static void main(String[] args) {
        Analyzer a = new Analyzer();
        SentiWordNet.getSingleton();
        System.out.println(a.analyze("The Lying NYTimes is at it again! Don’t believe a word. Sad!"));
    }

    private static String deHashtag(String hashtag) {
        StringBuilder str = new StringBuilder();

        boolean inHashtag = false;
        for (int i = 0; i < hashtag.length(); i++) {
            if (inHashtag) {
                if (hashtag.charAt(i) == ' ') {
                    inHashtag = false;
                    str.append(' ');
                } else if (Character.isUpperCase(hashtag.charAt(i))){
                    str.append(' ');
                    str.append(hashtag.charAt(i));
                } else {
                    str.append(hashtag.charAt(i));
                }
            } else {
                if(hashtag.charAt(i) == '#' && (i == 0 || hashtag.charAt(i - 1) == ' ')) {
                    inHashtag = true;
                    str.append(' ');
                } else {
                    str.append(hashtag.charAt(i));
                }
            }
        }

        return str.toString();
    }
}
