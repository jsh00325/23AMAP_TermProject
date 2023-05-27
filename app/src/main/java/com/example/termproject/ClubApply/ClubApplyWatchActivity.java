package com.example.termproject.ClubApply;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.termproject.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ClubApplyWatchActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String clubName;
    private TextView clubNameView;
    private ImageButton closeBtn;
    private RecyclerView applyRecycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clubapply_activity_watch);

        clubName = "New Club"; // TODO : 인텐트로 동아리 이름 받아오기...

        clubNameView = (TextView) findViewById(R.id.clubapply_watch_clubName);
        clubNameView.setText(clubName);

        closeBtn = (ImageButton) findViewById(R.id.clubapply_watch_closeBtn);
        closeBtn.setOnClickListener(view -> {finish();});

        applyRecycle = (RecyclerView) findViewById(R.id.clubapply_watch_recycle);
        applyRecycle.setLayoutManager(new LinearLayoutManager(this));

        List<String> applyDocIDs = new ArrayList<>();
        db.collection("club_apply").whereEqualTo("clubName", clubName).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                QuerySnapshot querySnapshot = task.getResult();
                for (QueryDocumentSnapshot document : querySnapshot)
                    applyDocIDs.add(document.getId());
            } else Log.d("ClubApplyWatch", "DB에서 신청 목록 가져오기 실패");
            applyRecycle.setAdapter(new ClubApplyWatchAdapter(applyDocIDs));
        });
    }
}