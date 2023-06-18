package com.example.termproject.ClubPage;


import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClubActivity extends AppCompatActivity {

    boolean bookMarked;
    ImageButton bookmark;
    DocumentSnapshot document;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clubpage_activity);
        TextView nametext = findViewById(R.id.nameTextView);
        Toolbar toolbar = findViewById(R.id.club_activity_toolbar);
        bookmark = findViewById(R.id.bookMark);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserUid = user.getUid();
        name = getIntent().getStringExtra("club_name");
        db.collection("users")
                .document(currentUserUid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        document = task.getResult();
                        if (document != null && document.exists()) {
                            // 현재 사용자의 문서에서 bookMark 배열 가져오기
                            List<String> bookMarkList = (List<String>) document.get("bookMark");

                            // name과 같은 문자열이 있는지 확인
                            bookMarked = bookMarkList != null && bookMarkList.contains(name);

                            if (bookMarked) {
                                setbookmark(bookMarked);
                            } else {
                                setbookmark(bookMarked);
                            }
                        }
                    } else {
                        Log.d("ClubPost", "Error getting document: ", task.getException());
                    }
                });



        nametext.setText(name);
        Log.d("ClubActivity", "Received club_name: " + name);
        /*
         * 만약 해당 동아리 계정이 아니면
         * dropDownMenu.setVisibility(View.GONE);
         * 해주면 됨
         *
         *
         *
         * */

        bookmark.setOnClickListener(view -> {
            if (bookMarked) {
                bookMarked = false;
                setbookmark(bookMarked);

                document.getReference().update("bookMark", FieldValue.arrayRemove(name))
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // 업데이트가 성공적으로 완료된 경우 실행할 코드
                            } else {
                                // 업데이트가 실패한 경우 실행할 코드
                            }
                        });
            } else {
                bookMarked = true;
                setbookmark(bookMarked);

                document.getReference().update("bookMark", FieldValue.arrayUnion(name))
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // 업데이트가 성공적으로 완료된 경우 실행할 코드
                            } else {
                                // 업데이트가 실패한 경우 실행할 코드
                            }
                        });
            }
        });
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
    private void setbookmark(boolean book) {
        if (book)
            bookmark.setImageResource(R.drawable.bookmark_selected);
        else
            bookmark.setImageResource(R.drawable.bookmark);
    }

}