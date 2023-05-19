package com.example.termproject.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.termproject.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {
    private View view;
    private ImageButton filterBtn;
    private RecyclerView homeFeed;
    private ShimmerFrameLayout homeFeedLoading;
    private SwipeRefreshLayout homeSrl;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.home_fragment, container, false);
        context = container.getContext();

        filterBtn = (ImageButton) view.findViewById(R.id.feed_filter_btn);
        filterBtn.setOnClickListener(curView -> {
            Intent it = new Intent(context, HomeFilterActivity.class);
            getActivity().startActivityForResult(it, 0);
        });

        homeFeed = (RecyclerView) view.findViewById(R.id.home_recycle_view);
        homeFeed.setLayoutManager(new LinearLayoutManager(container.getContext()));

        homeFeedLoading = (ShimmerFrameLayout) view.findViewById(R.id.home_feed_load);

        homeSrl = (SwipeRefreshLayout) view.findViewById(R.id.home_srl);
        homeSrl.setOnRefreshListener(() -> {
            loadHomeFeed();
            homeSrl.setRefreshing(false);
        });

        loadHomeFeed();
        return view;
    }

    public void loadHomeFeed() {
        showShimmer();

        // TODO : db에서 글 정보 읽어와서 Adaptor에 넘겨주기!
        List<String> forTestPostID = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8"));
        homeFeed.setAdapter(new HomeAdapter(forTestPostID));

        new Handler().postDelayed(() -> {
            hideShimmer();
        }, 700);
    }

    private void showShimmer() {
        homeFeedLoading.startShimmer();
        homeFeedLoading.setVisibility(View.VISIBLE);
        homeFeed.setVisibility(View.GONE);
    }

    private void hideShimmer() {
        homeFeedLoading.stopShimmer();
        homeFeedLoading.setVisibility(View.GONE);
        homeFeed.setVisibility(View.VISIBLE);
    }
}