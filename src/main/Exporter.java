package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import manager.TweetManager;
import manager.TwitterCriteria;
import model.Tweet;

public class Exporter {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");

	public static void export(String fileName, List<Tweet> tweets) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
			bw.write("id\tusername\tdate\ttext\tsentiment\tretweets\tfavorites\tmentions\tgeo\tpermalink\thashtags");
			bw.newLine();

			for (Tweet t : tweets) {
				bw.write(String.format("%s\t%s\t%s\t%s\t%f\t%d\t%d\t%s\t%s\t%s\t%s",
				         t.getId(),
				         t.getUsername(),
				         sdf.format(t.getDate()),
				         t.getText(),
				         t.getSentiment(),
				         t.getRetweets(),
				         t.getFavorites(),
				         t.getMentions(),
				         t.getGeo(),
				         t.getPermalink(),
				         t.getHashtags()));
				bw.newLine();
			}

			bw.close();

			System.out.println("Done. Output file generated \"" + fileName + "\".");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}