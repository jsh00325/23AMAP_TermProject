package com.example.termproject.Post;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.termproject.R;

public class PostActivity extends AppCompatActivity {

    private EditText editText;
    private ImageButton postButton;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity);

        editText = findViewById(R.id.editTextTextMultiLine);
        postButton = findViewById(R.id.imageButton2);
        backButton = findViewById(R.id.imageButton);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmationDialog();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Close the PostActivity and go back to the previous activity
            }
        });
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("알람 팝업")
                .setMessage("게시하시겠습니까?")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Handle posting logic here
                        String postContent = editText.getText().toString();
                        // Do something with the post content, e.g., upload to Firebase
                        Toast.makeText(PostActivity.this, "게시물이 올라갔습니다.", Toast.LENGTH_SHORT).show();
                        finish(); // Close the activity after posting
                    }
                })
                .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Do nothing or handle cancelation
                    }
                })
                .show();
    }
}
