package com.example.termproject.ClubApply;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.termproject.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class ClubApplyActivity extends AppCompatActivity {
    private String clubName;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView clubNameView;
    private ImageButton closeBtn;
    private EditText introduceTextView;
    private Button applyBtn;
    private String curString = "";
    private int activeColor, deactiveColor;
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clubapply_activity);

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        clubName = getIntent().getStringExtra("club_name"); // 인텐트에서 동아리 이름 받아오기
        clubNameView = (TextView) findViewById(R.id.clubapply_clubName);
        clubNameView.setText(clubName);

        closeBtn = findViewById(R.id.clubapply_closeBtn);
        closeBtn.setOnClickListener(view -> { finish(); });

        activeColor = ContextCompat.getColor(ClubApplyActivity.this, R.color.Primary);
        deactiveColor = ContextCompat.getColor(ClubApplyActivity.this, R.color.gray_dbdbdb);

        applyBtn = findViewById(R.id.clubapply_submissionBtn);
        applyBtn.setBackgroundColor(deactiveColor);
        applyBtn.setOnClickListener(view -> {
            if (curString.equals("")) {
                Toast.makeText(this, "자기소개란을 작성해주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            imm.hideSoftInputFromWindow(introduceTextView.getWindowToken(), 0);

            // DB에 신청 정보 저장하기
            Map<String, Object> applyData = new HashMap<>();

            applyData.put("userId", user.getUid());
            applyData.put("clubName", clubName);
            applyData.put("applyTime", Timestamp.now());
            applyData.put("introduceText", curString);

            DocumentReference newDoc = db.collection("club_apply").document();
            newDoc.set(applyData).addOnSuccessListener(unused -> {
                Log.d("clubApplyDB", newDoc.getId() + " 문서에 저장함.");
                Toast.makeText(this, clubName + "에 신청을 완료했습니다", Toast.LENGTH_SHORT).show();
                finish();
            }).addOnFailureListener(e -> {
                Log.w("clubApplyDB", "신청 정보 저장 에러...", e);
                Toast.makeText(this, "신청 오류", Toast.LENGTH_SHORT).show();
            });
        });

        introduceTextView = findViewById(R.id.clubapply_introduce_text);
        introduceTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                curString = introduceTextView.getText().toString();
                if (curString.equals("")) applyBtn.setBackgroundColor(deactiveColor);
                else applyBtn.setBackgroundColor(activeColor);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}