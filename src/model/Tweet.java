package model;

import java.util.Date;

/**
 * Model class to helps users getting info about an specific tweet
 * 
 * @author Jefferson
 */
public class Tweet {
	private String id;
	private String permalink;
	private String username;
	private String text;
	private Date date;
	private int retweets;
	private int favorites;
	private String mentions;
	private String hashtags;
	private String geo;
	private double sentiment;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPermalink() {
		return permalink;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getRetweets() {
		return retweets;
	}

	public void setRetweets(int retweets) {
		this.retweets = retweets;
	}

	public int getFavorites() {
		return favorites;
	}

	public void setFavorites(int favorites) {
		this.favorites = favorites;
	}
	
	public String getMentions() {
		return mentions;
	}

	public void setMentions(String mentions) {
		this.mentions = mentions;
	}
	
	public String getHashtags() {
		return hashtags;
	}

	public void setHashtags(String hashtags) {
		this.hashtags = hashtags;
	}

	public String getGeo() {
		return geo;
	}

	public void setGeo(String geo) {
		this.geo = geo;
	}

	public double getSentiment() {
		return sentiment;
	}

	public void setSentiment(double sentiment) {
		this.sentiment = sentiment;
	}

	@Override
	public String toString() {
		return "Tweet{" +
		       "id='" + id + '\'' +
		       ", permalink='" + permalink + '\'' +
		       ", username='" + username + '\'' +
		       ", text='" + text + '\'' +
		       ", date=" + date +
		       ", retweets=" + retweets +
		       ", favorites=" + favorites +
		       ", mentions='" + mentions + '\'' +
		       ", hashtags='" + hashtags + '\'' +
		       ", geo='" + geo + '\'' +
		       ", sentiment=" + sentiment +
		       '}';
	}
}