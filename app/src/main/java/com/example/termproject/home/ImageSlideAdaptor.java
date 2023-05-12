package com.example.termproject.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.termproject.R;

public class ImageSlideAdaptor extends RecyclerView.Adapter<ImageSlideAdaptor.FeedViewHolder> {
    // https://android-dev.tistory.com/12 참고
    private Context context;
    private String[] sliderImage;

    public ImageSlideAdaptor(Context context, String[] sliderImage) {
        this.context = context;
        this.sliderImage = sliderImage;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider, parent, false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        holder.bindSliderImage(sliderImage[position]);
    }

    @Override
    public int getItemCount() {
        return sliderImage.length;
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;

        public FeedViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.imageSlider);
        }

        public void bindSliderImage(String imageURL) {
            Glide.with(context).load(imageURL).into(iv);
        }
    }
}
