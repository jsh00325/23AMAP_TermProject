package com.example.termproject.ClubApply;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.termproject.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class ClubApplyActivity extends AppCompatActivity {
    private String clubName;
    private FirebaseAuth user = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView clubNameView;
    private ImageButton closeBtn;
    private EditText introduceTextView;
    private Button applyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clubapply_activity);

        clubName = getIntent().getStringExtra("club_name"); // 인텐트에서 동아리 이름 받아오기
        clubNameView = (TextView) findViewById(R.id.clubapply_clubName);
        clubNameView.setText(clubName);

        closeBtn = findViewById(R.id.clubapply_closeBtn);
        closeBtn.setOnClickListener(view -> { finish(); });

        introduceTextView = findViewById(R.id.clubapply_introduce_text);

        applyBtn = findViewById(R.id.clubapply_submissionBtn);
        applyBtn.setOnClickListener(view -> {
            // DB에 신청 정보 저장하기
            Map<String, Object> applyData = new HashMap<>();

            applyData.put("userId", user.getUid());
            applyData.put("clubName", clubName);
            applyData.put("applyTime", Timestamp.now());
            applyData.put("introduceText", introduceTextView.getText().toString());

            DocumentReference newDoc = db.collection("club_apply").document();
            newDoc.set(applyData).addOnSuccessListener(unused -> {
                Log.d("clubApplyDB", newDoc.getId() + " 문서에 저장함.");
            }).addOnFailureListener(e -> {
                Log.w("clubApplyDB", "신청 정보 저장 에러...", e);
            });

            finish();
        });
    }
}