package com.example.termproject.Home;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.termproject.R;

public class HomeFragment extends Fragment {
    View view;
    RecyclerView home_feed;
    SwipeRefreshLayout home_srl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_home, container, false);
        
        home_feed = (RecyclerView) view.findViewById(R.id.home_recycle_view);
        home_srl = (SwipeRefreshLayout) view.findViewById(R.id.home_srl);
        home_srl.setOnRefreshListener(srl_refresh_listener);



        return view;
    }
    
    SwipeRefreshLayout.OnRefreshListener srl_refresh_listener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            // TODO : 새로고침 시 실행할 코드

            home_srl.setRefreshing(false);
        }
    };
}