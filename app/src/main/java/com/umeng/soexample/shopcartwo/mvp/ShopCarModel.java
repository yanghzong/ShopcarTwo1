package com.umeng.soexample.shopcartwo.mvp;


import com.umeng.soexample.shopcartwo.Utils.ICallBack;
import com.umeng.soexample.shopcartwo.Utils.OkHttpUtils;
import com.umeng.soexample.shopcartwo.Utils.UrlUtils;


import java.lang.reflect.Type;


public class ShopCarModel implements ShowModelInterface{

    @Override
    public void getShopCar(ICallBack callBack, Type type) {

        OkHttpUtils.getInstance().get(UrlUtils.ShopCarUrl,callBack,type);
    }



    @Override
    public void getFirst(String url,ICallBack callBack, Type type) {
        OkHttpUtils .getInstance().get(url,callBack,type);
    }
    @Override
    public void getTwo(String url,ICallBack callBack, Type type) {
        OkHttpUtils .getInstance().get(url,callBack,type);
    }
}
