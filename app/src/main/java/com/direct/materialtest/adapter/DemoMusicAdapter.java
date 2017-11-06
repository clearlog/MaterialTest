package com.direct.materialtest.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.allen.library.SuperTextView;
import com.direct.materialtest.DemoActivity;
import com.direct.materialtest.R;
import com.direct.materialtest.music.bean.MusicMedia;
import com.direct.materialtest.news.newsbean.NewsBean;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by HeQiang on 2017/10/18 0018.
 */

public class DemoMusicAdapter extends CommonAdapter<MusicMedia> {

    private Context mContext;
    private OnItemClickListener itemClickListeners;

    public DemoMusicAdapter(Context context, ArrayList<MusicMedia> data) {
        super(context, R.layout.demo_music_item, data);
        mContext = context;
    }



    @Override
    protected void convert(final ViewHolder holder, MusicMedia musicMedia, final int position) {
        ((SuperTextView) holder.getView(R.id.super_tv1))
                .setLeftTopString(musicMedia.getArtist())
                .setLeftBottomString(musicMedia.getTitle())
                .setRightTopString(musicMedia.getSize())
                .setRightBottomString(musicMedia.getTime());
        Picasso.with(mContext).load(musicMedia.getImgID()).placeholder(R.drawable.ic_launcher
        ).into(((SuperTextView) holder.getView(R.id.super_tv1))
                .getLeftIconIV());

        holder.itemView.findViewById(R.id.super_tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListeners.onItemClick(view,holder,position);
            }
        });

    }

    @Override
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        super.setOnItemClickListener(onItemClickListener);
        this.itemClickListeners = onItemClickListener;
    }

}
