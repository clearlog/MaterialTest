package com.direct.materialtest.home.homeadapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.direct.materialtest.R;
import com.direct.materialtest.home.homebean.HomeBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HeQiang on 2017/10/23 0023.
 */

public class HomeClassAdapter extends CommonAdapter<HomeBean>{
    private Context context;
    private OnItemClickListener itemClickListeners;
    public HomeClassAdapter(Context context, List<HomeBean> data){
        super(context,R.layout.home_class_item,data);
        this.context=context;
    }

    @Override
    protected void convert(final ViewHolder holder, HomeBean homeBean, final int position) {
        holder.setText(R.id.class_name,homeBean.getClassName());
        Glide.with(context).load(homeBean.getClassImg()).into((CircleImageView) holder.getView(R.id.class_img));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
