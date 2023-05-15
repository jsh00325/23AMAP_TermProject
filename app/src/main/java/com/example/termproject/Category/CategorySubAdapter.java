package com.example.termproject.Category;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termproject.MainActivity;
import com.example.termproject.R;

import java.util.ArrayList;

public class CategorySubAdapter extends RecyclerView.Adapter<CategorySubAdapter.SubListViewHolder> {
    private ArrayList<String> subList;
    private Context context;

    public CategorySubAdapter(ArrayList<String> subList) {
        this.subList = subList;
    }

    @NonNull
    @Override
    public SubListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new SubListViewHolder(LayoutInflater.from(context).inflate(R.layout.category_item_sublist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SubListViewHolder holder, int position) {
        holder.setTextView(subList.get(position));
        holder.sub_cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subListKey = subList.get(holder.getAdapterPosition());

                CategoryClublistFragment nxtFragment = new CategoryClublistFragment();

                Bundle args = new Bundle();
                args.putString("clubSubKey", subListKey);
                nxtFragment.setArguments(args);

                MainActivity activity = (MainActivity) context;
                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.main_fragment, nxtFragment);
                ft.addToBackStack(null);
                ft.commit();
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
