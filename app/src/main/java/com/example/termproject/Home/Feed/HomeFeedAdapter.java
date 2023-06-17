package com.example.termproject.Home.Feed;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.termproject.ClubPage.ClubActivity;
import com.example.termproject.ClubPage.Feed.ClubpageCommentAdapter;
import com.example.termproject.ClubPage.Feed.ClubpageFeed;
import com.example.termproject.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kr.co.prnd.readmore.ReadMoreTextView;
import me.relex.circleindicator.CircleIndicator3;

public class HomeFeedAdapter extends RecyclerView.Adapter<HomeFeedAdapter.HomeFeedViewHolder> {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<String> documentIDs;
    private Context context;
    private FirebaseAuth user = FirebaseAuth.getInstance();

    public HomeFeedAdapter(List<String> documentIDs) {
        this.documentIDs = documentIDs;
    }

    @NonNull
    @Override
    public HomeFeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new HomeFeedViewHolder(LayoutInflater.from(context).inflate(R.layout.home_item_feed, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeFeedViewHolder holder, int position) {
        String documentID = documentIDs.get(position);
        DocumentReference docRef = db.collection("club_post").document(documentID);
        String curUserId = user.getUid();

        holder.startShimmer();
        HomeFeedData dbData = new HomeFeedData();
        docRef.get().addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                DocumentSnapshot doc1 = task1.getResult();

                dbData.setClubName(doc1.getString("club_name"));
                dbData.setUploadTime(doc1.getTimestamp("uptime"));
                dbData.setFeedImageURLs((List<String>)doc1.get("imageURL"));
                dbData.setMainText(doc1.getString("main_text").replace("\\n", "\n"));
                List<String> userLikeList = (List<String>)doc1.get("like_user");
                dbData.setLike(userLikeList.contains(curUserId));
                dbData.setLikeCount(userLikeList.size());

                db.collection("club_list").whereEqualTo("club_name", dbData.getClubName()).get()
                    .addOnCompleteListener(task2 -> {
                        if (task2.isSuccessful()) {
                            DocumentSnapshot doc2 = task2.getResult().getDocuments().get(0);
                            dbData.setClubImageURL(doc2.getString("image_url"));

                            List<String> commentID = new ArrayList<>();
                            db.collection("comment").whereEqualTo("postID", documentID).orderBy("time").get().addOnCompleteListener(task -> {
                                if (task.isSuccessful()){
                                    QuerySnapshot querySnapshot = task.getResult();
                                    for (QueryDocumentSnapshot document : querySnapshot)
                                        commentID.add(document.getId());
                                } else Log.d("commentLoading", "DB에서 댓글 목록 가져오기 실패");
                                dbData.setCommentDocIDs(commentID);

                                holder.setInformationToView(dbData);
                                holder.endShimmer();

                                Log.d("commentLoading", commentID.size() + "개 댓글");
                            });
                        } Log.d("HomeFeed", "동아리 프로필 사진 접근 실패...");
                    });
            } Log.d("HomeFeed", "게시글 접근 실패...");
        });

        holder.clubInfoLayout.setOnClickListener(view -> {
            Intent it = new Intent(context, ClubActivity.class);
            it.putExtra("club_name", dbData.getClubName());
            context.startActivity(it);
        });

        holder.likeBtn.setOnClickListener(view -> {
            if (dbData.isLike()) {
                dbData.setLike(false);
                dbData.setLikeCount(dbData.getLikeCount() - 1);
                holder.setLikeBtn(false);
                docRef.update("like_user", FieldValue.arrayRemove(curUserId));
            } else {
                dbData.setLike(true);
                dbData.setLikeCount(dbData.getLikeCount() + 1);
                holder.setLikeBtn(true);
                docRef.update("like_user", FieldValue.arrayUnion(curUserId));
            }
            holder.setLikeCount(dbData.getLikeCount());
        });

        holder.commentArea.setOnClickListener(view -> {
            // TODO : 나중에 이미지가 아니라 게시글 아이디로 넘기기
            Intent intent = new Intent(context, ClubpageFeed.class);
            intent.putExtra("imageUrl", dbData.getFeedImageURLs().get(0));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return documentIDs.size();
    }

    public class HomeFeedViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mainItem;
        private LinearLayout clubInfoLayout, commentArea;
        private ShimmerFrameLayout loadItem;
        private CircleImageView clubImageView;
        private TextView clubNameView, upTimeView, likeCountView, commentCountView;
        private ViewPager2 postImgView;
        private CircleIndicator3 indicator;
        private ImageButton likeBtn;
        private ReadMoreTextView postTextView;

        private HomeFeedViewHolder(@NonNull View itemView) {
            super(itemView);
            mainItem = (LinearLayout) itemView.findViewById(R.id.home_item_main);
            clubInfoLayout = (LinearLayout) itemView.findViewById(R.id.home_item_clubInfo);
            loadItem = (ShimmerFrameLayout) itemView.findViewById(R.id.home_item_shimmer);
            clubImageView = (CircleImageView) itemView.findViewById(R.id.home_item_clubImage);
            clubNameView = (TextView) itemView.findViewById(R.id.home_item_clubName);
            upTimeView = (TextView) itemView.findViewById(R.id.home_item_upTime);
            postImgView = (ViewPager2) itemView.findViewById(R.id.home_item_viewpager);
            indicator = (CircleIndicator3) itemView.findViewById(R.id.home_item_circleIndicator);
            likeBtn = (ImageButton) itemView.findViewById(R.id.home_item_likeBtn);
            commentCountView = (TextView) itemView.findViewById(R.id.home_item_commentCount);
            commentArea = (LinearLayout) itemView.findViewById(R.id.home_item_comment_linear);
            likeCountView = (TextView) itemView.findViewById(R.id.home_item_likeCount);
            postTextView = (ReadMoreTextView) itemView.findViewById(R.id.home_item_postText);
        }

        private void setInformationToView(HomeFeedData feedData) {
            if (feedData.getClubImageURL().equals("")) clubImageView.setImageResource(R.drawable.blank_user);
            else Glide.with(context).load(feedData.getClubImageURL()).circleCrop().into(clubImageView);
            clubNameView.setText(feedData.getClubName());
            upTimeView.setText(feedData.convertTimestamp());
            postImgView.setAdapter(new HomeFeedImageAdapter(feedData.getFeedImageURLs()));
            indicator.setViewPager(postImgView);
            postTextView.setText(feedData.getMainText());
            setLikeCount(feedData.getLikeCount());
            setLikeBtn(feedData.isLike());
            setCommentCount(feedData.getCommentDocIDs().size());
        }

        private void startShimmer() {
            loadItem.startShimmer();
            loadItem.setVisibility(View.VISIBLE);
            mainItem.setVisibility(View.GONE);
        }
        private void endShimmer() {
            loadItem.stopShimmer();
            loadItem.setVisibility(View.GONE);
            mainItem.setVisibility(View.VISIBLE);
        }

        private void setLikeBtn(boolean like) {
            if (like) likeBtn.setImageResource(R.drawable.heart_fill);
            else likeBtn.setImageResource(R.drawable.heart_empty);
        }
        private void setLikeCount(int count) {
            String countStr;
            if (count < 1e3) countStr = Integer.toString(count);
            else if (count < 1e6) countStr = String.format("%.1fK", count / 1000.0);
            else countStr = String.format("%.1fM", count / 1000000.0);
            likeCountView.setText(countStr);
        }
        private void setCommentCount(int count) {
            String countStr;
            if (count < 1e3) countStr = Integer.toString(count);
            else if (count < 1e6) countStr = String.format("%.1fK", count / 1000.0);
            else countStr = String.format("%.1fM", count / 1000000.0);
            commentCountView.setText(countStr);
        }
    }
}
