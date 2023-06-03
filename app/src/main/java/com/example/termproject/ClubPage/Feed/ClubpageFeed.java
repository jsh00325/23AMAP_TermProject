package com.example.termproject.ClubPage.Feed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.termproject.ClubPage.ClubActivity;
import com.example.termproject.R;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import kr.co.prnd.readmore.ReadMoreTextView;
import me.relex.circleindicator.CircleIndicator3;

public class ClubpageFeed extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Context context;
    private FirebaseAuth user = FirebaseAuth.getInstance();

    private CircleImageView clubImageView;
    private TextView clubNameView, upTimeView, likeCountView;
    private ImageButton likeBtn;
    private ReadMoreTextView postTextView;
    private ViewPager2 viewPager;
    private CircleIndicator3 circleIndicator;
    private LinearLayout clubInfoLayout;
    List<String> userLikeList;
    boolean like;
    int likeCount;
    private String imageUrl;
    FirebaseUser user11 = FirebaseAuth.getInstance().getCurrentUser();

    private List<String> imageUrls;
    private ClubpageFeedImageAdapter imageAdapter;
    DocumentSnapshot document;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clubpage_item_post);
        clubInfoLayout = (LinearLayout)findViewById(R.id.clubpage_item_clubInfo);
        // 뷰 초기화
        clubImageView = findViewById(R.id.clubpage_item_clubImage);
        clubNameView = findViewById(R.id.clubpage_item_clubName);
        upTimeView = findViewById(R.id.clubpage_item_upTime);
        likeBtn = findViewById(R.id.clubpage_item_likeBtn);
        likeCountView = findViewById(R.id.clubpage_item_likeCount);
        postTextView = findViewById(R.id.clubpage_item_postText);
        circleIndicator = findViewById(R.id.clubpage_item_circleIndicator);
        viewPager = findViewById(R.id.clubpage_item_viewpager);


        imageUrl = getIntent().getStringExtra("imageUrl");
        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();

        db.collection("club_post")
                .whereArrayContains("imageURL", imageUrl)
                .limit(1)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (!task.getResult().isEmpty()) {
                            document = task.getResult().getDocuments().get(0);

                            String clubName = document.getString("club_name");
                            List<String> imageUrls = (List<String>) document.get("imageURL");
                            Timestamp uploadTime = document.getTimestamp("uptime");
                            String mainText = document.getString("main_text").replace("\\n", "\n");
                            userLikeList = (List<String>) document.get("like_user");
                            like = userLikeList.contains(user1.getUid());
                            likeCount = userLikeList.size();
                            setLikeBtn(true);

                            db.collection("club_list").whereEqualTo("club_name", clubName).get()
                                    .addOnCompleteListener(task2 -> {
                                        if (task2.isSuccessful()) {
                                            if (!task2.getResult().isEmpty()) {
                                                DocumentSnapshot doc2 = task2.getResult().getDocuments().get(0);
                                                final String finalClubImageURL = doc2.getString("image_url");

                                                setViewData(document, imageUrl, imageUrls, finalClubImageURL, clubName, uploadTime, mainText);
                                            } else {
                                                Log.d("HomeFeed", "동아리 프로필 사진 접근 실패...");
                                            }
                                        } else {
                                            Log.d("HomeFeed", "동아리 프로필 사진 접근 실패...");
                                        }
                                    });
                        } else {
                            // 일치하는 문서가 없을 때 처리할 코드를 추가할 수 있습니다.
                            // 예: "일치하는 문서가 없습니다." 메시지 표시 등
                        }
                    } else {
                        Log.d("clubpageFeed", "Error getting documents: ", task.getException());
                    }
                });

        likeBtn.setOnClickListener(view -> {
            if (like) {
                like = false;
                likeCount = likeCount - 1;
                likeCountView.setText(String.valueOf(likeCount));
                setLikeBtn(like);

                document.getReference().update("like_user", FieldValue.arrayRemove(user1.getUid()))
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // 업데이트가 성공적으로 완료된 경우 실행할 코드
                            } else {
                                // 업데이트가 실패한 경우 실행할 코드
                            }
                        });
            } else {
                like = true;
                likeCount = likeCount + 1;
                likeCountView.setText(String.valueOf(likeCount));
                setLikeBtn(like);

                document.getReference().update("like_user", FieldValue.arrayUnion(user1.getUid()))
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // 업데이트가 성공적으로 완료된 경우 실행할 코드
                            } else {
                                // 업데이트가 실패한 경우 실행할 코드
                            }
                        });
            }
        });


    }
    public ClubpageFeed() {
        this.context = this;
    }

    private void setViewData(DocumentSnapshot document, String imageurl, List<String> imageUrls, String clubImageURL, String clubName, Timestamp uploadTime, String mainText) {
        // Set club image
        Glide.with(this).load(clubImageURL).into(clubImageView);


        // Set viewpager with images
        imageAdapter = new ClubpageFeedImageAdapter(imageUrls);
        viewPager.setAdapter(imageAdapter);
        circleIndicator.setViewPager(viewPager);
        likeCountView.setText(String.valueOf(likeCount));
        likeBtn.setImageResource(R.drawable.heart_fill);
        // Set club name
        clubNameView.setText(clubName);

        // Set upload time
        String formattedTime = convertTimestamp(uploadTime);
        upTimeView.setText(formattedTime);

        // Set main text
        postTextView.setText(mainText);


        clubInfoLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, ClubActivity.class);
            intent.putExtra("club_name", clubName);
            context.startActivity(intent);
        });

    }

    private void setLikeBtn(boolean like) {
        if (like)
            likeBtn.setImageResource(R.drawable.heart_fill);
        else
            likeBtn.setImageResource(R.drawable.heart_empty);
    }

    private String convertTimestamp(Timestamp timestamp) {
        Date date = timestamp.toDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault());
        return sdf.format(date);
    }
}


