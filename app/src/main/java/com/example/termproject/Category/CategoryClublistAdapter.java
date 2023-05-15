package com.example.termproject.Category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.termproject.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryClublistAdapter extends RecyclerView.Adapter<CategoryClublistAdapter.ClublistViewHolder> {
    private ArrayList<CategoryClubData> clubName;
    private Context context;

    public CategoryClublistAdapter(ArrayList<CategoryClubData> clubName) {
        this.clubName = clubName;
    }

    @NonNull
    @Override
    public ClublistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ClublistViewHolder(LayoutInflater.from(context).inflate(R.layout.category_item_clublist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClublistViewHolder holder, int position) {
        CategoryClubData curData = clubName.get(holder.getAdapterPosition());

        if (curData.imageURL == "") holder.clubImageView.setImageResource(R.drawable.mypage);
        else Glide.with(context).load(curData.imageURL).circleCrop().into(holder.clubImageView);
        holder.clubNameView.setText(curData.clubName);
        holder.clubMainCategoryView.setText(curData.mainCategory);
        holder.clubSubCategoryView.setText(curData.subCategory);
    }

    @Override
    public int getItemCount() {
        return clubName.size();
    }

    public static class ClublistViewHolder extends RecyclerView.ViewHolder {
        CircleImageView clubImageView;
        TextView clubNameView;
        TextView clubMainCategoryView;
        TextView clubSubCategoryView;

        public ClublistViewHolder(View itemView) {
            super(itemView);
            clubImageView = (CircleImageView) itemView.findViewById(R.id.category_club_image);
            clubNameView = (TextView) itemView.findViewById(R.id.category_club_name);
            clubMainCategoryView = (TextView) itemView.findViewById(R.id.category_club_main);
            clubSubCategoryView = (TextView) itemView.findViewById(R.id.category_club_sub);
        }

    }
}
