package com.example.termproject.MyPage;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termproject.R;

import java.util.ArrayList;

public class ScrapListAdapter extends RecyclerView.Adapter<ScrapListAdapter.ViewHolder> {

    private ArrayList<ScrapItemData> itemData;
    public ScrapListAdapter(ArrayList<ScrapItemData> itemData) {
        this.itemData = itemData;
    }

    public interface ScrapListClickListener{
        void onItemClicked(int position);
        void onImageViewClicked(int position, int imageIndex);

    }


    private ScrapListClickListener mListener;

    public void setOnClickListener(ScrapListClickListener listener) {
        this.mListener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mypage_activity_likeitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ScrapItemData item = itemData.get(position);
        holder.image1.setImageResource(item.getImage1());
        holder.image2.setImageResource(item.getImage2());
        holder.image3.setImageResource(item.getImage3());

        if (mListener != null) {
            final int pos = position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClicked(pos);
                }
            });

            holder.image1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onImageViewClicked(pos,1);
                }
            });
            holder.image2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onImageViewClicked(pos,2);
                }
            });
            holder.image3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onImageViewClicked(pos,3);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return itemData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image1, image2, image3;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image1 = itemView.findViewById(R.id.image1);
            image2 = itemView.findViewById(R.id.image2);
            image3 = itemView.findViewById(R.id.image3);

            //이미지뷰 원형으로 표시

            image1.setClipToOutline(true);

            image2.setClipToOutline(true);

            image3.setClipToOutline(true);
        }
    }

    //리스트 삭제 이벤트
    public void remove(int position){
        try {
            itemData.remove(position);
            notifyDataSetChanged();
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }
}
