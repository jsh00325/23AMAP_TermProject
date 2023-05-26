package com.example.termproject.UserManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.termproject.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.termproject.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class MemberInitActivity extends AppCompatActivity {

    private static final String TAG = "MemberInfoActivity";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;
    private EditText nameEditText, schoolNumEditText, departmentEditText, phoneNumEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity_minit);
        Button startButton = findViewById(R.id.startButton);
        nameEditText = findViewById(R.id.nameEditText);
        schoolNumEditText = findViewById(R.id.schoolNumEditText);
        departmentEditText = findViewById(R.id.departmentEditText);
        phoneNumEditText = findViewById(R.id.phoneNumEditText);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileUpdate();
                Intent intent = new Intent(MemberInitActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                toast("신비한 동아리 사전에 오신 것을 환영합니다!!");
                startActivity(intent);
                finish();
            }
        });
    }

    private void profileUpdate(){

        String name = nameEditText.getText().toString();
        String schoolNum = schoolNumEditText.getText().toString();
        String department = departmentEditText.getText().toString();
        String phoneNum = phoneNumEditText.getText().toString();


    if(name.length()>0 && schoolNum.length()==10 && department.length()>0 && phoneNum.length()>=10){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if(user != null){
            //이름, 학번, 학과, 전화번호
            MemberInfo memberInfo = new MemberInfo(user.getUid(), name, schoolNum, department, phoneNum);
            db.collection("users").document(user.getUid()).set(memberInfo)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                            //회원 등록 성공 로직
                            toast("회원 정보 등록에 성공하였습니다!!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                            toast("회원 정보 등록에 실패하였습니다");

                        }
                    });
        }

    }
        else{
        toast("회원 정보 등록에 실패했습니다.");
    }
}
    private void toast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}