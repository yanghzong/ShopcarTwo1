package com.umeng.soexample.shopcartwo.mvp;

import com.umeng.soexample.shopcartwo.Utils.ICallBack;

import java.lang.reflect.Type;

public interface ShowModelInterface {
    void getShopCar(final ICallBack callBack, final Type type);
    void getFirst(String url,final ICallBack callBack, final Type type);
    void getTwo(String url,final ICallBack callBack, final Type type);
}
