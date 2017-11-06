package com.direct.materialtest.news;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.direct.materialtest.R;

public class NewsDetailsActivity extends AppCompatActivity {
    public static final String NEWS_TITLE = "news_title";
    public static final String NEWS_CONTENT = "news_content";
    public static final String NEWS_IMAGEID = "news_imageurl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        Intent intent = getIntent();
        String newstitle = intent.getStringExtra(NEWS_TITLE);
        String newscontent= intent.getStringExtra(NEWS_CONTENT);
        String newsImageurl = intent.getStringExtra(NEWS_IMAGEID);
        Toolbar toolbar = (Toolbar) findViewById(R.id.news_toobar);
        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.news_collapsing_toolbar);
        ImageView newsImageView= (ImageView) findViewById(R.id.news_image_view);
        TextView newsContentText= (TextView) findViewById(R.id.news_content_text);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(newstitle);
        Glide.with(this).load(newsImageurl).into(newsImageView);
        String newscon=generateFruitContenr(newscontent);
        newsContentText.setText(newscon);
    }

    private String generateFruitContenr(String newsContent) {
        StringBuilder Content=new StringBuilder();
        for (int i=0;i<500;i++){
            Content.append(newsContent);
        }
        return Content.toString();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
