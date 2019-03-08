package com.umeng.soexample.shopcartwo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.umeng.soexample.shopcartwo.adapter.MyAdapter;
import com.umeng.soexample.shopcartwo.bean.ShopCarBean;
import com.umeng.soexample.shopcartwo.mvp.ShopCarPresenter;
import com.umeng.soexample.shopcartwo.mvp.ShopView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ShopView {

    @BindView(R.id.el_show)
    ExpandableListView elShow;
    @BindView(R.id.cb_all)
    CheckBox cbAll;
    @BindView(R.id.tv_allprice)
    TextView tvAllprice;
    @BindView(R.id.btn_allnum)
    Button btnAllnum;

    private List<ShopCarBean.DataBean> sellerData = new ArrayList<>();
    private List<ShopCarBean.DataBean.ListBean> sunlist = new ArrayList<>();
    private MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initPresenter();
        initAdapter();
    }

    private void initAdapter() {
        myAdapter = new MyAdapter(sellerData, this);
        myAdapter.setOnCartListChangeListener(new MyAdapter.OnCartListChangeListener() {
            @Override
            public void SellerSelectedChange(int groupPosition) {
                boolean b = myAdapter.isCurrentSellerAllProductSelected(groupPosition);
                myAdapter.changeCurrentSellerAllProductSelected(groupPosition, !b);
                myAdapter.notifyDataSetChanged();
                refreshAllSelectedAndTotalPriceAndTotalNumber();
            }

            @Override
            public void changeCurrentProductSelected(int groupPosition, int childPosition) {
                myAdapter.changeCurrentProductSelected(groupPosition, childPosition);
                myAdapter.notifyDataSetChanged();
                refreshAllSelectedAndTotalPriceAndTotalNumber();
            }

            @Override
            public void ProductNumberChange(int groupPosition, int childPosition, int number) {
                myAdapter.changeCurrentProductNumber(groupPosition, childPosition, number);
                myAdapter.notifyDataSetChanged();
                refreshAllSelectedAndTotalPriceAndTotalNumber();
            }
        });
        elShow.setAdapter(myAdapter);
//
        for (int i = 0; i < sellerData.size(); i++) {
            elShow.expandGroup(i);

        }
    }

    private void initPresenter() {
        ShopCarPresenter shopCarPresenter = new ShopCarPresenter();
        shopCarPresenter.atach(this);
        shopCarPresenter.getShopCart();
    }

    @Override
    public void onSuccess(Object obj) {
        ShopCarBean shopCarBean = (ShopCarBean) obj;
        List<ShopCarBean.DataBean> data = shopCarBean.getData();
        if (data != null) {
            sellerData.clear();
            sellerData.addAll(data);
        }
        myAdapter.notifyDataSetChanged();

    }

    @Override
    public void onTwo(Object obj) {

    }


    @Override
    public void onFailed(String str) {

    }

    @OnClick({R.id.cb_all, R.id.btn_allnum})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cb_all:
                boolean allProductsSelected = myAdapter.isAllProductsSelected();
                myAdapter.changeAllProductsSelected(!allProductsSelected);
                myAdapter.notifyDataSetChanged();
                //刷新底部的方法
                refreshAllSelectedAndTotalPriceAndTotalNumber();
                break;
            case R.id.btn_allnum:
                break;
        }
    }

    private void refreshAllSelectedAndTotalPriceAndTotalNumber() {

        boolean allProductsSelected = myAdapter.isAllProductsSelected();
        cbAll.setChecked(allProductsSelected);
        Double totalPrice = myAdapter.calculateTotalPrice();
        tvAllprice.setText("总价：￥" + totalPrice);
        int totalNumber = myAdapter.calculateTotalNumber();
        btnAllnum.setText("去结算(" + totalNumber + ")");
    }

    @OnClick(R.id.btn_allnum)
    public void onViewClicked() {
        Intent intent=new Intent(MainActivity.this,FenLeiActivity.class);
        startActivity(intent);
    }
}
