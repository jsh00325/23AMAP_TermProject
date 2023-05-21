package com.example.termproject.MyPage;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.termproject.R;
import com.example.termproject.UserManagement.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordResetActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
//    Toolbar toolbar;
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
//                finish();
//                return true;
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_activity_pwreset);

        findViewById(R.id.sendButton).setOnClickListener(onClickListener);
//
//        toolbar = findViewById(R.id.tool_bar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.sendButton) {
                send();
            }
        }
    };


    private void send(){

        String email = ((EditText) findViewById(R.id.emailEditText_password_reset)).getText().toString();

        if (email.length() > 0) {
//            Toast.makeText(PasswordResetActivity.this,"웹메일 속 링크를 클릭해주세요.\n스팸메일함에 메일이 있을 수 있습니다.",Toast.LENGTH_SHORT).show();
//
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {
                            Toast.makeText(PasswordResetActivity.this,"웹메일 속 링크를 클릭해주세요.\n스팸메일함에 메일이 있을 수 있습니다.",Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(PasswordResetActivity.this, LoginActivity.class);
//                                startActivity(intent);
                            finish();
                        }
                    });
        } else {
            Toast.makeText(PasswordResetActivity.this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

}