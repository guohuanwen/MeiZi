package com.bcgtgjyb.huanwen.meizi.view.presenter;

import android.util.Log;

import com.bcgtgjyb.huanwen.meizi.view.MainActivity;
import com.bcgtgjyb.huanwen.meizi.view.db.FuliDB;
import com.bcgtgjyb.huanwen.meizi.view.net.HttpFuliJson;

import java.util.List;

/**
 * Created by huanwen on 2015/9/7.
 */
public class MainPresenter {
    private final String TAG="MainPresenter";
    private MainActivity view;
    private FuliDB fuliDB;
    private HttpFuliJson httpFuliJson;

    public MainPresenter() {
        fuliDB=FuliDB.getInstence();
        httpFuliJson=new HttpFuliJson();
    }

    public void takeView(MainActivity view){
        this.view=view;
    }


    public void initRecycler(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List list=fuliDB.getUrl();
                setRecyclerView(list);
            }
        }).start();

    }


    public void refreshUrl(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "refresh " + Thread.currentThread().getName());
                long start=fuliDB.getNoteCount();
                httpFuliJson.saveJson();
                if(start==0){
                    setRecyclerView(fuliDB.getUrl());
                }else {
                    httpFuliJson.saveJson();
                    long end = fuliDB.getNoteCount();
                    if ((end - start) == httpFuliJson.getFuliNumber()) {
                        refreshUrl();
                    } else {
                        setRecyclerView(fuliDB.getUrl());
                    }

                }
            }
        }).start();
    }


    private void setRecyclerView(List list){
        view.setRecyclerViewAdapter(list);
        view.closeSwipeRefreshLayout();
    }






}
