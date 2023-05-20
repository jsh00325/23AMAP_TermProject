package com.example.termproject.Home.Filter;

import java.util.ArrayList;
import java.util.List;

public class HomeFilterMainData {
    private String mainCategory;
    private List<HomeFilterSubData> childDatas = new ArrayList<>();
    private int childCount = 0;

    public HomeFilterMainData(String mainCategory, List<String> childCategory) {
        this.mainCategory = mainCategory;
        childDatas.add(new HomeFilterSubData(HomeFilterSubData.ALL, "모두"));
        for (String child : childCategory)
            childDatas.add(new HomeFilterSubData(HomeFilterSubData.CHILD, child));
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public List<HomeFilterSubData> getChildDatas() {
        return childDatas;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }
    public void increaseCount() { childCount++; }
    public void decreaseCount() { childCount--; }
}
