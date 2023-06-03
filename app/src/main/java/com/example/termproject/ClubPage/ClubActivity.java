package com.example.termproject.ClubPage;


import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.example.termproject.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClubActivity extends AppCompatActivity {

    ImageButton apply_btn;
    Map<String, List<String>> bookMark = new HashMap<>();
    boolean bookMarked;
    String name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clubpage_activity);

        Toolbar toolbar = findViewById(R.id.club_activity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        ImageButton dropDownMenu = findViewById(R.id.cp_item_dropdown_menu);

        /*
         * 만약 해당 동아리 계정이 아니면
         * dropDownMenu.setVisibility(View.GONE);
         * 해주면 됨
         *
         *
         *
         * */



        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.club_activity_collapsebar);
        collapsingToolbarLayout.setTitle("동아리 페이지");

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager2 = findViewById(R.id.home_viewpager2);

        ImageButton back_btn = findViewById(R.id.back_btn);


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClubActivity.super.onBackPressed();
            }
        });
        ClubPageViewPagerAdapter clubPageViewPagerAdapter = new ClubPageViewPagerAdapter(this, name);
        viewPager2.setAdapter(clubPageViewPagerAdapter);

        ArrayList<String> tabElement = new ArrayList<>();
        tabElement.add("동아리 정보");
        tabElement.add("게시글");

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                TextView textView = new TextView(ClubActivity.this);
                textView.setText(tabElement.get(position));
                textView.setGravity(Gravity.CENTER);
                tab.setCustomView(textView);
            }
        }).attach();

        tabLayout.setTabTextColors(R.color.gray_dbdbdb, R.color.maintheme);
    }

}