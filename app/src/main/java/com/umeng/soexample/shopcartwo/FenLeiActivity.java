package com.umeng.soexample.shopcartwo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.umeng.soexample.shopcartwo.Utils.UrlUtils;
import com.umeng.soexample.shopcartwo.adapter.FirstAdapter;
import com.umeng.soexample.shopcartwo.adapter.TwoAdapter;
import com.umeng.soexample.shopcartwo.bean.FirstBean;
import com.umeng.soexample.shopcartwo.bean.TwoBean;
import com.umeng.soexample.shopcartwo.mvp.ShopCarPresenter;
import com.umeng.soexample.shopcartwo.mvp.ShopView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FenLeiActivity extends AppCompatActivity  implements ShopView {

    @BindView(R.id.rv_first)
    RecyclerView rvFirst;
    @BindView(R.id.rv_two)
    RecyclerView rvTwo;
    private ShopCarPresenter shopCarPresenter;
    private List<FirstBean.DataBean> firstlist=new ArrayList<>();
    private List<TwoBean.DataBean.ListBean> twolist=new ArrayList<>();
    private FirstAdapter firstAdapter;
    private TwoAdapter twoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fen_lei);
        ButterKnife.bind(this);

        initPresenter();

        initAdapter();
    }

    private void initPresenter() {
        shopCarPresenter = new ShopCarPresenter();
        shopCarPresenter.atach(this);
        shopCarPresenter.getFirst(UrlUtils.FirstUrl);
        shopCarPresenter.getTwo(UrlUtils.TwoUrl+1);

    }

    private void initAdapter() {
        firstAdapter = new FirstAdapter(FenLeiActivity.this,firstlist);
        rvFirst.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvFirst.setAdapter(firstAdapter);
        firstAdapter.setOnClickListener(new FirstAdapter.OnClickListener() {
            @Override
            public void onClick(int cid) {
                Toast.makeText(FenLeiActivity.this, ""+cid, Toast.LENGTH_SHORT).show();
                shopCarPresenter.getTwo(UrlUtils.TwoUrl+cid);
            }
        });
        twoAdapter = new TwoAdapter(FenLeiActivity.this,twolist);
        rvTwo.setAdapter(twoAdapter);
        rvTwo.setLayoutManager(new GridLayoutManager(this,3));

    }

    @Override
    public void onSuccess(Object obj) {
        FirstBean firstBean= (FirstBean) obj;
        if (firstBean!=null){
            firstlist.clear();
            firstlist.addAll(firstBean.getData());
            firstAdapter.notifyDataSetChanged();
        }




    }

    @Override
    public void onTwo(Object obj) {
        TwoBean twoBean= (TwoBean) obj;
        List<TwoBean.DataBean> data = twoBean.getData();
        if (data!=null){
            twolist.clear();
            for (int i = 0; i <data.size() ; i++) {
                twolist.addAll(data.get(i).getList());
                twoAdapter.notifyDataSetChanged();
            }


        }
    }

    @Override
    public void onFailed(String str) {

    }
}
