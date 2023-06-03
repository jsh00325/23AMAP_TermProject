package com.example.termproject.ClubPage.Feed;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.termproject.R;

import java.util.List;

public class ClubpageFeedImageAdapter extends RecyclerView.Adapter<ClubpageFeedImageAdapter.FeedViewHolder> {
    private Context context;
    private List<String> sliderImage;

    public ClubpageFeedImageAdapter(List<String> sliderImage) {
        this.sliderImage = sliderImage;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new FeedViewHolder(LayoutInflater.from(context).inflate(R.layout.home_item_imageslider, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        String imgURL = sliderImage.get(position);

        Glide.with(context).load(imgURL).into(holder.feedImageView);
        holder.feedImageView.setOnClickListener(view -> {
            Intent it = new Intent(context, ClubpageFeedImageViewer.class);
            it.putExtra("ImageURL", imgURL);
            context.startActivity(it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        });
    }

    @Override
    public int getItemCount() {
        return sliderImage.size();
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder {
        private ImageView feedImageView;

        public FeedViewHolder(View itemView) {
            super(itemView);
            feedImageView = itemView.findViewById(R.id.imageSlider);
        }
    }
}
