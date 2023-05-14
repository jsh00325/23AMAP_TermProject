package com.example.termproject.Category;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termproject.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoryMainListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final static int MAIN_ITEM = 0;
    public final static int SUB_ITEM = 1;
    private List<Data> mainList;

    Context context;    // Toast 메시지 확인용

    public CategoryMainListAdapter(List<Data> mainList) {
        this.mainList = mainList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        if (viewType == MAIN_ITEM)
            return new MainListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_main_item, parent,false));
        else if (viewType == SUB_ITEM)
            return new SubListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_sub_item, parent,false));
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Data cur_data = mainList.get(position);
        if (cur_data.viewType == MAIN_ITEM) {
            MainListViewHolder curViewHolder = (MainListViewHolder) holder;
            curViewHolder.setTextView(cur_data.text);

            curViewHolder.main_cl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 펼쳐져 있는 아이템 클릭 -> 서브 리스트 닫기
                    if (cur_data.isOpen) {
                        // TODO : 서브 리스트 닫는거 구현하기


                        curViewHolder.main_toggle.setImageResource(R.drawable.down_sign);
                        cur_data.isOpen = false;
                    }
                    // 닫혀진 아이템 클릭 -> 서브 리스트 열기
                    else {
                        // TODO : 서브 리스트 여는거 구현하기

                        curViewHolder.main_toggle.setImageResource(R.drawable.up_sign);
                        cur_data.isOpen = true;
                    }
                    // notifyItemChanged(curViewHolder.getAdapterPosition());
                }
            });
        }
        else {
            SubListViewHolder curViewHolder = (SubListViewHolder) holder;
            curViewHolder.setTextView(cur_data.text);

            curViewHolder.sub_cl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO : 항목 눌렀을 때 액티비티 실행하는거 구현 (Intent로 어떤 소분류인지 넘겨줌)
                    Toast.makeText(context, "서브 리스트 클릭!", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    @Override
    public int getItemCount() {
        return mainList.size();
    }

    public static class MainListViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout main_cl;
        public TextView main_title_tv;
        public ImageView main_toggle;

        public MainListViewHolder(View itemView) {
            super(itemView);
            main_cl = (ConstraintLayout) itemView.findViewById(R.id.category_main_Cl);
            main_title_tv = (TextView) itemView.findViewById(R.id.category_main_TextView);
            main_toggle = (ImageView) itemView.findViewById(R.id.category_main_Toggle);
        }

        public void setTextView(String text) {
            main_title_tv.setText(text);
        }
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

    public static class Data {
        public String text;
        public String subListKey;
        public List<Data> subList;
        public boolean isOpen;
        private int viewType;

        public Data(int viewType, String text) {
            this.viewType = viewType;
            this.text = text;
            isOpen = false;
            subList = new ArrayList<Data>();
        }

        public void insertChild(String text, String key) {
            Data childData = new Data(SUB_ITEM, text);
            childData.subListKey = key;
            subList.add(childData);
        }
    }
}
