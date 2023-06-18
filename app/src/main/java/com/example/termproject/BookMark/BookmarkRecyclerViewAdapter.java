package com.example.termproject.BookMark;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.termproject.ClubPage.ClubActivity;
import com.example.termproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class BookmarkRecyclerViewAdapter extends RecyclerView.Adapter<BookmarkRecyclerViewAdapter.ViewHolder> {

    FirebaseStorage storage = FirebaseStorage.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DocumentReference userRef;
    String userUid = user.getUid();

    private ArrayList<BookmarkItem> bookMarkItemList;
    Context mContext;

    public BookmarkRecyclerViewAdapter(ArrayList<BookmarkItem> bookMarkItemList, DocumentReference userRef, Context mContext) {
        this.bookMarkItemList = bookMarkItemList;
        this.userRef = userRef;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BookmarkRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_activity_item, parent, false);
        BookmarkRecyclerViewAdapter.ViewHolder viewHolder = new BookmarkRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkRecyclerViewAdapter.ViewHolder holder, int position) {
        BookmarkItem bookmarkItem = bookMarkItemList.get(position);

        DocumentReference clubRef = bookmarkItem.getClubRef();
        holder.club_name.setText(bookmarkItem.getClubName());
        holder.main_category.setText(bookmarkItem.getMajor()+" ");
        holder.sub_category.setText(bookmarkItem.getMinor()+" ");
        holder.bindProfileImage(bookmarkItem.getIconUrl());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ClubActivity.class);
                intent.putExtra("club_name", holder.club_name.getText().toString());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookMarkItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ConstraintLayout constraintLayout;
        private CircleImageView icon;
        private TextView club_name;
        private TextView main_category;
        private TextView sub_category;
        private String image_url;
        private boolean isChecked = false;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.bookmark_item_icon);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
            club_name = itemView.findViewById(R.id.bookmark_item_name);
            main_category = itemView.findViewById(R.id.bookmark_item_major);
            sub_category = itemView.findViewById(R.id.bookmark_item_minor);
        }

        public void bindProfileImage(String image_url){
            if (image_url.equals("")) icon.setImageResource(R.drawable.blank_user);
            else Glide.with(mContext).load(image_url).into(icon);
            icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

    }
}
