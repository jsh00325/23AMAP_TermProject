package com.example.termproject.UserManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.termproject.R;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordResetActivity extends AppCompatActivity {
    private EditText emailEditText;
    private Button resetButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passwordreset_activity);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.emailEditText);
        resetButton = findViewById(R.id.resetButton);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(PasswordResetActivity.this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // Firebase에 비밀번호 재설정 이메일 전송
                    mAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(PasswordResetActivity.this, "비밀번호 재설정 이메일을 보냈습니다. 이메일을 확인해주세요.", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(PasswordResetActivity.this, "비밀번호 재설정 이메일 보내기를 실패했습니다. 이메일 주소를 확인해주세요.", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}
