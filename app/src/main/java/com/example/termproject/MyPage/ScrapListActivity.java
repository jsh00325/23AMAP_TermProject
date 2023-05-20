package com.example.termproject.MyPage;


import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termproject.R;

import java.util.ArrayList;


public class ScrapListActivity extends AppCompatActivity implements ScrapListAdapter.ScrapListClickListener {

    ArrayList<ScrapItemData> dataList = new ArrayList<>();
    int[] cat = {R.drawable.png_01, R.drawable.png_02, R.drawable.png_03,R.drawable.png_04,R.drawable.png_05,R.drawable.png_06};

    final ScrapListAdapter adapter = new ScrapListAdapter(dataList);
    static int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_activity_like);

        RecyclerView recyclerView = findViewById(R.id.mypage_like);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        for (int i = 0; i < 2; i++) {
            dataList.add(new ScrapItemData(cat[i], cat[i+1], cat[i+2] ));
        }

        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(this);

    }

    @Override
    public void onItemClicked(int position) {
        Toast.makeText(getApplicationContext(), "Item : "+position, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onImageViewClicked(int position, int imageIndex) {
        Toast.makeText(getApplicationContext(), "Image : "+position, Toast.LENGTH_SHORT).show();
    }

}