package com.direct.materialtest.bilibili;

import android.content.Context;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.direct.materialtest.R;
import com.direct.materialtest.base.BaseActivity;
import com.direct.materialtest.util.StatusBarUtil;
import com.direct.materialtest.widget.NoScrollViewPager;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.BindView;

public class BiliBiliActivity extends BaseActivity {
    private Context mContext = this;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {"头条", "社会", "国内", "国际", "娱乐", "体育", "军事",
    "科技","财经","时尚"};
    private MyPagerAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bili_bili);
        SetToolBar(R.id.toobar, R.id.toobar_title, "BiliBili新闻");
        for (String title : mTitles) {
            mFragments.add(SimpleCardFragment.getInstance(title));
        }

        View decorView = getWindow().getDecorView();

        ViewPager vp = ViewFindUtils.find(decorView, R.id.view_pager);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);

        /** tab固定宽度 */
        SlidingTabLayout tabLayout= ViewFindUtils.find(decorView, R.id.tab_bayout);
        tabLayout.setViewPager(vp);
        vp.setCurrentItem(0);
    }



    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
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

        return toolbar;
    }

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
