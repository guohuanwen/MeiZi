package com.bcgtgjyb.huanwen.meizi.view.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.bcgtgjyb.huanwen.meizi.view.MainActivity;
import com.bcgtgjyb.huanwen.meizi.view.bean.FuliDetil;
import com.bcgtgjyb.huanwen.meizi.view.db.FuliDB;
import com.bcgtgjyb.huanwen.meizi.view.net.HttpFuliJson;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.schedulers.HandlerScheduler;
import rx.schedulers.Schedulers;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;
/**
 * Created by huanwen on 2015/9/7.
 */
public class MainPresenter {

    //网络是否连接
    private boolean net=false;
    private final String TAG="MainPresenter";
    private MainActivity view;
    private FuliDB fuliDB;
    private HttpFuliJson httpFuliJson;
    public Handler backgroundHandler;
    private  int fuliCount=1;
    private Context context;

    public MainPresenter(Context context) {
        this.context=context;
        fuliDB= FuliDB.getInstance(context);
        httpFuliJson=new HttpFuliJson(context);
        BackgroundThread backgroundThread = new BackgroundThread();
        backgroundThread.start();
        backgroundHandler = new Handler(backgroundThread.getLooper());
        //test
//        httpFuliJson.getFuliUrlSaveDB(1);
    }

    //绑定Mainactivity
    public void takeView(MainActivity view){
        this.view=view;
    }





    //第一次初始化
    public void init(){
        Log.i(TAG, "init "+fuliDB.getNoteCount());
        if(fuliDB.getNoteCount()==0){
            Observable.just("本地无数据,请下拉刷新")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(view.mainToast);
            return;
        }
//网络无法连接，则从数据库获取
        if(true){
            fuliDB.qureyUrlObservable(1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List>() {
                        @Override
                        public void onCompleted() {
                            view.setIsMoreLoad(false);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "onError "+"initDB  "+ e.toString());
                        }

                        @Override
                        public void onNext(List list) {
                            Observable.just(list).subscribe(view.replaceRecycleSubscriber);
                        }
                    });
        }
    }

    //下拉刷新
    public void pullToRefresh(){
        //从网络获取
        fuliCount=1;
        fuliDataFormNet(1).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List>() {
                    @Override
                    public void onCompleted() {
                        net = true;
                        Observable.just("").observeOn(AndroidSchedulers.mainThread()).subscribe(view.closeSwipRefershLayout);
                    }

                    @Override
                    public void onError(Throwable e) {
                        net = false;
                        Observable.just("无网络连接")
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(view.mainToast);
                    }

                    @Override
                    public void onNext(List list) {
                        List url = new ArrayList();
                        for (Object obj : list) {
                            url.add(((FuliDetil) obj).getUrl());
                        }
                        Observable.just(url)
                                .subscribe(view.replaceRecycleSubscriber);
                        httpFuliJson.saveToDB(list, fuliCount);
                    }
                });
    }





    //从数据库得到页数为 param 的所有url，返回Observers<List<String>>
    //从数据库取出数据填充
    private void fillFuliUrlViewFromDB(int param ){
       List list =fuliDB.query(param);
        Observable.just(list).subscribe(view.addRecyclerListSubscriber);
//        fuliDB.qureyUrlObservable(param)
////                .subscribeOn(HandlerScheduler.from(backgroundHandler))
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext(new Action1<List>() {
//                    @Override
//                    public void call(List list) {
//                        for (Object o : list) {
//                            Log.i(TAG, "call " + o.toString());
//                        }
//                    }
//                })
//                .subscribe(view.addRecyclerListSubscriber);
    }


    //从网络获取数据并填充view和数据库
    private void fillFuliUrlViewDBFromNet(final int param){
        fuliDataFormNet(param).subscribeOn(HandlerScheduler.from(backgroundHandler))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError " + e.toString());
                    }

                    @Override
                    public void onNext(List list) {
                        List url = new ArrayList();
                        for (Object obj : list) {
                            url.add(((FuliDetil) obj).getUrl());
                        }
                        Observable.just(url)
//                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(view.addRecyclerListSubscriber);

                        httpFuliJson.saveToDB(list,param);

                    }
                });
    }


//  从网络获取数据 返回Obsetvable<List<FuliDetil>>
    private Observable<List> fuliDataFormNet(int param){
        return httpFuliJson.getHttpFuli(param);
    }







    //底部加载更多
    public  void refreshMore(){
        Log.i(TAG, "refreshMore "+net);
        //从网络加载 net
        if(net){
            fuliCount=(int)((view.getUrlCount()+1)/httpFuliJson.getFuliNumber()) + 1;
            Log.i(TAG, "refreshMore " + fuliCount + "   "+view.getUrlCount());
            fillFuliUrlViewDBFromNet(fuliCount);
//
        }
        //从数据库加载
        else{
            fuliCount=(int)((view.getUrlCount()+1)/httpFuliJson.getFuliNumber())+1;
            Log.i(TAG, "refreshMore " + fuliCount + "   "+view.getUrlCount());
            fillFuliUrlViewFromDB(fuliCount);
        }

    }


    static class BackgroundThread extends HandlerThread {
        BackgroundThread() {
            super("SchedulerSample-BackgroundThread", THREAD_PRIORITY_BACKGROUND);
        }
    }









}
