package com.example.termproject.UserManagement;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.termproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;


public class SignUpActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseFirestore mDb;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button signUpButton;
    private FirebaseUser mUser;
    private static final String TAG = "SignUpActivity";
    private Long mLastClickTime = 10000L;
    Toolbar toolbar;


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        toolbar = findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        mDb = FirebaseFirestore.getInstance();
        mUser = mAuth.getCurrentUser();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signUpButton = findViewById(R.id.startButton);

        signUpButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            signUp(email, password);
        });

        //random 이 부분 일단 제외

    }

    private void signUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 이메일 인증 보내기
                            sendEmailVerification();
                        } else {
                            // 회원 가입 실패
                            Toast.makeText(SignUpActivity.this, "회원 가입에 실패했습니다.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendEmailVerification() {
        FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "이메일 인증을 보냈습니다. 이메일을 확인해주세요.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("TAG", "sendEmailVerification", task.getException());
                    Toast.makeText(SignUpActivity.this, "이메일 인증 보내기에 실패했습니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // onStart() - 기존 사용자 관련 안 함.




}