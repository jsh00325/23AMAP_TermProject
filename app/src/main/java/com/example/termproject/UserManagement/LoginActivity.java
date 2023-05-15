package com.example.termproject.UserManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.termproject.MainActivity;
import com.example.termproject.R;
/*
import com.example.termproject.YoonJinTestActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
*/

public class LoginActivity extends AppCompatActivity {
    // private FirebaseAuth mAuth;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        // mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.loginButton).setOnClickListener(mOnClickListener);
        findViewById(R.id.gotopasswordResetButton).setOnClickListener(mOnClickListener);
        findViewById(R.id.goToSignUp).setOnClickListener(mOnClickListener);
    }


    View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            Intent intent;
            int id = view.getId();
            if (id == R.id.loginButton) {
                login();
            } else if (id == R.id.gotopasswordResetButton) {// TODO : 액티비티 만들어서 연결해두기
                // intent = new Intent(LoginActivity.this, PasswordResetActivity.class);
                // startActivity(intent);
            } else if (id == R.id.goToSignUp) {
                intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        }
    };

    private void login() {
        String email = ((EditText) findViewById(R.id.emailEditText_login)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordEditText_login)).getText().toString();
        /*
        if (email.length() > 0 && password.length() > 0) {

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(LoginActivity.this,"로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            } else {
                                if (task.getException() != null) {
                                    Toast.makeText(LoginActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        } else {
            Toast.makeText(LoginActivity.this,"이메일 또는 비밀번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
        }
        */
    }

    private void toast(String text){
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}