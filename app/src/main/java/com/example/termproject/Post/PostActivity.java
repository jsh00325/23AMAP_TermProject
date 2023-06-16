package com.example.termproject.Post;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.termproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import android.widget.Button;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

public class PostActivity extends AppCompatActivity {
    private ImageButton postButton;
    private ImageButton backButton;
    private ImageButton imageButton3;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ImageAdapter imageAdapter;
    private List<Bitmap> imagesList = new ArrayList<>();

    private static final int PICK_IMAGE_REQUEST = 1;

    private FirebaseFirestore firestore;
    private CollectionReference clubPostCollection;
    private String adminCategory;
    private String adminClub;
    private EditText editTextTextMultiLine;

    public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

        private List<Bitmap> imagesList;

        public ImageAdapter(List<Bitmap> imagesList) {
            this.imagesList = imagesList;
        }

        @NonNull
        @Override
        public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
            return new ImageViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
            Bitmap imageBitmap = imagesList.get(position);
            Glide.with(holder.itemView.getContext())
                    .load(imageBitmap)
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return imagesList.size();
        }

        public class ImageViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public ImageViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
                ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                int imageSize = (int) (itemView.getResources().getDisplayMetrics().widthPixels * 0.2); // 60% of screen width
                layoutParams.width = imageSize;
                layoutParams.height = imageSize;
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(layoutParams);
            }
        }
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity);

        Button postButton = findViewById(R.id.button2);
        backButton = findViewById(R.id.imageButton);
        imageButton3 = findViewById(R.id.imageButton3);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        imageAdapter = new ImageAdapter(imagesList);
        recyclerView.setAdapter(imageAdapter);
        editTextTextMultiLine = findViewById(R.id.editTextTextMultiLine);

        Intent intent = getIntent();
        adminCategory = intent.getStringExtra("club_category");
        adminClub = intent.getStringExtra("club_name");

        // Firestore 초기화
        firestore = FirebaseFirestore.getInstance();
        clubPostCollection = firestore.collection("club_post");

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imagesList.size() < 1) {
                    Toast.makeText(PostActivity.this, "사진을 한 장 이상 첨부하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    showConfirmationDialog();
                }
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
                        String category = adminCategory;
                        String clubName = adminClub.replace(" ", "_");
                        String postContent = editTextTextMultiLine.getText().toString(); // Get text from EditText
                        uploadPost(category, clubName, postContent);
                    }
                })

                .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();
    }

    private void uploadPost(final String category, final String clubName, final String postContent) {
        List<String> likeUsers = new ArrayList<>();
        // Add more like users if needed

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = currentUser.getUid();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        final String uptime = dateFormat.format(new Date());

        Map<String, Object> postData = new HashMap<>();
        postData.put("category", category);
        postData.put("club_name", clubName);
        postData.put("like_user", likeUsers);
        postData.put("main_text", postContent);
        postData.put("uptime", FieldValue.serverTimestamp());
        postData.put("userID", userID);

        // Firestore에 게시물 데이터 추가
        clubPostCollection.add(postData)
                .addOnSuccessListener(documentReference -> {
                    // 게시물 문서 ID
                    String postId = documentReference.getId();

                    // 이미지 업로드
                    uploadImages(postId);
                })
                .addOnFailureListener(e -> {
                    // 게시물 추가 실패 시 처리
                    Toast.makeText(PostActivity.this, "게시물 업로드 실패", Toast.LENGTH_SHORT).show();
                });
    }

    private void saveImageUrl(String postId, List<String> imageUrls) {
        // 게시물 문서 참조
        DocumentReference postRef = clubPostCollection.document(postId);

        // Firestore에 이미지 URL 저장
        postRef.update("imageURL", imageUrls)
                .addOnSuccessListener(aVoid -> {
                    // 이미지 URL 저장 완료
                })
                .addOnFailureListener(e -> {
                    // 이미지 URL 저장 실패
                });
    }

    private void uploadImages(String postId) {
        // Firebase Storage 참조 가져오기
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        // 이미지 업로드를 위한 변수 초기화
        int numImages = imagesList.size();
        AtomicInteger uploadedImagesCount = new AtomicInteger(0);
        List<String> imageUrls = new ArrayList<>(); // Image URLs

        for (int i = 0; i < numImages; i++) {
            final Bitmap imageBitmap = imagesList.get(i);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageData = baos.toByteArray();

            final String imageName = "image_" + i + ".jpg";
            final StorageReference imageRef = storageRef.child("images/" + postId + "/" + imageName);

            // 이미지 회전 정보 확인
            Uri imageUri = getImageUri(imageBitmap);
            int rotation = getRotationFromExif(imageUri);

            // 이미지 회전 처리
            Bitmap rotatedBitmap = rotateImage(imageBitmap, rotation);

            // 회전된 이미지를 업로드
            ByteArrayOutputStream rotatedBaos = new ByteArrayOutputStream();
            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, rotatedBaos);
            byte[] rotatedImageData = rotatedBaos.toByteArray();

            UploadTask uploadTask = imageRef.putBytes(rotatedImageData);
            final int finalI = i;
            uploadTask.addOnSuccessListener(taskSnapshot -> {
                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();
                    imageUrls.add(imageUrl); // Add URL to the list

                    // 이미지 업로드 완료 수 증가
                    int count = uploadedImagesCount.incrementAndGet();

                    // 마지막 이미지 업로드 완료 시 처리
                    if (count == numImages) {
                        saveImageUrl(postId, imageUrls); // Save image URLs to Firestore
                        Toast.makeText(PostActivity.this, "게시물이 올라갔습니다.", Toast.LENGTH_SHORT).show();
                        imagesList.clear();
                        imageAdapter.notifyDataSetChanged();
                        setResult(RESULT_OK);
                        finish();
                    }
                });
            }).addOnFailureListener(e -> {
                // 이미지 업로드 실패 시 처리
                e.printStackTrace();
            });
        }
    }

    private Uri getImageUri(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    private int getRotationFromExif(Uri imageUri) {
        int rotation = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(imageUri.getPath());
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotation = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotation = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotation = 270;
                    break;
                default:
                    rotation = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotation;
    }

    private Bitmap rotateImage(Bitmap bitmap, int rotation) {
        if (rotation != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(rotation);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        return bitmap;
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
