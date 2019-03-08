package com.umeng.soexample.shopcartwo.mvp;

import com.google.gson.reflect.TypeToken;
import com.umeng.soexample.shopcartwo.Utils.ICallBack;
import com.umeng.soexample.shopcartwo.bean.FirstBean;
import com.umeng.soexample.shopcartwo.bean.ShopCarBean;
import com.umeng.soexample.shopcartwo.bean.TwoBean;

import java.lang.reflect.Type;

public class ShopCarPresenter implements ShowPresenterInterface {

    private ShopCarModel shopCarModel;
    private ShopView shopView;

    public  void  atach(ShopView shopView){
        this.shopView=shopView;
        shopCarModel=new ShopCarModel();
    }
    @Override
    public void getShopCart() {
        Type type=new TypeToken<ShopCarBean>(){}.getType();
        shopCarModel.getShopCar(new ICallBack() {
            @Override
            public void onSuccess(Object obj) {
                ShopCarBean shopCarBean= (ShopCarBean) obj;
                if (shopCarBean!=null){
                    shopView.onSuccess(shopCarBean);
                }
            }

            @Override
            public void onFailed(Exception e) {
                shopView.onFailed(e.getMessage());
            }


        },type);

    }

    @Override
    public void getFirst(String url) {
        Type type=new TypeToken<FirstBean>(){}.getType();
        shopCarModel.getFirst(url,new ICallBack() {
            @Override
            public void onSuccess(Object obj) {
                FirstBean firstBean= (FirstBean) obj;
                if (firstBean!=null){
                    shopView.onSuccess(firstBean);
                }
            }

            @Override
            public void onFailed(Exception e) {
                shopView.onFailed(e.getMessage());
            }


        },type);



    }
    @Override
    public void getTwo(String url) {
        Type type=new TypeToken<TwoBean>(){}.getType();
        shopCarModel.getTwo(url,new ICallBack() {
            @Override
            public void onSuccess(Object obj) {
                TwoBean twoBean= (TwoBean) obj;
                if (twoBean!=null){
                    shopView.onTwo(twoBean);
                }
            }

            @Override
            public void onFailed(Exception e) {
                shopView.onFailed(e.getMessage());
            }


        },type);



    }

    public  void  detach(){
        if (shopCarModel!=null){
            shopCarModel=null;
        }
        if (shopView!=null){
            shopView=null;
        }
    }
}
