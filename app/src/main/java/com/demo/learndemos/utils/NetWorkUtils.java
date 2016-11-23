package com.demo.learndemos.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by zyf on 2016/10/25.
 */

public class NetWorkUtils<T> {

    private Context mContext;

    private static NetWorkUtils netWorkUtils = null;

    private NetWorkUtils(Context context) {
        mContext = context;
    }

    public static NetWorkUtils getInstance(Context context) {
        if (netWorkUtils == null) {
            synchronized (netWorkUtils) {
                if (netWorkUtils == null) {
                    netWorkUtils = new NetWorkUtils(context);
                }
            }
        }
        return netWorkUtils;
    }

    public void makeRequest(String url, final IRequestCallBack callBack) {
        RequestQueue volley = Volley.newRequestQueue(mContext);
        volley.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {

            }
        });
    }

    public interface IRequestCallBack<T> {

        void onResponseSuccess(T t);

        void onResponseFailure();

        void onRequestComplete(String url);

    }


}
