package com.example.termproject.UserManagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.termproject.MainActivity;
import com.example.termproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private TextView id;
    private TextView password;
    private Button loginButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // 다크모드 해제
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        mAuth = FirebaseAuth.getInstance();
        id = findViewById(R.id.idEditText_login);
        password = findViewById(R.id.passwordEditText_login);
        loginButton = findViewById(R.id.loginButton);

        TextWatcher loginTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String inputId = id.getText().toString().trim();
                String inputPassword = password.getText().toString().trim();

                if (!inputId.isEmpty() && !inputPassword.isEmpty()) {
                    loginButton.setBackgroundColor(Color.parseColor("#00BFFF"));
                } else {
                    loginButton.setBackgroundColor(Color.GRAY);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        id.addTextChangedListener(loginTextWatcher);
        password.addTextChangedListener(loginTextWatcher);

        loginButton.setOnClickListener(view -> {
            // 사용자가 입력한 아이디와 비밀번호 가져오기
            String inputId = id.getText().toString().trim();
            String inputPassword = password.getText().toString().trim();

            // 아이디와 비밀번호 유효성 검사
            if (inputId.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(LoginActivity.this, "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                // Firebase Authentication을 사용한 로그인 처리
                mAuth.signInWithEmailAndPassword(inputId, inputPassword)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null && user.isEmailVerified()) {
                                    // 이메일 인증이 완료된 회원인 경우 로그인 성공
                                    Toast.makeText(LoginActivity.this, "로그인이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // 이메일 인증이 되지 않은 회원인 경우 로그인 실패
                                    mAuth.signOut(); // 로그인 시도 후 로그아웃 처리
                                    Toast.makeText(LoginActivity.this, "이메일 인증이 필요합니다.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // 로그인 실패
                                Toast.makeText(LoginActivity.this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        findViewById(R.id.gotopasswordResetButton).setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, PasswordResetActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.goToSignUp).setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    long time = 0;
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        if(System.currentTimeMillis()-time>=1000) {
            time=System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"뒤로 가기 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
        else if(System.currentTimeMillis()-time<1000){ // 뒤로 가기 한번 더 눌렀을때의 시간간격 텀이 1초
            finishAffinity();
            System.runFinalization();
            System.exit(0);
        }
    }
}
