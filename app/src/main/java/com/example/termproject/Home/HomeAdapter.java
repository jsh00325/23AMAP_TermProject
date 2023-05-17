package com.example.termproject.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.termproject.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kr.co.prnd.readmore.ReadMoreTextView;
import me.relex.circleindicator.CircleIndicator3;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeFeedViewHolder> {
    private List<String> documentIDs;
    private Context context;

    public HomeAdapter(List<String> documentIDs) {
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
        // TODO : documentID를 통해 db에서 정보 불러오기!
        holder.postTextView.setText(documentID);

        List<String> images = new ArrayList<>(Arrays.asList(
            "https://cdn.pixabay.com/photo/2023/04/22/10/01/insect-7943499_960_720.jpg",
            "https://cdn.pixabay.com/photo/2023/03/27/17/40/tree-7881297_960_720.jpg",
            "https://cdn.pixabay.com/photo/2023/04/05/13/01/animal-7901464_960_720.jpg",
            "https://cdn.pixabay.com/photo/2023/04/21/11/51/flower-7941764_960_720.jpg"
        ));

        holder.postImgView.setAdapter(new HomeFeedImageAdapter(images));
        holder.indicator.setViewPager(holder.postImgView);
    }

    @Override
    public int getItemCount() {
        return documentIDs.size();
    }

    public class HomeFeedViewHolder extends RecyclerView.ViewHolder {
        CircleImageView clubImageView;
        TextView clubNameView, upTimeView;
        ViewPager2 postImgView;
        CircleIndicator3 indicator;
        ImageButton likeBtn;
        ReadMoreTextView postTextView;

        public HomeFeedViewHolder(@NonNull View itemView) {
            super(itemView);
            clubImageView = (CircleImageView) itemView.findViewById(R.id.home_item_clubImage);
            clubNameView = (TextView) itemView.findViewById(R.id.home_item_clubName);
            upTimeView = (TextView) itemView.findViewById(R.id.home_item_upTime);
            postImgView = (ViewPager2) itemView.findViewById(R.id.home_item_viewpager);
            indicator = (CircleIndicator3) itemView.findViewById(R.id.home_item_circleIndicator);
            likeBtn = (ImageButton) itemView.findViewById(R.id.home_item_likeBtn);
            postTextView = (ReadMoreTextView) itemView.findViewById(R.id.home_item_postText);
        }
    }
}
