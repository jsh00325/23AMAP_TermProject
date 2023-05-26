package com.example.termproject.UserManagement;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.text.TextWatcher;
import android.text.Editable;
import android.graphics.Color;

import com.example.termproject.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.example.termproject.R;
import com.example.termproject.UserManagement.SignUpActivity;
import com.example.termproject.UserManagement.PasswordResetActivity;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private EditText id;
    private EditText password;
    private Button loginButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

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
            String inputId = id.getText().toString().trim();
            String inputPassword = password.getText().toString().trim();

            // 이메일 형식인지 확인
            if (!isEmailValid(inputId)) {
                Toast.makeText(LoginActivity.this, "유효한 이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                return; // 유효하지 않은 이메일인 경우, 함수를 종료하고 다시 입력받도록 합니다.
            }

            // 아이디와 비밀번호 유효성 검사
            if (inputId.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(LoginActivity.this, "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            }  else {
                // Firebase Authentication을 사용한 로그인 처리
                mAuth.signInWithEmailAndPassword(inputId, inputPassword)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // 로그인 성공
                                Toast.makeText(LoginActivity.this, "로그인이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
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
            //Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            //startActivity(intent);
        });
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }
}
