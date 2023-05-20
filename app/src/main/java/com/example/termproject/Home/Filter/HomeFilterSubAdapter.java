package com.example.termproject.Home.Filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termproject.R;

import java.util.List;

public class HomeFilterSubAdapter extends RecyclerView.Adapter<HomeFilterSubAdapter.SubViewHolder> {
    private HomeFilterMainData fromItem;
    private List<HomeFilterSubData> items;
    private Context context;
    final private int childCount;

    public HomeFilterSubAdapter(HomeFilterMainData fromItem) {
        this.fromItem = fromItem;
        this.items = fromItem.getChildDatas();
        childCount = items.size() - 1;
    }

    @NonNull
    @Override
    public SubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new SubViewHolder(LayoutInflater.from(context).inflate(R.layout.home_item_filter_sub, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SubViewHolder holder, int position) {
        HomeFilterSubData item = items.get(holder.getAdapterPosition());
        holder.toggleBtn.setText(item.getSubCategory());
        holder.toggleBtn.setTextOn(item.getSubCategory());
        holder.toggleBtn.setTextOff(item.getSubCategory());
        holder.toggleBtn.setChecked(item.getChecked());

        holder.toggleBtn.setOnClickListener(view -> {
            if (item.getType() == HomeFilterSubData.ALL) {
                if (item.getChecked()) {
                    for (HomeFilterSubData child : items)
                        child.setChecked(false);
                    fromItem.setChildCount(0);
                    item.setChecked(false);
                } else {
                    for (HomeFilterSubData child : items)
                        child.setChecked(true);
                    fromItem.setChildCount(childCount);
                    item.setChecked(true);
                }
            } else {
                if (item.getChecked()) {
                    item.setChecked(false);
                    fromItem.decreaseCount();
                } else {
                    item.setChecked(true);
                    fromItem.increaseCount();
                }
            }
            if (fromItem.getChildCount() == childCount) items.get(0).setChecked(true);
            else items.get(0).setChecked(false);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class SubViewHolder extends RecyclerView.ViewHolder {
        public ToggleButton toggleBtn;

        public SubViewHolder(@NonNull View itemView) {
            super(itemView);
            toggleBtn = (ToggleButton) itemView.findViewById(R.id.HomeFilter_toggleBtn);
        }
    }
}
