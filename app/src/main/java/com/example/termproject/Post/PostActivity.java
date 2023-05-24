package com.example.termproject.Post;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.termproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class PostActivity extends AppCompatActivity {

    EditText hashTag, contents;
    Button upload;
    DocumentReference userRef;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity);

        hashTag = findViewById(R.id.hashTag);
        contents = findViewById(R.id.contents);

        upload = findViewById(R.id.upload);
        upload.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.upload:
                    new AlertDialog.Builder(PostActivity.this)
                            .setTitle("알람 팝업")
                            .setMessage("게시하시겠습니까?")
                            .setPositiveButton("아니요", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            })
                            .setNegativeButton("네", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    userRef.collection("posts").add(new PostInfo(hashTag.getText().toString(),
                                                    contents.getText().toString(), FieldValue.serverTimestamp()))
                                            .addOnSuccessListener(documentReference -> {
                                                Toast.makeText(PostActivity.this, "게시되었습니다.",
                                                        Toast.LENGTH_SHORT).show();
                                            });
                                    finish();
                                }
                            })
                            .show();
                    break;
            }
        }
    };
}
