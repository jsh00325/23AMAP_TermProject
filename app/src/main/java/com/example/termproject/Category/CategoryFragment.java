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
import java.util.Arrays;

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
            CategoryMainData academic = new CategoryMainData("학술");
            academic.subList = new ArrayList<>(Arrays.asList("IT", "인문", "자연", "진로/발명/창업"));
            mainList.add(academic);

            CategoryMainData society = new CategoryMainData("사회");
            society.subList = new ArrayList<>(Arrays.asList("봉사"));
            mainList.add(society);

            CategoryMainData literary = new CategoryMainData("문예");
            literary.subList = new ArrayList<>(Arrays.asList("밴드", "악기", "노래", "연극", "미술", "요리", "댄스", "사진/영상"));
            mainList.add(literary);

            CategoryMainData sport = new CategoryMainData("체육");
            sport.subList = new ArrayList<>(Arrays.asList("구기", "라켓", "무술", "익스트림 스포츠", "양궁", "게임"));
            mainList.add(sport);

            CategoryMainData religion = new CategoryMainData("종교");
            religion.subList = new ArrayList<>(Arrays.asList("기독교", "불교", "천주교"));
            mainList.add(religion);
        }

        mainListView.setAdapter(new CategoryMainAdapter(mainList));
        return view;
    }
}