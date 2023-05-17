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
    ArrayList<CategoryMainData> mainList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.category_fragment, container, false);

        mainListView = (RecyclerView) view.findViewById(R.id.category_main_list);
        mainListView.setLayoutManager(new LinearLayoutManager(container.getContext()));

        // 다시 기존 프래그먼트로 돌아왔을 때, 이미 mainList가 만들어져 있으면 pass -> 뷰가 열리고 닫힌 정보를 저장 가능
        if (mainList.size() == 0) {
            mainList.add(new CategoryMainData("학술", new ArrayList<>(Arrays.asList("IT", "인문", "자연", "진로/발명/창업"))));
            mainList.add(new CategoryMainData("사회", new ArrayList<>(Arrays.asList("봉사"))));
            mainList.add(new CategoryMainData("문예", new ArrayList<>(Arrays.asList("밴드", "악기", "노래", "연극", "미술", "요리", "댄스", "사진/영상"))));
            mainList.add(new CategoryMainData("체육", new ArrayList<>(Arrays.asList("구기", "라켓", "무술", "익스트림 스포츠", "양궁", "게임"))));
            mainList.add(new CategoryMainData("종교", new ArrayList<>(Arrays.asList("기독교", "불교", "천주교"))));
        }

        mainListView.setAdapter(new CategoryMainAdapter(mainList));
        return view;
    }
}