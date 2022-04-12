package com.example.twittercc;

public class Tweet {
    private String username;
    private String displayname;
    private String tweet;
    private String publishedTime;

    public Tweet(String username,String displayname,String tweet,String publishedTime){
        setDisplayname(displayname);
        setUsername(username);
        setTweet(tweet);
        setPublishedTime(publishedTime);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(String publishedTime) {
        this.publishedTime = publishedTime;
    }
}
