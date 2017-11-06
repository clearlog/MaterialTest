package com.direct.materialtest.news.adapter;

import android.content.Context;
import android.widget.TextView;

import com.direct.materialtest.R;
import com.direct.materialtest.news.newsbean.NewsBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by HeQiang on 2017/10/17 0017.
 */

public class NewsTitleAdapter extends CommonAdapter<String> {
    private Context mContext;
    public NewsTitleAdapter(Context context, List<String> datas) {
        super(context, R.layout.news_nav_item, datas);
        mContext = context;
    }

    @Override
    protected void convert(ViewHolder holder, String s, int position) {
        TextView navtitleitem=holder.getView(R.id.nav_title_item);
        navtitleitem.setText(s);

    }
}
