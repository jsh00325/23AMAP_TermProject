package com.example.termproject.Home.Filter;

import android.widget.ToggleButton;

public class HomeFilterSubData {
    final static public int ALL = 0;
    final static public int CHILD = 1;
    private int type;
    private String subCategory;
    private boolean isChecked = false;

    public HomeFilterSubData(int type, String subCategory) {
        this.type = type;
        this.subCategory = subCategory;
    }

    public int getType() {
        return type;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean getChecked() {
        return isChecked;
    }
}
