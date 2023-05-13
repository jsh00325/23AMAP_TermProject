package com.example.termproject.home;

import android.content.Context;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.termproject.R;

public class HomeItemFragment extends Fragment {
    private ViewPager2 sliderViewPager;
    private LinearLayout layoutIndicator;

    private Context context;
    String[] images = new String[] {
            "https://cdn.pixabay.com/photo/2023/04/22/10/01/insect-7943499_960_720.jpg",
            "https://cdn.pixabay.com/photo/2023/03/27/17/40/tree-7881297_960_720.jpg",
            "https://cdn.pixabay.com/photo/2023/04/05/13/01/animal-7901464_960_720.jpg",
            "https://cdn.pixabay.com/photo/2023/04/21/11/51/flower-7941764_960_720.jpg"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home_item, container, false);
        context = getActivity().getApplicationContext();

        sliderViewPager = view.findViewById(R.id.feed_viewpager);
        layoutIndicator = view.findViewById(R.id.feed_indicator);

        sliderViewPager.setOffscreenPageLimit(1);
        sliderViewPager.setAdapter(new ImageSlideAdaptor(context, images));

        // 콜백 설명  ->  https://itpangpang.tistory.com/290
        sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            // 페이지의 변화가 생길 때 호출
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });
        setUpIndicator(images.length);

        return view;
    }
    private void setUpIndicator(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(8, 8, 8, 8);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(context);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dot_item));
            indicators[i].setLayoutParams(params);
            layoutIndicator.addView(indicators[i]);
        }
        setCurrentIndicator(0);
    }
    private void setCurrentIndicator(int position) {
        int childCount = layoutIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutIndicator.getChildAt(i);
            if (i == position)
                imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dot_selected_item));
            else
                imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dot_item));
        }
    }
}