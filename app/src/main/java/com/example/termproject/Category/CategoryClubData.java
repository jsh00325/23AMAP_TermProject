package com.example.termproject.Category;

public class CategoryClubData {
    final private String imageURL;
    final private String clubName;
    final private String mainCategory;
    final private String subCategory;

    public CategoryClubData(String imageURL, String clubName, String mainCategory, String subCategory) {
        this.imageURL = imageURL;
        this.clubName = clubName;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
    }

    public String getImageURL() {
        return imageURL;
    }
    public String getClubName() {
        return clubName;
    }
    public String getMainCategory() {
        return mainCategory;
    }
    public String getSubCategory() {
        return subCategory;
    }
}