package com.example.termproject.Category;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.termproject.R;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {
    View view;
    RecyclerView mainListView;
    ArrayList<CategoryMainData> mainList = new ArrayList<CategoryMainData>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category, container, false);


        mainListView = (RecyclerView) view.findViewById(R.id.category_main_list);
        mainListView.setLayoutManager(new LinearLayoutManager(container.getContext()));

        // 다시 기존 프래그먼트로 돌아왔을 때, 이미 mainList가 만들어져 있으면 pass -> 뷰가 열리고 닫힌 정보를 저장 가능!!!
        if (mainList.size() == 0) {
            // TODO : 이렇게 더럽게 하지 말고 DB로 깔끔하게 구현?
            CategoryMainData academic = new CategoryMainData("학술 분과");
            academic.insertChild("IT", "it");
            academic.insertChild("인문", "humanity");
            academic.insertChild("자연", "natural");
            academic.insertChild("진로/발명/창업", "academic_else");
            mainList.add(academic);

            CategoryMainData society = new CategoryMainData("사회 분과");
            society.insertChild("봉사", "volunteer");
            mainList.add(society);

            CategoryMainData literary = new CategoryMainData("문예 분과");
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

            CategoryMainData sport = new CategoryMainData("체육 분과");
            sport.insertChild("구기", "ball");
            sport.insertChild("라켓", "racket");
            sport.insertChild("무술", "martialArt");
            sport.insertChild("익스트림 스포츠", "extreme");
            sport.insertChild("양궁", "bow");
            sport.insertChild("게임", "game");
            mainList.add(sport);

            CategoryMainData religion = new CategoryMainData("종교 분과");
            religion.insertChild("기독교", "christian");
            religion.insertChild("불교", "buddhism");
            religion.insertChild("천주교", "catholic");
            mainList.add(religion);
        }

        mainListView.setAdapter(new CategoryMainAdapter(mainList));
        return view;
    }
}