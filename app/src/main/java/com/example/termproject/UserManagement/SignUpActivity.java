package com.example.termproject.UserManagement;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.example.termproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private EditText passwordcheckText;
    private Button signUpButton;
    private FirebaseUser mUser;
    private static final String TAG = "SignUpActivity";
    private Long mLastClickTime = 10000L;
    private EditText nameEditText, schoolNumEditText, departmentEditText, phoneNumEditText;
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

        // 다크모드 해제
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        toolbar = findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        mDb = FirebaseFirestore.getInstance();
        mUser = mAuth.getCurrentUser();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordcheckText = findViewById(R.id.passwordcheckEditText);
        signUpButton = findViewById(R.id.startButton);

        nameEditText = findViewById(R.id.nameText);
        schoolNumEditText = findViewById(R.id.snumText);
        departmentEditText = findViewById(R.id.majorText);
        phoneNumEditText = findViewById(R.id.numberText);

        signUpButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String check = passwordcheckText.getText().toString();

            if (password.equals(check)) {
                signUp(email, password);
            } else {
                Toast.makeText(getApplicationContext(), "비밀번호 확인을 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
        });

        //random 이 부분 일단 제외

    }


    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void signUp(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("이메일을 입력하세요.");
            emailEditText.requestFocus();
            return;
        }

        if (!isValidEmail(email)) {
            emailEditText.setError("유효한 이메일을 입력하세요.");
            emailEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("비밀번호를 입력하세요.");
            passwordEditText.requestFocus();
            return;
        }

        if (password.length() < 6) {
            passwordEditText.setError("비밀번호는 최소 6자 이상이어야 합니다.");
            passwordEditText.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 회원가입 성공
                            //Toast.makeText(getApplicationContext(), "이메일로 인증을 완료해주세요.", Toast.LENGTH_SHORT).show();
                            sendEmailVerification();
                        } else {
                            // 회원가입 실패
                            Toast.makeText(getApplicationContext(), "회원가입에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // 이메일 인증 메일 전송
    private void sendEmailVerification() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // 이메일 인증 메일 전송 성공
                            //Toast.makeText(getApplicationContext(), "이메일로 인증 링크가 전송되었습니다.", Toast.LENGTH_SHORT).show();
                            profileUpdate();
                        } else {
                            // 이메일 인증 메일 전송 실패
                            Toast.makeText(getApplicationContext(), "이메일 전송에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "회원가입에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private void profileUpdate() {

        String name = nameEditText.getText().toString();
        String schoolNum = schoolNumEditText.getText().toString();
        String department = departmentEditText.getText().toString();
        String phoneNum = phoneNumEditText.getText().toString();


        if (name.length() > 0 && schoolNum.length() == 10 && department.length() > 0 && phoneNum.length() >= 10) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            if (user != null) {
                //이름, 학번, 학과, 전화번호
                MemberInfo memberInfo = new MemberInfo(user.getUid(), name, schoolNum, department, phoneNum);
                db.collection("users").document(user.getUid()).set(memberInfo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                //회원 등록 성공 로직
                                Toast.makeText(getApplicationContext(), "이메일 인증으로 회원가입을 완료하세요", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                                Toast.makeText(getApplicationContext(), "회원 정보 임시 저장에 실패하였습니다", Toast.LENGTH_SHORT).show();

                            }
                        });
            }

        } else {
            Toast.makeText(getApplicationContext(), "회원 정보 등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
        }
    }



    // 이메일 인증 완료 시 회원 등록 처리

    /*
    private void registerUserAfterEmailVerification() {
        // 현재 사용자 가져오기
        FirebaseUser user = mAuth.getCurrentUser();

        // 사용자가 있고 이메일 인증이 완료된 경우에만 회원 등록 처리
        if (user != null && user.isEmailVerified()) {
            // 회원 등록 처리 코드 작성
            // 파이어베이스 user에 사용자 등록
            // ...

            Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "이메일 인증을 완료해주세요.", Toast.LENGTH_SHORT).show();
        }
    }
    */

    // onStart() - 기존 사용자 관련 안 함.




}