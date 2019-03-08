package com.umeng.soexample.shopcartwo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umeng.soexample.shopcartwo.R;
import com.umeng.soexample.shopcartwo.bean.FirstBean;

import java.util.List;

public class FirstAdapter extends RecyclerView.Adapter<FirstAdapter.ViewHolder> {
    //传id值
    public  interface  OnClickListener{
        void  onClick(int cid);
    }
    private OnClickListener onClickListener;

    public  void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener=onClickListener;
    }
    private Context context;
    private List<FirstBean.DataBean> list;

    public FirstAdapter(Context context, List<FirstBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View  itemView=View.inflate(context,R.layout.item_first,null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.tvFirst.setText(list.get(i).getName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cid = list.get(i).getCid();
                onClickListener.onClick(cid);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvFirst;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFirst = itemView.findViewById(R.id.tv_first);
        }
    }
}
