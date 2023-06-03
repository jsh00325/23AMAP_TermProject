package com.example.termproject.MyPage;

import java.util.List;

public class ScrapItemData {
    private List<String> imageUrls;

    public ScrapItemData(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}


