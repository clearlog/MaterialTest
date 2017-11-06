package com.direct.materialtest;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.direct.materialtest.base.BaseActivity;
import com.direct.materialtest.news.NewsDetailsActivity;
import com.direct.materialtest.news.adapter.NewsAdapter;
import com.direct.materialtest.news.adapter.NewsTitleAdapter;
import com.direct.materialtest.news.newsbean.NewsBean;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewsActivity extends BaseActivity {
    private int times=0;
    private int refreshTime=0;
    private XRecyclerView xrecyclerView;
    private RecyclerView navrecyclerView;
    private NewsTitleAdapter newsTitleAdapter;
    private NewsAdapter adapter;
    private List<NewsBean> newsBeanList = new ArrayList<>();
    private String[] url = new String[]{
            "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1580482776,3214289843&fm=80&w=179&h=119&img.JPEG",
            "https://ss2.baidu.com/-vo3dSag_xI4khGko9WTAnF6hhy/vip/whcrop%3D176%2C106/sign=cffe5aed6363f6241c086f41e834d6c9/f3d3572c11dfa9ecb7f287c36ad0f703908fc102.jpg",
            "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1383170885,2038374031&fm=80&w=179&h=119&img.JPEG",
            "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2218526845,522933158&fm=80&w=179&h=119&img.JPEG",
            "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=775731846,1370921386&fm=80",
            "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3860616424,1789830124&fm=80&w=179&h=119&img.PNG",
            "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=4096069947,473932952&fm=80&w=179&h=119&img.JPEG",
            "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3244336274,3651700434&fm=80&w=179&h=119&img.JPEG",
            "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=219781665,3032880226&fm=80&w=179&h=119&img.JPEG",
            "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=831306574,2792494868&fm=80&w=179&h=119&img.GIF",

    };

    private String datanav[]={"头条","国内","国际","娱乐","体育","军事","明星","时尚","科技","财经"};

    private List<String> listsnav=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        SetToolBar(R.id.toobar, R.id.toobar_title, "新闻");
        xrecyclerView = (XRecyclerView) findViewById(R.id.recycler_view);
        adapter = new NewsAdapter(this, getData());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xrecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
        xrecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        xrecyclerView.setLayoutManager(layoutManager);
        xrecyclerView.setAdapter(adapter);
        setHeader();
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent=new Intent(NewsActivity.this, NewsDetailsActivity.class);
                NewsBean news=newsBeanList.get(position);
                intent.putExtra(NewsDetailsActivity.NEWS_TITLE,news.getTitle());
                intent.putExtra(NewsDetailsActivity.NEWS_IMAGEID,news.getImgUrl());
                intent.putExtra(NewsDetailsActivity.NEWS_CONTENT,news.getTime());
                startActivity(intent);
                //Toast.makeText(NewsActivity.this, position + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        ReFresData();


    }

    private void ReFresData(){

        xrecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refreshTime++;
                times = 0;
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        getData();
                        adapter.notifyDataSetChanged();
                        xrecyclerView.refreshComplete();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                if (times < 2) {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            getRefreshData();
                            xrecyclerView.loadMoreComplete();
                            adapter.notifyDataSetChanged();
                        }
                    }, 1000);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            getRefreshData();
                            xrecyclerView.setNoMore(true);
                            adapter.notifyDataSetChanged();
                        }
                    }, 1000);
                }
                times++;
            }
        });
    }

    private void setHeader(){
//        View header = getLayoutInflater().inflate(R.layout.news_nav, null);
//        header.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//       xrecyclerView.addHeaderView(header);
        navtitle();
        navrecyclerView= (RecyclerView)findViewById(R.id.new_recycler_view);
        newsTitleAdapter=new NewsTitleAdapter(this,listsnav);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        navrecyclerView.setLayoutManager(layoutManager);
        navrecyclerView.setAdapter(newsTitleAdapter);


    }

    private void navtitle() {
        for (int i=0;i<datanav.length;i++){
            listsnav.add(datanav[i]);
        }

    }



    private List<NewsBean> getData() {
        //newsBeanList.clear();
        for (int i = 0; i < 10; i++) {
            NewsBean newsBean = new NewsBean();
            newsBean.setImgUrl(url[i]);
            newsBean.setTitle("新闻标题" + i);
            newsBean.setTime("哈佛有嘻哈教你如何用一个freestyle轻松毕业" + i);
            newsBeanList.add(newsBean);
        }
        return newsBeanList;
    }

    private List<NewsBean> getRefreshData() {
        for (int i = 0; i < 10; i++) {
            NewsBean newsBean = new NewsBean();
            newsBean.setImgUrl(url[i]);
            newsBean.setTitle("新标" + i);
            newsBean.setTime("哈佛有嘻哈教你如何用" + i);
            newsBeanList.add(newsBean);
        }
        return newsBeanList;
    }





    private Toolbar SetToolBar(int Id, int titleId, String titleString) {
        Toolbar toolbar = (Toolbar) findViewById(Id);
        TextView toobar_title = (TextView) findViewById(titleId);
        toobar_title.setText(titleString);
        //toolbar.setTitle("外婆家的小院");//设置标题
        //toolbar.setSubtitle("外婆家的小院");//设置副标题
        //toolbar.setSubtitleTextColor(Color.BLACK);  //设置副标题字体颜色
        setSupportActionBar(toolbar);   //必须使用
        //设置Menu菜单位置显示在toobar下面
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        return toolbar;
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
