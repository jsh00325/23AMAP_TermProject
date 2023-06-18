package com.example.termproject.Home.Feed;

import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.List;

public class HomeFeedData {
    private String clubImageURL = "";
    private String clubName;
    private Timestamp uploadTime;
    private List<String> feedImageURLs, commentDocIDs;
    private String mainText;
    private boolean like;
    private int likeCount;

    public HomeFeedData() {

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
    public boolean isLike() {
        return like;
    }
    public int getLikeCount() {
        return likeCount;
    }
    public List<String> getCommentDocIDs() {
        return commentDocIDs;
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
    public void setLike(boolean like) {
        this.like = like;
    }
    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
    public void setCommentDocIDs(List<String> commentDocIDs) {
        this.commentDocIDs = commentDocIDs;
    }

    public String convertTimestamp() {
        return new SimpleDateFormat("yy/MM/dd HH:mm").format(uploadTime.toDate());
    }
}
