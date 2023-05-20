package com.example.termproject.Home.Feed;

import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.List;

public class HomeFeedData {
    private String clubImageURL = "";
    private String clubName;
    private Timestamp uploadTime;
    private List<String> feedImageURLs;
    private String mainText;

    public HomeFeedData() {

    }
    public HomeFeedData(String clubImageURL, String clubName, Timestamp uploadTime, List<String> feedImageURLs, String mainText) {
        this.clubImageURL = clubImageURL;
        this.clubName = clubName;
        this.uploadTime = uploadTime;
        this.feedImageURLs = feedImageURLs;
        this.mainText = mainText;
    }

    public String getClubImageURL() {
        return clubImageURL;
    }
    public String getClubName() {
        return clubName;
    }
    public List<String> getFeedImageURLs() {
        return feedImageURLs;
    }
    public String getMainText() {
        return mainText;
    }

    public void setClubImageURL(String clubImageURL) {
        this.clubImageURL = clubImageURL;
    }
    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
    public void setUploadTime(Timestamp uploadTime) {
        this.uploadTime = uploadTime;
    }
    public void setFeedImageURLs(List<String> feedImageURLs) {
        this.feedImageURLs = feedImageURLs;
    }
    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public String convertTimestamp() {
        return new SimpleDateFormat("yy/MM/dd HH:mm").format(uploadTime.toDate());
    }
}
