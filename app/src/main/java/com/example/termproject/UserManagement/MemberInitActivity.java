package com.example.termproject.UserManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.termproject.R;

public class MemberInitActivity extends AppCompatActivity {

    private static final String TAG = "MemberInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity_minit);
        Button startButton = findViewById(R.id.startButton);
    }
    private void toast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}