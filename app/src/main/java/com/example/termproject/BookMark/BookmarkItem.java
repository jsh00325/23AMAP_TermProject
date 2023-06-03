package com.example.termproject.BookMark;

import com.google.firebase.firestore.DocumentReference;

public class BookmarkItem {

    private String image_url, club_name, main_category, sub_category;
    //private boolean alarmChecked;
    private DocumentReference clubRef;

    public BookmarkItem(String image_url, String club_name, String main_category, String sub_category, DocumentReference reference){
        this.image_url = image_url; //image_url
        this.club_name = club_name; //club_name
        this.main_category = main_category; //main_category
        this.sub_category = sub_category; //sub_category
        this.clubRef = clubRef;

    }
    //alt + inst 누르면 자동 getter & setter
    public String getIconUrl() {
        return image_url;
    }

    public void setIconUrl(String image_url) {
        this.image_url = image_url;
    }

    public String getClubName() {
        return club_name;
    }

    public void setClubName(String club_name) {
        this.club_name = club_name;
    }

    public String getMajor() {
        return main_category;
    }

    public void setMajor(String main_category) {
        this.main_category = main_category;
    }

    public String getMinor() {
        return sub_category;
    }

    public void setMinor(String sub_category) {
        this.sub_category = sub_category;
    }

    /*
    public boolean isAlarmChecked() {
        return alarmChecked;
    }

    public void setAlarmChecked(boolean alarmChecked) {
        this.alarmChecked = alarmChecked;
    }
    */

    public DocumentReference getClubRef() {
        return clubRef;
    }

    public void setClubRef(DocumentReference clubRef) {
        this.clubRef = clubRef;
    }
}