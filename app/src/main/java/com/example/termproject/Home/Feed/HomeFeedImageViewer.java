package com.example.termproject.Home.Feed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.termproject.R;
import com.jsibbold.zoomage.ZoomageView;

public class HomeFeedImageViewer extends AppCompatActivity {
    String imageURL;
    ZoomageView popUpImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_feedimgview);

        Intent it = getIntent();
        imageURL = it.getStringExtra("ImageURL");

        popUpImageView = (ZoomageView) findViewById(R.id.feed_image_viewer_iv);
        Glide.with(HomeFeedImageViewer.this).load(imageURL).into(popUpImageView);

        findViewById(R.id.feed_image_viewer_closeBtn).setOnClickListener(view ->  { finish(); });
    }
}
