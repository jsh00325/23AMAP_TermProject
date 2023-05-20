package com.example.termproject.Home.Feed;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.termproject.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kr.co.prnd.readmore.ReadMoreTextView;
import me.relex.circleindicator.CircleIndicator3;

public class HomeFeedAdapter extends RecyclerView.Adapter<HomeFeedAdapter.HomeFeedViewHolder> {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<String> documentIDs;
    private Context context;

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

        holder.startShimmer();
        HomeFeedData dbData = new HomeFeedData();
        db.collection("club_post").document(documentID).get().addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                DocumentSnapshot doc1 = task1.getResult();

                dbData.setClubName(doc1.getString("club_name"));
                dbData.setUploadTime(doc1.getTimestamp("uptime"));
                dbData.setFeedImageURLs((List<String>)doc1.get("imageURL"));
                dbData.setMainText(doc1.getString("main_text").replace("\\n", "\n"));

                db.collection("club_list").whereEqualTo("club_name", dbData.getClubName()).get()
                    .addOnCompleteListener(task2 -> {
                        if (task2.isSuccessful()) {
                            DocumentSnapshot doc2 = task2.getResult().getDocuments().get(0);
                            dbData.setClubImageURL(doc2.getString("image_url"));

                            holder.setInfomationToView(dbData);
                            holder.endShimmer();
                        } Log.d("HomeFeed", "동아리 프로필 사진 접근 실패...");
                    });
            } Log.d("HomeFeed", "게시글 접근 실패...");
        });
    }

    private synchronized HomeFeedData getFeedDataFromDB(String documentID) {
        HomeFeedData dbData = new HomeFeedData();
        db.collection("club_post").document(documentID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();

                dbData.setClubName(document.getString("club_name"));
                dbData.setUploadTime(document.getTimestamp("uptime"));
                dbData.setFeedImageURLs((List<String>)document.get("imageURL"));
                dbData.setMainText(document.getString("main_text").replace("\\n", "\n"));
            } else {
                Toast.makeText(context, "게시글 접근 실패...", Toast.LENGTH_SHORT).show();
            }
        });
        return dbData;
    }

    @Override
    public int getItemCount() {
        return documentIDs.size();
    }

    public class HomeFeedViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mainItem;
        ShimmerFrameLayout loadItem;
        CircleImageView clubImageView;
        TextView clubNameView, upTimeView;
        ViewPager2 postImgView;
        CircleIndicator3 indicator;
        ImageButton likeBtn;
        ReadMoreTextView postTextView;

        public HomeFeedViewHolder(@NonNull View itemView) {
            super(itemView);
            mainItem = (LinearLayout) itemView.findViewById(R.id.home_item_main);
            loadItem = (ShimmerFrameLayout) itemView.findViewById(R.id.home_item_shimmer);
            clubImageView = (CircleImageView) itemView.findViewById(R.id.home_item_clubImage);
            clubNameView = (TextView) itemView.findViewById(R.id.home_item_clubName);
            upTimeView = (TextView) itemView.findViewById(R.id.home_item_upTime);
            postImgView = (ViewPager2) itemView.findViewById(R.id.home_item_viewpager);
            indicator = (CircleIndicator3) itemView.findViewById(R.id.home_item_circleIndicator);
            likeBtn = (ImageButton) itemView.findViewById(R.id.home_item_likeBtn);
            postTextView = (ReadMoreTextView) itemView.findViewById(R.id.home_item_postText);
        }

        private void setInfomationToView(HomeFeedData feedData) {
            if (feedData.getClubImageURL().equals("")) clubImageView.setImageResource(R.drawable.blank_user);
            else Glide.with(context).load(feedData.getClubImageURL()).circleCrop().into(clubImageView);
            clubNameView.setText(feedData.getClubName());
            upTimeView.setText(feedData.convertTimestamp());
            postImgView.setAdapter(new HomeFeedImageAdapter(feedData.getFeedImageURLs()));
            indicator.setViewPager(postImgView);
            postTextView.setText(feedData.getMainText());
        }

        public void startShimmer() {
            loadItem.startShimmer();
            loadItem.setVisibility(View.VISIBLE);
            mainItem.setVisibility(View.GONE);
        }
        public void endShimmer() {
            loadItem.stopShimmer();
            loadItem.setVisibility(View.GONE);
            mainItem.setVisibility(View.VISIBLE);
        }
    }
}
