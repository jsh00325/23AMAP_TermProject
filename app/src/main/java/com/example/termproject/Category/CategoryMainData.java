package com.example.termproject.Category;

import java.util.ArrayList;

public class CategoryMainData {

    public String text;
    public ArrayList<String> subList;
    public boolean isOpen;

    public CategoryMainData(String text) {
        this.text = text;
        isOpen = false;
    }
}