package com.example.termproject.Post;

import java.util.List;

public class PostData {
    private String category;
    private String club_name;
    private String imageURL;
    private List<String> like_users;
    private String main_text;
    private String uptime;
    private String userID;

    public PostData() {
        // Default constructor required for Firebase
    }

    public PostData(String category, String club_name, String imageURL, List<String> like_users, String main_text, String uptime, String userID) {
        this.category = category;
        this.club_name = club_name;
        this.imageURL = imageURL;
        this.like_users = like_users;
        this.main_text = main_text;
        this.uptime = uptime;
        this.userID = userID;
    }

    public String getCategory() {
        return category;
    }

    public String getClub_name() {
        return club_name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public List<String> getLike_users() {
        return like_users;
    }

    public String getMain_text() {
        return main_text;
    }

    public String getUptime() {
        return uptime;
    }

    public String getUserID() {
        return userID;
    }
}
