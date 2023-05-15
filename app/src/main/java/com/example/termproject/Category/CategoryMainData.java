package com.example.termproject.Category;

import java.util.ArrayList;

public class CategoryMainData {

    public String text;
    public ArrayList<CategorySubData> subList;
    public boolean isOpen;

    public CategoryMainData(String text) {
        this.text = text;
        isOpen = false;
        subList = new ArrayList<>();
    }

    public void insertChild(String text, String key) {
        subList.add(new CategorySubData(text, key));
    }
}