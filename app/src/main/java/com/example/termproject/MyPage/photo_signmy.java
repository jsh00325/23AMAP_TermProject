package com.example.termproject.MyPage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.termproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class photo_signmy extends AppCompatActivity {
    private ImageView profileImageView;
    private StorageReference storageRef;
    private Button camera;
    private Button gallery;
    private Button enroll;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mDb;

    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int GALLERY_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_sign_phto_acticity);

        // Firebase 인증 및 Firestore 초기화
        mAuth = FirebaseAuth.getInstance();
        mDb = FirebaseFirestore.getInstance();

        // Firebase Storage의 레퍼런스 설정
        storageRef = FirebaseStorage.getInstance().getReference();

        // ImageView 초기화 -> 안 바꾸면 그냥 blank
        profileImageView = findViewById(R.id.profileImageView);
        camera = findViewById(R.id.cameraButton);
        gallery = findViewById(R.id.galleryButton);
        enroll = findViewById(R.id.startButton2);

        camera.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
                    return;
                }
            }
            // 카메라에서 사진을 찍기 위한 Intent 생성
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                // Intent를 처리할 수 있는 앱이 있는 경우에만 startActivityForResult 호출
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            } else {
                Toast.makeText(photo_signmy.this, "사진을 찍을 수 있는 앱이 설치되어 있지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        });


        gallery.setOnClickListener(view -> {
            //갤러리에서 고른 사진 하나 profileImageView에 저장
            // profileImageView "@+id/profileImageView"에 넣기
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, GALLERY_REQUEST_CODE);
        });

        enroll.setOnClickListener(view -> {
            // Firebase Firestore에 이미지 URL 등록
            final String imageUrl = ""; // 이미지 URL을 저장할 변수

            // Firebase Storage에 이미지 업로드
            if (profileImageView.getDrawable() != null) {
                // ImageView에서 Bitmap 추출
                BitmapDrawable drawable = (BitmapDrawable) profileImageView.getDrawable();
                Bitmap imageBitmap = drawable.getBitmap();

                // Bitmap을 ByteArrayOutputStream으로 변환
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageData = baos.toByteArray();

                // Firebase Storage에 업로드
                StorageReference imageRef = storageRef.child("profile_images/" + mAuth.getUid() + ".jpg");
                UploadTask uploadTask = imageRef.putBytes(imageData);
                uploadTask.addOnSuccessListener(taskSnapshot -> {
                    // 업로드 성공 시 이미지 URL 가져오기
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        final String finalImageUrl = uri.toString(); // final 변수로 선언

                        // Firebase Firestore에 이미지 URL 등록
                        String userId = mAuth.getUid();
                        if (userId != null) {
                            mDb.collection("users").document(userId)
                                    .update("imageUrl", finalImageUrl) // finalImageUrl 사용
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(photo_signmy.this, "프로필 사진이 변경되었습니다.", Toast.LENGTH_SHORT).show();
                                        // LoginActivity로 이동
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(photo_signmy.this, "이미지 등록 실패", Toast.LENGTH_SHORT).show();
                                        finish();
                                    });
                        }
                    });
                }).addOnFailureListener(e -> {
                    Toast.makeText(photo_signmy.this, "이미지 업로드 실패", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                });
            }
            //check
            //profileImageView.getDrawable() == null
            else {
                Toast.makeText(photo_signmy.this, "기본 프로필로 변경되었습니다.", Toast.LENGTH_SHORT).show();
                // LoginActivity로 이동
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE && data != null) {
                // 카메라에서 이미지를 가져온 경우
                Bundle extras = data.getExtras();
                if (extras != null && extras.containsKey("data")) {
                    Bitmap imageBitmap = (Bitmap) extras.get("data");

                    profileImageView.setImageBitmap(imageBitmap);
                }
            } else if (requestCode == GALLERY_REQUEST_CODE && data != null) {
                // 갤러리에서 이미지를 가져온 경우
                Uri imageUri = data.getData();
                try {
                    Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    profileImageView.setImageBitmap(imageBitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // 프로필 이미지 크기 통일
            int imageSize = getResources().getDimensionPixelSize(R.dimen.profile_image_size);
            profileImageView.getLayoutParams().width = imageSize;
            profileImageView.getLayoutParams().height = imageSize;
            profileImageView.setBackgroundResource(R.drawable.make_roundimage);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 허용된 경우
                Toast.makeText(this, "카메라 권한이 허용되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                // 권한이 거부된 경우
                Toast.makeText(this, "카메라 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}

