package com.example.termproject.ClubPage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.termproject.ClubPage.Feed.ClubpageFeed;
import com.example.termproject.R;

import java.util.List;

public class ClubPostListAdapter extends RecyclerView.Adapter<ClubPostListAdapter.ViewHolder> {

    private List<String> imageUrlList;
    private Context context;

    public ClubPostListAdapter(List<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_activity_likeitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imageUrl = imageUrlList.get(position);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(context).load(imageUrl).into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.png_06);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클릭된 게시물의 정보를 가져옴
                String selectedImageUrl = imageUrlList.get(holder.getAdapterPosition());

                // clubpageFeed 액티비티를 실행하고 게시물 정보를 전달
                Intent intent = new Intent(context, ClubpageFeed.class);
                intent.putExtra("imageUrl", selectedImageUrl);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageUrlList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}

