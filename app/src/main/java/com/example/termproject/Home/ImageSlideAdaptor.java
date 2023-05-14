package com.example.termproject.Home;

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
import com.example.termproject.view.FeedImageViewer;

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
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent it = new Intent(context, FeedImageViewer.class);
                    it.putExtra("ImageURL", imageURL);
                    context.startActivity(it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            });
        }
    }
}
