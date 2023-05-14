package com.example.termproject.Category;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.termproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryFragment extends Fragment {
    View view;
    RecyclerView mainListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category, container, false);

        mainListView = (RecyclerView) view.findViewById(R.id.category_main_list);

        List<String> groupList = new ArrayList<>();
        groupList.add("학술 분과");
        groupList.add("사회 분과");
        groupList.add("문예 분과");
        groupList.add("체육 분과");
        groupList.add("종교 분과");

        mainListView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        mainListView.setAdapter(new CategoryMainListAdapter(groupList));

        return view;
    }
}