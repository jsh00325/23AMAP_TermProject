package com.example.termproject.Category.SubCategoryList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.termproject.ClubPage.ClubActivity;
import com.example.termproject.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryClublistAdapter extends RecyclerView.Adapter<CategoryClublistAdapter.ClublistViewHolder> {
    final private ArrayList<CategoryClubData> clubName;
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

        if (curData.getImageURL().equals("")) holder.clubImageView.setImageResource(R.drawable.blank_user);
        else Glide.with(context).load(curData.getImageURL()).circleCrop().into(holder.clubImageView);
        holder.clubNameView.setText(curData.getClubName());
        holder.clubMainCategoryView.setText(curData.getMainCategory());
        holder.clubSubCategoryView.setText(curData.getSubCategory());

        holder.clubView.setOnClickListener(view -> {
            Intent it = new Intent(context, ClubActivity.class);
            it.putExtra("club_name", curData.getClubName());
            context.startActivity(it);
        });
    }

    @Override
    public int getItemCount() {
        return clubName.size();
    }

    public static class ClublistViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout clubView;
        CircleImageView clubImageView;
        TextView clubNameView;
        TextView clubMainCategoryView;
        TextView clubSubCategoryView;

        public ClublistViewHolder(View itemView) {
            super(itemView);
            clubView = (ConstraintLayout) itemView.findViewById(R.id.category_clubitem_Cl); 
            clubImageView = (CircleImageView) itemView.findViewById(R.id.category_club_image);
            clubNameView = (TextView) itemView.findViewById(R.id.category_club_name);
            clubMainCategoryView = (TextView) itemView.findViewById(R.id.category_club_main);
            clubSubCategoryView = (TextView) itemView.findViewById(R.id.category_club_sub);
        }

    }
}
