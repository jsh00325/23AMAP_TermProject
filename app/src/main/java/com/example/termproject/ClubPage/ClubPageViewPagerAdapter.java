package com.example.termproject.ClubPage;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ClubPageViewPagerAdapter extends FragmentStateAdapter {

    String name;

    String Uid;

    public ClubPageViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);

    }

    public ClubPageViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, String name) {
        super(fragmentActivity);
        this.name = name;
    }
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return ClubInfo.newInstance(position, name);
            case 1:
                return ClubPost.newInstance(position , name);

        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
