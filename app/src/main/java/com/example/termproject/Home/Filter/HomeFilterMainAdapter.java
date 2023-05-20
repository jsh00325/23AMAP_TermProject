package com.example.termproject.Home.Filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termproject.R;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class HomeFilterMainAdapter extends RecyclerView.Adapter<HomeFilterMainAdapter.MainViewHolder> {
    private List<HomeFilterMainData> items;
    private Context context;

    public HomeFilterMainAdapter(List<HomeFilterMainData> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.home_item_filter_main, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        HomeFilterMainData item = items.get(holder.getAdapterPosition());

        holder.mainText.setText(item.getMainCategory() + " 분과");

        FlexboxLayoutManager flexManager = new FlexboxLayoutManager(context);
        HomeFilterSubAdapter childAdapter = new HomeFilterSubAdapter(item);

        holder.subRecycle.setLayoutManager(flexManager);
        holder.subRecycle.setAdapter(childAdapter);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void clearAll() {
        for (HomeFilterMainData main : items)
            for (HomeFilterSubData sub : main.getChildDatas())
                sub.setChecked(false);
        notifyDataSetChanged();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        public TextView mainText;
        public RecyclerView subRecycle;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            mainText = (TextView) itemView.findViewById(R.id.HomeFilter_main_title);
            subRecycle = (RecyclerView) itemView.findViewById(R.id.HomeFilter_sub_recycle);
        }
    }
}