package com.example.termproject.Category.CategoryTree;

import java.util.ArrayList;

public class CategoryMainData {

    final private String text;
    private ArrayList<String> subList;
    private boolean isOpen = false;

    public CategoryMainData(String text, ArrayList<String> subList) {
        this.text = text;
        this.subList = subList;
    }

    public String getText() {
        return text;
    }
    public ArrayList<String> getSubList() {
        return subList;
    }
    public boolean getIsOpen() {
        return isOpen;
    }

    public void flipIsOpen() {isOpen = !isOpen;}
}