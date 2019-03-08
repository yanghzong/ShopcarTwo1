package com.umeng.soexample.shopcartwo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.umeng.soexample.shopcartwo.R;
import com.umeng.soexample.shopcartwo.bean.TwoBean;

import java.util.List;

public class TwoAdapter extends RecyclerView.Adapter<TwoAdapter.ViewHolder> {
    private Context context;
    private List<TwoBean.DataBean.ListBean> list;

    public TwoAdapter(Context context, List<TwoBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View  itemView=View.inflate(context,R.layout.item_two,null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvTwo.setText(list.get(i).getName());
        Glide.with(context).load(list.get(i).getIcon()).into(viewHolder.imgTwo);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTwo;
        private final ImageView imgTwo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTwo = itemView.findViewById(R.id.tv_two);
            imgTwo = itemView.findViewById(R.id.img_two);
        }
    }
}
