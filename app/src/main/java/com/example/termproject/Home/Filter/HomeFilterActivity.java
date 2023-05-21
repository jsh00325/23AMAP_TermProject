package com.example.termproject.Home.Filter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.termproject.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFilterActivity extends AppCompatActivity {
    final private int SAVED_FILTER_INFO = RESULT_FIRST_USER + 1;
    private ImageButton closeBtn;
    private Button clearAll, saveFilter;
    private RecyclerView mainRecyclerView;
    private List<HomeFilterMainData> filters = new ArrayList<>();
    private List<String> userFilterInfo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_filter);

        closeBtn = (ImageButton) findViewById(R.id.HomeFilter_closeBtn);
        clearAll = (Button) findViewById(R.id.HomeFilter_clearAll);
        saveFilter = (Button) findViewById(R.id.HomeFilter_saveFilter);

        if (filters.size() == 0) initFilterList();

        userFilterInfo = readCacheData("userFilterInfo");
        checkFromFilterData(userFilterInfo);

        mainRecyclerView = (RecyclerView) findViewById(R.id.HomeFilter_main_recycle);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        HomeFilterMainAdapter filterAdapter = new HomeFilterMainAdapter(filters);
        mainRecyclerView.setAdapter(filterAdapter);

        closeBtn.setOnClickListener(view -> {
            finish();
        });
        clearAll.setOnClickListener(view -> {
            filterAdapter.clearAll();
        });
        saveFilter.setOnClickListener(view -> {
            userFilterInfo = new ArrayList<>();
            for (HomeFilterMainData main : filters)
                for (HomeFilterSubData sub : main.getChildDatas())
                    if (sub.getType() == HomeFilterSubData.CHILD && sub.getChecked())
                        userFilterInfo.add(sub.getSubCategory());

            if (userFilterInfo.isEmpty()) {
                Toast.makeText(this, "적어도 하나의 관심 분야를 선택하세요!", Toast.LENGTH_SHORT).show();
            } else {
                saveCacheData("userFilterInfo", userFilterInfo);
                setResult(SAVED_FILTER_INFO);
                finish();
            }
        });
    }

    private void initFilterList() {
        filters.add(new HomeFilterMainData("학술", new ArrayList<>(Arrays.asList("IT", "인문", "자연", "진로/발명/창업"))));
        filters.add(new HomeFilterMainData("사회", new ArrayList<>(Arrays.asList("봉사"))));
        filters.add(new HomeFilterMainData("문예", new ArrayList<>(Arrays.asList("밴드", "악기", "노래", "연극", "미술", "요리", "댄스", "사진/영상"))));
        filters.add(new HomeFilterMainData("체육", new ArrayList<>(Arrays.asList("구기", "라켓", "무술", "익스트림 스포츠", "양궁", "게임"))));
        filters.add(new HomeFilterMainData("종교", new ArrayList<>(Arrays.asList("기독교", "불교", "천주교"))));
    }

    // TODO : 캐시에서 읽은 값 체크해두기...
    private void checkFromFilterData(List<String> data) {
        for (HomeFilterMainData main : filters) {
            for (HomeFilterSubData sub : main.getChildDatas()) {
                if (data.contains(sub.getSubCategory())) {
                    sub.setChecked(true);
                    main.increaseCount();
                }
            }
            if (main.getChildCount() == main.getChildDatas().size() - 1)
                main.getChildDatas().get(0).setChecked(true);
        }
    }

    private void saveCacheData(String fileName, List<String> data) {
        try {
            FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private List<String> readCacheData(String fileName) {
        List<String> dataList = new ArrayList<>();
        try {
            FileInputStream fis = openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            dataList = (List<String>) ois.readObject();
            ois.close();
            fis.close();
        } catch (FileNotFoundException fnfe) {
            // 파일이 존재하지 않을 때 -> 모두 존재하는 값으로 저장.
            dataList = new ArrayList<>(Arrays.asList("IT", "인문", "자연", "진로/발명/창업",
                    "봉사", "밴드", "악기", "노래", "연극", "미술", "요리", "댄스", "사진/영상",
                    "구기", "라켓", "무술", "익스트림 스포츠", "양궁", "게임", "기독교", "불교", "천주교"));
            saveCacheData(fileName, dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }
}