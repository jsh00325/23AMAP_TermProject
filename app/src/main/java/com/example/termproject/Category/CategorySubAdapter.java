package com.example.termproject.Category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termproject.R;

import java.util.ArrayList;

public class CategorySubAdapter extends RecyclerView.Adapter<CategorySubAdapter.SubListViewHolder> {
    private ArrayList<CategorySubData> subList;
    private Context context;

    public CategorySubAdapter(ArrayList<CategorySubData> subList) {
        this.subList = subList;
    }

    @NonNull
    @Override
    public SubListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new SubListViewHolder(LayoutInflater.from(context).inflate(R.layout.category_sub_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SubListViewHolder holder, int position) {
        holder.setTextView(subList.get(position).text);
        holder.sub_cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subListKey = subList.get(holder.getAdapterPosition()).subListKey;
                // TODO : subListKey 값의 동아리 목록
                Toast.makeText(context, subListKey, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return subList.size();
    }

    public static class SubListViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout sub_cl;
        public TextView sub_title_tv;

        public SubListViewHolder(View itemView) {
            super(itemView);
            sub_cl = (ConstraintLayout) itemView.findViewById(R.id.category_sub_Cl);
            sub_title_tv = (TextView) itemView.findViewById(R.id.category_sub_TextView);
        }

        public void setTextView(String text) {
            sub_title_tv.setText(text);
        }
    }
}
