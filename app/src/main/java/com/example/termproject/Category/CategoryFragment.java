package com.example.termproject.Category;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
    ExpandableListView expandableListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category, container, false);

        expandableListView = (ExpandableListView) view.findViewById(R.id.category_main_list);

        // ExpandableListView를 출력하기 위한 임시 이름
        List<String> groupList = new ArrayList<>();
        Map<String, List<String>> childMap = new HashMap<>();

        groupList.add("대분류 1");
        groupList.add("대분류 2");
        groupList.add("대분류 3");

        List<String> group1Children = new ArrayList<>();
        group1Children.add("소분류 1-1");
        group1Children.add("소분류 1-2");

        List<String> group2Children = new ArrayList<>();
        group2Children.add("소분류 2-1");
        group2Children.add("소분류 2-2");
        group2Children.add("소분류 2-3");

        List<String> group3Children = new ArrayList<>();
        group3Children.add("소분류 3-1");

        childMap.put(groupList.get(0), group1Children);
        childMap.put(groupList.get(1), group2Children);
        childMap.put(groupList.get(2), group3Children);

        expandableListView.setAdapter(new CategoryExpandableAdapter(container.getContext(), groupList, childMap));


        return view;
    }
}