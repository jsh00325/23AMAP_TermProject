package com.example.termproject.Post;

public class PostData {
    private String category;
    private String club_name;
    private String imageURL;
    private String like_user;
    private String main_text;
    private String uptime;
    private String userID;

    public PostData() {
        // Default constructor required for Firebase
    }

    public PostData(String category, String club_name, String imageURL, String like_user, String main_text, String uptime, String userID) {
        this.category = category;
        this.club_name = club_name;
        this.imageURL = imageURL;
        this.like_user = like_user;
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

    public String getLike_user() {
        return like_user;
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
