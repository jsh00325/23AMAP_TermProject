package com.example.termproject.Home;

import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFilterData {
    private ToggleButton allBtn;
    private List<ToggleButton> childBtns = new ArrayList<>();
    private int checkCount = 0;

    public HomeFilterData(ToggleButton allBtn) {
        this.allBtn = allBtn;
    }
    public void addChildBtn(ToggleButton child) {
        childBtns.add(child);
    }

    public void settingListener() {
        allBtn.setOnClickListener(view -> {
            if (allBtn.isChecked()) {
                for (ToggleButton childBtn : childBtns)
                    childBtn.setChecked(true);
                checkCount = childBtns.size();
            }
            else {
                for (ToggleButton childBtn : childBtns)
                    childBtn.setChecked(false);
                checkCount = 0;
            }
        });
        for (ToggleButton childBtn : childBtns) {
            childBtn.setOnClickListener(view -> {
                if (childBtn.isChecked()) checkCount++;
                else checkCount--;

                if (checkCount == childBtns.size()) allBtn.setChecked(true);
                else allBtn.setChecked(false);
            });
        }
    }
    public void clearAll() {
        allBtn.setChecked(false);
        for (ToggleButton childBtn : childBtns)
            childBtn.setChecked(false);
        checkCount = 0;
    }

    public List<ToggleButton> getChildBtns() {
        return childBtns;
    }
}
