package com.example.termproject.MyPage;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.termproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ScrapListActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private ScrapListAdapter adapter;
    private List<String> imageUrlList;
    private SwipeRefreshLayout likelistSrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_activity_like);

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.mypage_like);
        imageUrlList = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        recyclerView.setVisibility(View.GONE);

        likelistSrl = (SwipeRefreshLayout)findViewById(R.id.likelist_srl);
        likelistSrl.setColorSchemeColors(getResources().getColor(R.color.Primary));
        likelistSrl.setOnRefreshListener(() -> {
            db.collection("club_post")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            imageUrlList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                List<String> imageUrls = (List<String>) document.get("imageURL");
                                List<String> likeUsers = (List<String>) document.get("like_user");
                                if (imageUrls != null && likeUsers != null && likeUsers.contains(user.getUid())) {
                                    imageUrlList.add(imageUrls.get(0));
                                }
                            }

                            adapter = new ScrapListAdapter(imageUrlList, recyclerView);
                            recyclerView.setAdapter(adapter);
                            // RecyclerView 레이아웃 관리자 설정
                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
                            recyclerView.setLayoutManager(layoutManager);
                            adapter.onDataLoaded();

                        } else {
                            Log.d("ScrapListActivity", "Error getting documents: ", task.getException());
                        }
                    });
            likelistSrl.setRefreshing(false);
        });







        db.collection("club_post")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        imageUrlList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            List<String> imageUrls = (List<String>) document.get("imageURL");
                            List<String> likeUsers = (List<String>) document.get("like_user");
                            if (imageUrls != null && likeUsers != null && likeUsers.contains(user.getUid())) {
                                imageUrlList.add(imageUrls.get(0));
                            }
                        }

                        adapter = new ScrapListAdapter(imageUrlList, recyclerView);
                        recyclerView.setAdapter(adapter);
                        // RecyclerView 레이아웃 관리자 설정
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
                        recyclerView.setLayoutManager(layoutManager);
                        adapter.onDataLoaded();

                    } else {
                        Log.d("ScrapListActivity", "Error getting documents: ", task.getException());
                    }
                });
    }
}

