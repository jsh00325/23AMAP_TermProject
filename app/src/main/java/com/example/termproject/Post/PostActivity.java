package com.example.termproject.Post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.termproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PostActivity extends AppCompatActivity {
    private EditText editText;
    private ImageButton postButton;
    private ImageButton backButton;
    private ImageButton imageButton3;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ImageAdapter imageAdapter;
    private List<Bitmap> imagesList = new ArrayList<>();

    private static final int PICK_IMAGE_REQUEST = 1;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity);

        editText = findViewById(R.id.editTextTextMultiLine);
        postButton = findViewById(R.id.imageButton2);
        backButton = findViewById(R.id.imageButton);
        imageButton3 = findViewById(R.id.imageButton3);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        imageAdapter = new ImageAdapter(imagesList);
        recyclerView.setAdapter(imageAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("club_post");

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmationDialog();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
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
                        String postContent = editText.getText().toString();
                        uploadPost(postContent);
                    }
                })
                .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();
    }

    private void uploadPost(final String postContent) {
        String category = "자연";
        String clubName = "코스모스";
        String[] likeUsers = {
                "testID",
                // Add more like users if needed
        };
        String userID = "test123";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 a hh:mm:ss", Locale.getDefault());
        final String uptime = dateFormat.format(new Date());

        final DatabaseReference postRef = databaseReference.push();
        postRef.child("category").setValue(category);
        postRef.child("club_name").setValue(clubName);
        postRef.child("like_users").setValue(likeUsers);
        postRef.child("main_text").setValue(postContent);
        postRef.child("uptime").setValue(uptime);
        postRef.child("userID").setValue(userID);

        // Save the number of images to be uploaded
        postRef.child("numImages").setValue(imagesList.size());

        // Upload images to Firebase Storage and get the download URLs
        for (int i = 0; i < imagesList.size(); i++) {
            final Bitmap imageBitmap = imagesList.get(i);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageData = baos.toByteArray();

            final String imageName = "image_" + i + ".jpg";
            final StorageReference imageRef = FirebaseStorage.getInstance().getReference().child("images").child(postRef.getKey()).child(imageName);

            final int finalI = i;
            UploadTask uploadTask = imageRef.putBytes(imageData);
            uploadTask.addOnSuccessListener(taskSnapshot -> {
                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();

                    // Update the image URL in Firebase database
                    postRef.child("imageURLs").child(String.valueOf(finalI)).setValue(imageUrl);

                    // Check if all images have been uploaded
                    postRef.child("numImages").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int numImages = dataSnapshot.getValue(Integer.class);
                            if (numImages > 0) {
                                numImages--;
                                postRef.child("numImages").setValue(numImages);

                                // If all images have been uploaded, show the success message and finish the activity
                                if (numImages == 0) {
                                    Toast.makeText(PostActivity.this, "게시물이 올라갔습니다.", Toast.LENGTH_SHORT).show();
                                    imagesList.clear();
                                    imageAdapter.notifyDataSetChanged();
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle database error
                        }
                    });
                });
            }).addOnFailureListener(e -> {
                // Handle the image upload failure
            });
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imagesList.add(bitmap);
                imageAdapter.notifyDataSetChanged();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
