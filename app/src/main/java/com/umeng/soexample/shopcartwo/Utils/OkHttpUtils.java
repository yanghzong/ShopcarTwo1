package com.umeng.soexample.shopcartwo.Utils;


import android.os.Handler;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtils {
    private Handler handler=new Handler();
    private static  OkHttpUtils  instance;
    private OkHttpClient client;
    private  OkHttpUtils (){
        client=new OkHttpClient();
    }

    public static  OkHttpUtils getInstance(){
        if (instance==null){
            synchronized (OkHttpUtils.class){
                if (instance==null){
                    instance=new OkHttpUtils();
                }
            }
        }
        return instance;
    }

    public  void  get(String  url, final ICallBack callBack, final Type type) {
        Request request = new Request
                .Builder()
                .url(url)
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailed(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                final Object o = gson.fromJson(string, type);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(o);
                    }
                });

            }
        });
    }



    }
