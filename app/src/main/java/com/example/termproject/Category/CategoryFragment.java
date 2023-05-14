package com.example.termproject.Category;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.termproject.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class CategoryFragment extends Fragment {
    View view;
    RecyclerView mainListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category, container, false);

        mainListView = (RecyclerView) view.findViewById(R.id.category_main_list);
        mainListView.setLayoutManager(new LinearLayoutManager(container.getContext()));

        List<CategoryMainListAdapter.Data> mainList = new ArrayList<CategoryMainListAdapter.Data>();

        CategoryMainListAdapter.Data academic = new CategoryMainListAdapter.Data(CategoryMainListAdapter.MAIN_ITEM, "학술 분과");
        academic.insertChild("IT", "it");
        academic.insertChild("인문", "humanity");
        academic.insertChild("자연", "natural");
        academic.insertChild("진로/발명/창업", "academic_else");
        mainList.add(academic);

        CategoryMainListAdapter.Data society = new CategoryMainListAdapter.Data(CategoryMainListAdapter.MAIN_ITEM, "사회 분과");
        society.insertChild("봉사", "volunteer");
        mainList.add(society);

        CategoryMainListAdapter.Data literary = new CategoryMainListAdapter.Data(CategoryMainListAdapter.MAIN_ITEM, "문예 분과");
        literary.insertChild("밴드", "band");
        literary.insertChild("악기", "instrument");
        literary.insertChild("노래", "sing");
        literary.insertChild("연극", "drama");
        literary.insertChild("미술", "art");
        literary.insertChild("요리", "cook");
        literary.insertChild("댄스", "dance");
        literary.insertChild("사진/영상", "picture");
        literary.insertChild("수공예", "handicraft");
        mainList.add(literary);

        CategoryMainListAdapter.Data sport = new CategoryMainListAdapter.Data(CategoryMainListAdapter.MAIN_ITEM, "체육 분과");
        sport.insertChild("구기", "ball");
        sport.insertChild("라켓", "racket");
        sport.insertChild("무술", "martialArt");
        sport.insertChild("익스트림 스포츠", "extreme");
        sport.insertChild("양궁", "bow");
        sport.insertChild("게임", "game");
        mainList.add(sport);

        CategoryMainListAdapter.Data religion = new CategoryMainListAdapter.Data(CategoryMainListAdapter.MAIN_ITEM, "종교 분과");
        religion.insertChild("기독교", "christian");
        religion.insertChild("불교", "buddhism");
        religion.insertChild("천주교", "catholic");
        mainList.add(religion);

        mainListView.setAdapter(new CategoryMainListAdapter(mainList));

        return view;
    }
}