package com.direct.materialtest.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

import com.allen.library.SuperTextView;
import com.direct.materialtest.R;
import com.direct.materialtest.news.newsbean.NewsBean;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by allen on 2016/10/31.
 */

public class NewsAdapter extends CommonAdapter<NewsBean> {
    private Context mContext;

    public NewsAdapter(Context context, List<NewsBean> datas) {
        super(context, R.layout.news_item, datas);
        mContext = context;
    }

    @Override
    protected void convert(ViewHolder holder, NewsBean newsBean, int position) {

        ((SuperTextView) holder.getView(R.id.super_tv)).setLeftTopString(newsBean.getTitle()).setLeftBottomString(newsBean.getTime());
        Picasso.with(mContext).load(newsBean.getImgUrl()).placeholder(R.drawable.head_default
        ).into(((SuperTextView) holder.getView(R.id.super_tv))
                .getLeftIconIV());
    }


}
