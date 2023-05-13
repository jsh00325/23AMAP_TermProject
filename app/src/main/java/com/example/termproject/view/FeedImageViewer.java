package com.example.termproject.view;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.termproject.R;

public class FeedImageViewer extends AppCompatActivity {
    String imageURL;
    ImageView popUpImageView;
    RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_image_viewer);

        Intent it = getIntent();
        imageURL = it.getStringExtra("ImageURL");

        popUpImageView = (ImageView) findViewById(R.id.feed_image_viewer_iv);
        popUpImageView.setImageResource(R.drawable.test_profile_image1);
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
