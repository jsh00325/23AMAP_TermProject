package com.example.termproject.view;

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

public class FeedImageViewer extends AppCompatActivity {
    String imageURL;
    ZoomageView popUpImageView;
    RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_feedimgview);

        Intent it = getIntent();
        imageURL = it.getStringExtra("ImageURL");

        popUpImageView = (ZoomageView) findViewById(R.id.feed_image_viewer_iv);
        Glide.with(FeedImageViewer.this).load(imageURL).apply(options).into(popUpImageView);

        findViewById(R.id.feed_image_viewer_closeBtn).setOnClickListener(closeActivity);
    }

    View.OnClickListener closeActivity = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
}
