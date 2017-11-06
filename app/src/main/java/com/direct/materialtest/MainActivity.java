package com.direct.materialtest;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.direct.materialtest.adapter.FruitAdapter;
import com.direct.materialtest.base.BaseActivity;
import com.direct.materialtest.bilibili.BiliBiliActivity;
import com.direct.materialtest.databean.Fruit;
import com.direct.materialtest.framelayout.FourthFrame;
import com.direct.materialtest.framelayout.GameFrame;
import com.direct.materialtest.framelayout.LiveFrame;
import com.direct.materialtest.framelayout.ThirdFrame;
import com.direct.materialtest.util.StatusBarUtil;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
import com.zaaach.citypicker.CityPickerActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends BaseActivity {
    private Fragment frame1,frame2,frame3,frame4;
    private int mStatusBarColor;
    private int mAlpha = StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA;

    private static final int REQUEST_CODE_PICK_CITY = 233;
    private RecyclerView recyclerView;
    private List<Fruit> fruitList = new ArrayList<>();
    private FruitAdapter fruitAdapter;
    private Fruit[] fruits = {new Fruit("苹果", R.mipmap.mm), new Fruit("香蕉", R.mipmap.mm),
            new Fruit("梨子", R.mipmap.mm), new Fruit("桃子", R.mipmap.mm),
            new Fruit("草莓", R.mipmap.mm), new Fruit("葡萄", R.mipmap.mm),
            new Fruit("柿子", R.mipmap.mm), new Fruit("枇杷", R.mipmap.mm)};
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottombar();
        SetToolBar(R.id.toobar, R.id.toobar_title, "外婆家的小院");
        // 初始化并设置当前Fragment
        initFragment(1);
        initFruits();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        fruitAdapter = new FruitAdapter(fruitList, this);
        recyclerView.setAdapter(fruitAdapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "你点击了FloatingActionButton", Toast.LENGTH_SHORT).show();
//                Snackbar.make(view, "Data delete", Snackbar.LENGTH_SHORT)
//                        .setAction("Undo", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                Toast.makeText(MainActivity.this, "你点击了FloatingActionButton", Toast.LENGTH_SHORT).show();
//                            }
//                        }).show();
            }
        });

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RefreshFruit();
            }
        });


    }

    //设置fragmen
    private void initFragment(int index) {
        // 由于是引用了V4包下的Fragment，所以这里的管理器要用getSupportFragmentManager获取
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 开启事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 隐藏所有Fragment
        hideFragment(transaction);
        switch (index) {
            case 1:
                if (frame1 == null) {
                    frame1 = new LiveFrame();
                    transaction.add(R.id.frame_layout, frame1);
                } else {
                    transaction.show(frame1);
                }
                break;
            case 2:
                if (frame2 == null) {
                    frame2 = new GameFrame();
                    transaction.add(R.id.frame_layout, frame2);
                } else {
                    transaction.show(frame2);
                }
                break;
            case 3:
                if (frame3 == null) {
                    frame3 = new ThirdFrame();
                    transaction.add(R.id.frame_layout, frame3);
                } else {
                    transaction.show(frame3);
                }
                break;
            case 4:
                if (frame4 == null) {
                    frame4 = new FourthFrame();
                    transaction.add(R.id.frame_layout, frame4);
                } else {
                    transaction.show(frame4);
                }
                break;
            default:
                break;

        }
        // 提交事务
        transaction.commit();
    }


    private void bottombar() {
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_favorites:
                        initFragment(1);
                        //Toast.makeText(MainActivity.this, "切换直播", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tab_nearby:
                        initFragment(2);
                        //Toast.makeText(MainActivity.this, "切换游戏", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tab_friends:
                        initFragment(3);
                        //Toast.makeText(MainActivity.this, "切换动漫", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tab_center:
                        initFragment(4);
                        //Toast.makeText(MainActivity.this, "切换电影", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                }

            }
        });
        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_favorites:
                        //Toast.makeText(MainActivity.this, "直播重复点击做其他操作", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tab_nearby:
                        //Toast.makeText(MainActivity.this, "游戏重复点击做其他操作", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tab_friends:
                        //Toast.makeText(MainActivity.this, "动漫重复点击做其他操作", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tab_center:
                        //Toast.makeText(MainActivity.this, "电影重复点击做其他操作", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                }
            }
        });

    }


    private void RefreshFruit() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        fruitAdapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });

            }
        }).start();
    }

    private void initFruits() {
        fruitList.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }

    }

    private Toolbar SetToolBar(int Id, int titleId, String titleString) {
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(Id);
        toolbar.setNavigationIcon(R.mipmap.header);
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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
        //navigationView.setCheckedItem(R.id.nav_call);//默认选中第一个
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setCheckable(false);//设置选项可选
                item.setChecked(false);//设置选型被选中
                drawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.nav_mendian:
                        Intent homeintent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(homeintent);
                        //st.makeText(MainActivity.this, "门店", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_yule:
                        Intent musicintent = new Intent(MainActivity.this, MusicActivity.class);
                        startActivity(musicintent);
                        //Toast.makeText(MainActivity.this, "音乐", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_news:
                        Intent newsintent = new Intent(MainActivity.this, NewsActivity.class);
                        startActivity(newsintent);
                        //Toast.makeText(MainActivity.this, "新闻", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_weather:
                        Intent weatherintent = new Intent(MainActivity.this, WeatherActivity.class);
                        startActivity(weatherintent);
                        //Toast.makeText(MainActivity.this, "天气", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_other:
                        startActivityForResult(new Intent(MainActivity.this, CityPickerActivity.class),
                                REQUEST_CODE_PICK_CITY);
                        //Toast.makeText(MainActivity.this, "其他", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_center:
                        Intent centerintent = new Intent(MainActivity.this, CenterActivity.class);
                        startActivity(centerintent);
                        //Toast.makeText(MainActivity.this, "个人中心", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                }
                return true;
            }
        });
        //添加左边图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
                //Toast.makeText(MainActivity.this, "你点击了左边header", Toast.LENGTH_SHORT).show();
            }
        });
        //添加menu项点击事件
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.backup:
                        Intent biliintent = new Intent(MainActivity.this, BiliBiliActivity.class);
                        startActivity(biliintent);
                        //Toast.makeText(MainActivity.this, "网易音乐", Toast.LENGTH_SHORT).show();
                        break;
//                    case R.id.delete:
//                        //Toast.makeText(MainActivity.this, "delete", Toast.LENGTH_SHORT).show();
//                        break;
                    case R.id.setting:
                        //Toast.makeText(MainActivity.this, "setting", Toast.LENGTH_SHORT).show();
                        break;
                    default:

                }
                return true;
            }
        });
        return toolbar;
    }

    //隐藏Fragment
    private void hideFragment(FragmentTransaction transaction) {
        if (frame1 != null) {
            transaction.hide(frame1);
        }
        if (frame2 != null) {
            transaction.hide(frame2);
        }
        if (frame3 != null) {
            transaction.hide(frame3);
        }
        if (frame4 != null) {
            transaction.hide(frame4);
        }

    }

    /*
    * 重写onCreateOptionsMenu加载menu
    * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toobar, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK) {
            if (data != null) {
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                //resultTV.setText("当前选择：" + city);
            }
        }
    }


    @Override
    protected void setStatusBar() {
        mStatusBarColor = getResources().getColor(R.color.colorPrimary);
        StatusBarUtil.setColorForDrawerLayout(this, (DrawerLayout) findViewById(R.id.drawer_layout), mStatusBarColor, mAlpha);
    }

}
