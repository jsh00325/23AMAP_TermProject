package com.example.termproject.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.termproject.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFilterActivity extends AppCompatActivity {
    final private int SAVED_FILTER_INFO = RESULT_FIRST_USER + 1;
    private ImageButton closeBtn;
    private Button clearAll, saveFilter;
    private List<HomeFilterData> filters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_filter);

        closeBtn = (ImageButton) findViewById(R.id.HomeFilter_closeBtn);
        clearAll = (Button) findViewById(R.id.HomeFilter_clearAll);
        saveFilter = (Button) findViewById(R.id.HomeFilter_saveFilter);

        initFilterList();

        closeBtn.setOnClickListener(view -> {
            finish();
        });
        clearAll.setOnClickListener(view -> {
            for (HomeFilterData curData : filters)
                curData.clearAll();
        });
        saveFilter.setOnClickListener(view -> {
            List<String> filteredSubList = new ArrayList<>();
            for (HomeFilterData curData : filters)
                for (ToggleButton childBtn : curData.getChildBtns())
                    if (childBtn.isChecked()) filteredSubList.add((String)childBtn.getTextOn());

            // TODO : DB의 유저정보에 이 정보 저장하기 (사람마다 필터링 기억해서 다음에 들어와도 다르게)


            setResult(SAVED_FILTER_INFO);
            finish();
        });
    }
    
    // 필터 토글 버튼에 대한 데이터 리스트 만들기 & 클릭 리스너 설정
    private void initFilterList() {
        HomeFilterData academic = new HomeFilterData(findViewById(R.id.HomeFilter_academic_all));
        academic.addChildBtn(findViewById(R.id.HomeFilter_academic_it));
        academic.addChildBtn(findViewById(R.id.HomeFilter_academic_humanity));
        academic.addChildBtn(findViewById(R.id.HomeFilter_academic_nature));
        academic.addChildBtn(findViewById(R.id.HomeFilter_academic_else));
        filters.add(academic);

        HomeFilterData society = new HomeFilterData(findViewById(R.id.HomeFilter_society_all));
        society.addChildBtn(findViewById(R.id.HomeFilter_society_volunteer));
        filters.add(society);
        
        HomeFilterData literary = new HomeFilterData(findViewById(R.id.HomeFilter_literary_all));
        literary.addChildBtn(findViewById(R.id.HomeFilter_literary_band));
        literary.addChildBtn(findViewById(R.id.HomeFilter_literary_inst));
        literary.addChildBtn(findViewById(R.id.HomeFilter_literary_sing));
        literary.addChildBtn(findViewById(R.id.HomeFilter_literary_play));
        literary.addChildBtn(findViewById(R.id.HomeFilter_literary_art));
        literary.addChildBtn(findViewById(R.id.HomeFilter_literary_cook));
        literary.addChildBtn(findViewById(R.id.HomeFilter_literary_dance));
        literary.addChildBtn(findViewById(R.id.HomeFilter_literary_picture));
        filters.add(literary);

        HomeFilterData sport = new HomeFilterData(findViewById(R.id.HomeFilter_sport_all));
        sport.addChildBtn(findViewById(R.id.HomeFilter_sport_ball));
        sport.addChildBtn(findViewById(R.id.HomeFilter_sport_racket));
        sport.addChildBtn(findViewById(R.id.HomeFilter_sport_martialArt));
        sport.addChildBtn(findViewById(R.id.HomeFilter_sport_extream));
        sport.addChildBtn(findViewById(R.id.HomeFilter_sport_bow));
        sport.addChildBtn(findViewById(R.id.HomeFilter_sport_game));
        filters.add(sport);

        HomeFilterData religion = new HomeFilterData(findViewById(R.id.HomeFilter_religion_all));
        religion.addChildBtn(findViewById(R.id.HomeFilter_religion_cristian));
        religion.addChildBtn(findViewById(R.id.HomeFilter_religion_buddhism));
        religion.addChildBtn(findViewById(R.id.HomeFilter_religion_catholic));
        filters.add(religion);

        for (HomeFilterData curData : filters)
            curData.settingListener();
    }
}