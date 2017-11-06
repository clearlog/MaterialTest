package com.direct.materialtest;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.direct.materialtest.adapter.HomeAdapter;
import com.direct.materialtest.base.BaseActivity;
import com.direct.materialtest.databean.Fruit;
import com.direct.materialtest.home.gridview.GridViewAdapter;
import com.direct.materialtest.home.gridview.HeaderViewBean;
import com.direct.materialtest.home.gridview.MyViewPagerAdapter;
import com.direct.materialtest.home.homeadapter.HomeClassAdapter;
import com.direct.materialtest.home.homebean.HomeBean;
import com.direct.materialtest.util.GlideImageLoader;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HomeActivity extends BaseActivity {
    private static final int pageColumn = 10;
    private static final int pageRow = 2;

    private ViewPager mViewPagerGrid;
    private List<View> mViewPagerGridList;
    private List<HeaderViewBean> mDatas;


    private XRecyclerView xRecyclerView;
    private RecyclerView classRcv;
    private HomeAdapter myadapter;
    private HomeClassAdapter homeClassAdapter;
    private List<Fruit> fruits = new ArrayList<>();
    private List<HomeBean> homeBeen = new ArrayList<>();
    private Fruit[] fruitdata = {new Fruit("妹子", R.mipmap.mm), new Fruit("帅哥些", R.mipmap.gg),
            new Fruit("老大", R.mipmap.nyx), new Fruit("美女", R.mipmap.mm),
            new Fruit("一张图片", R.mipmap.gg), new Fruit("男的", R.mipmap.nyx),
            new Fruit("捏一下", R.mipmap.mm), new Fruit("滚蛋", R.mipmap.gg)};

    private HomeBean[] homeclass = {new HomeBean("换世门生", R.mipmap.gg), new HomeBean("念阳枭", R.mipmap.nyx),
            new HomeBean("美女", R.mipmap.mm), new HomeBean("画江湖", R.mipmap.gg),
            new HomeBean("妹子", R.mipmap.mm), new HomeBean("念阳枭", R.mipmap.nyx),
            new HomeBean("无宗会", R.mipmap.gg), new HomeBean("更多", R.mipmap.mm)};
    private int refreshTime = 0;
    private int times = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        HomeBottombar();
        SetToolBar(R.id.toobar, R.id.toobar_title, "门店");
        initFruits();
        xRecyclerView = (XRecyclerView) findViewById(R.id.xrecycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(gridLayoutManager);
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        addHeaderView();
        myadapter = new HomeAdapter(fruits, this);
        xRecyclerView.setAdapter(myadapter);
        myadapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(HomeActivity.this, FruitActivity.class);
                Fruit fruit = fruits.get(position);
                intent.putExtra(FruitActivity.FRUIT_NAME, fruit.getName());
                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID, fruit.getImageId());
                startActivity(intent);
                //Snackbar.make(view, "点击了我"+position, Snackbar.LENGTH_SHORT).show();
            }
        });
        //xRecyclerView.refresh();

    }

    private void initFruits() {
        fruits.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(fruitdata.length);
            fruits.add(fruitdata[index]);
        }

    }

    private void addHeaderView() {

        View header = getLayoutInflater().inflate(R.layout.home_banner, null);
        header.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        xRecyclerView.addHeaderView(header);
        Banner banner = (Banner) header.findViewById(R.id.banner);
        String[] urls = getResources().getStringArray(R.array.url4);
        final List list = Arrays.asList(urls);
        banner.setImages(list)
                .setImageLoader(new GlideImageLoader())
                .setDelayTime(3000)
                .start();

        //banner图片的点击事件
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                //Toast.makeText(HomeActivity.this, "页面的点击跳转详情"+position, Toast.LENGTH_SHORT).show();
            }
        });

        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refreshTime++;
                times = 0;
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        initFruits();
                        myadapter.notifyDataSetChanged();
                        xRecyclerView.refreshComplete();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                if (times < 2) {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            for (int i = 0; i < 10; i++) {
                                Random random = new Random();
                                int index = random.nextInt(fruitdata.length);
                                fruits.add(fruitdata[index]);
                            }
                            xRecyclerView.loadMoreComplete();
                            myadapter.notifyDataSetChanged();
                        }
                    }, 1000);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            for (int i = 0; i < 10; i++) {
                                Random random = new Random();
                                int index = random.nextInt(fruitdata.length);
                                fruits.add(fruitdata[index]);
                            }
                            xRecyclerView.setNoMore(true);
                            myadapter.notifyDataSetChanged();
                        }
                    }, 1000);
                }
                times++;
            }
        });


        //分类
        initHome();
        classRcv = (RecyclerView) header.findViewById(R.id.class_recycler_view);
        GridLayoutManager classmanager = new GridLayoutManager(this, 4);
        classmanager.setOrientation(GridLayoutManager.VERTICAL);
        classRcv.setLayoutManager(classmanager);
        homeClassAdapter = new HomeClassAdapter(this, homeBeen);
        classRcv.setAdapter(homeClassAdapter);
        homeClassAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //Toast.makeText(HomeActivity.this, "我是第"+position+"个", Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


        //Gridview
        mViewPagerGrid = (ViewPager) header.findViewById(R.id.vp);
        initDatas();
        LayoutInflater inflater = LayoutInflater.from(this);
        //塞GridView至ViewPager中：
        int pageSize = pageColumn * pageRow;
        //一共的页数等于 总数/每页数量，并取整。
        int pageCount = (int) Math.ceil(mDatas.size() * 1.0 / pageSize);
        //ViewPager viewpager = new ViewPager(this);
        mViewPagerGridList = new ArrayList<View>();
        for (int index = 0; index < pageCount; index++) {
            //每个页面都是inflate出一个新实例
            GridView grid = (GridView) inflater.inflate(R.layout.item_viewpager, mViewPagerGrid, false);
            grid.setAdapter(new GridViewAdapter(this, mDatas, index));
            mViewPagerGridList.add(grid);
        }
        mViewPagerGrid.setAdapter(new MyViewPagerAdapter(mViewPagerGridList));

    }

    private void initDatas() {
        mDatas = new ArrayList<HeaderViewBean>();
        mDatas.clear();
        for (int i = 0; i < 2; i++) {
            mDatas.add(new HeaderViewBean("美食1", R.mipmap.gg));
            mDatas.add(new HeaderViewBean("电影2", R.mipmap.gg));
            mDatas.add(new HeaderViewBean("酒店3", R.mipmap.gg));
            mDatas.add(new HeaderViewBean("KTV4", R.mipmap.gg));
            mDatas.add(new HeaderViewBean("外卖5", R.mipmap.gg));
            mDatas.add(new HeaderViewBean("美女6", R.mipmap.gg));
            mDatas.add(new HeaderViewBean("美女7", R.mipmap.gg));
            mDatas.add(new HeaderViewBean("美女8", R.mipmap.gg));
            mDatas.add(new HeaderViewBean("美女9", R.mipmap.gg));
            mDatas.add(new HeaderViewBean("美女10", R.mipmap.gg));
            mDatas.add(new HeaderViewBean("美食1", R.mipmap.gg));
            mDatas.add(new HeaderViewBean("电影2", R.mipmap.gg));
            mDatas.add(new HeaderViewBean("酒店3", R.mipmap.gg));
            mDatas.add(new HeaderViewBean("KTV4", R.mipmap.gg));
            mDatas.add(new HeaderViewBean("外卖5", R.mipmap.gg));
            mDatas.add(new HeaderViewBean("美女6", R.mipmap.gg));
            mDatas.add(new HeaderViewBean("美女7", R.mipmap.gg));
            mDatas.add(new HeaderViewBean("美女8", R.mipmap.gg));
            mDatas.add(new HeaderViewBean("美女9", R.mipmap.gg));
            mDatas.add(new HeaderViewBean("美女10", R.mipmap.gg));
        }

    }
    private void initHome() {
        homeBeen.clear();
        for (int i = 0; i < 8; i++) {
            //Random random = new Random();
            //int index = random.nextInt(homeclass.length);
            homeBeen.add(homeclass[i]);
        }

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

        //添加menu项点击事件
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.backup:
//                        Toast.makeText(HomeActivity.this, "backup", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.delete:
//                        Toast.makeText(HomeActivity.this, "delete", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.setting:
//                        Toast.makeText(HomeActivity.this, "setting", Toast.LENGTH_SHORT).show();
//                        break;
//                    default:
//
//                }
//                return true;
//            }
//        });
        return toolbar;
    }


    private void HomeBottombar() {
        BottomBar bottomBar = (BottomBar) findViewById(R.id.home_bottombar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_recents:
                        //Toast.makeText(HomeActivity.this, "切换到门店", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tab_favorites:
                        //Toast.makeText(HomeActivity.this, "切换到商城", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tab_nearby:
                        //Toast.makeText(HomeActivity.this, "切换到消息", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tab_friends:
                        //Toast.makeText(HomeActivity.this, "切换我的", Toast.LENGTH_SHORT).show();
                        break;
                    default:

                }

            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_recents:
                        //Toast.makeText(HomeActivity.this, "门店重复点击做其他操作", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tab_favorites:
                        //Toast.makeText(HomeActivity.this, "商城重复点击做其他操作", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tab_nearby:
                        //Toast.makeText(HomeActivity.this, "消息重复点击做其他操作", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tab_friends:
                        //Toast.makeText(HomeActivity.this, "我的重复点击做其他操作", Toast.LENGTH_SHORT).show();
                        break;
                    default:

                }
            }
        });

    }

    /*
    * 重写onCreateOptionsMenu加载menu
    * */
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.toobar, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
