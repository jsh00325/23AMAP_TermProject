package com.example.termproject.Category.CategoryTree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termproject.R;

import java.util.ArrayList;

/*  #1. https://notepad96.tistory.com/197 - Expandable Layout
    #2. https://youtu.be/RYM2H0Qzq9I - RecyclerView 완벽 정리       */
public class CategoryMainAdapter extends RecyclerView.Adapter<CategoryMainAdapter.MainListViewHolder> {
    private Context context;
    public ArrayList<CategoryMainData> mainList;

    public CategoryMainAdapter(ArrayList<CategoryMainData> mainList) {
        this.mainList = mainList;
    }

    @NonNull
    @Override
    public MainListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new MainListViewHolder(LayoutInflater.from(context).inflate(R.layout.category_item_mainlist, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainListViewHolder holder, int position) {
        CategoryMainData cur_data = mainList.get(position);
        holder.setTextView(cur_data.getText() + " 분과");

        holder.subView.setLayoutManager(new LinearLayoutManager(context));
        holder.subView.setAdapter(new CategorySubAdapter(cur_data.getSubList()));

        if (cur_data.getIsOpen()) {
            holder.subView.setVisibility(View.VISIBLE);
            holder.main_toggle.setImageResource(R.drawable.up_sign);
        }
        else {
            holder.subView.setVisibility(View.GONE);
            holder.main_toggle.setImageResource(R.drawable.down_sign);
        }

        holder.main_cl.setOnClickListener(view -> {
            // 펼쳐져 있는 아이템 클릭 -> 서브 리스트 닫기
            if (cur_data.getIsOpen()) {
                holder.subView.setVisibility(View.GONE);
                holder.main_toggle.setImageResource(R.drawable.down_sign);
            }
            // 닫혀진 아이템 클릭 -> 서브 리스트 열기
            else {
                holder.subView.setVisibility(View.VISIBLE);
                holder.main_toggle.setImageResource(R.drawable.up_sign);
            }
            cur_data.flipIsOpen();
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return mainList.size();
    }

    public static class MainListViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout main_cl;
        public TextView main_title_tv;
        public ImageView main_toggle;
        private RecyclerView subView;

        public MainListViewHolder(View itemView) {
            super(itemView);
            main_cl = (ConstraintLayout) itemView.findViewById(R.id.category_main_Cl);
            main_title_tv = (TextView) itemView.findViewById(R.id.category_main_TextView);
            main_toggle = (ImageView) itemView.findViewById(R.id.category_main_Toggle);
            subView = (RecyclerView) itemView.findViewById(R.id.category_sub_RV);
        }

        public void setTextView(String text) {
            main_title_tv.setText(text);
        }
    }
}
