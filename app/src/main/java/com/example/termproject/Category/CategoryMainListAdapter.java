package com.example.termproject.Category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termproject.R;

import java.util.List;
import java.util.Map;

public class CategoryMainListAdapter extends RecyclerView.Adapter<CategoryMainListAdapter.MainListViewHolder> {
    private List<String> mainList;

    public CategoryMainListAdapter(List<String> mainList) {
        this.mainList = mainList;
    }

    @NonNull
    @Override
    public MainListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_main_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainListViewHolder holder, int position) {
        holder.main_title_tv.setText(mainList.get(position));
    }


    @Override
    public int getItemCount() {
        return mainList.size();
    }

    public static class MainListViewHolder extends RecyclerView.ViewHolder {
        public TextView main_title_tv;
        public MainListViewHolder(View itemView) {
            super(itemView);
            main_title_tv = (TextView) itemView.findViewById(R.id.category_main_TextView);
        }
    }

}
