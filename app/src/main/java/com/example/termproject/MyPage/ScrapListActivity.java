package com.example.termproject.MyPage;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ScrapListActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private ScrapListAdapter adapter;
    private List<String> imageUrlList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_activity_like);

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.mypage_like);
        imageUrlList = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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

                        adapter = new ScrapListAdapter(imageUrlList);
                        recyclerView.setAdapter(adapter);

                        // RecyclerView 레이아웃 관리자 설정
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
                        recyclerView.setLayoutManager(layoutManager);
                    } else {
                        Log.d("ScrapListActivity", "Error getting documents: ", task.getException());
                    }
                });
    }
}

