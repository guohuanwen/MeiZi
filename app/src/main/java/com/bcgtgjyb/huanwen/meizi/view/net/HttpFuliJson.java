package com.bcgtgjyb.huanwen.meizi.view.net;

import android.content.Context;
import android.util.Log;

import com.bcgtgjyb.huanwen.meizi.view.bean.FuliDetil;
import com.bcgtgjyb.huanwen.meizi.view.bean.FuliJson;
import com.bcgtgjyb.huanwen.meizi.view.db.FuliDB;
import com.bcgtgjyb.huanwen.meizi.view.tools.MyTime;
import com.google.gson.Gson;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by huanwen on 2015/9/6.
 */
public class HttpFuliJson {
    private Context context;
    private final String TAG="HttpFuliJson";
    private int fuliCount=1;
    public final static int fuliNumber=10;
    private FuliDB fuliDB;
    private MyTime myTime;
//    private MyApplication myApplication=MyApplication.getInstence();
    public HttpFuliJson(Context context) {
        this.context= context;
        fuliDB=FuliDB.getInstance(context);
        myTime= new MyTime();
    }

    public long getFuliNumber(){
        return this.fuliNumber;
    }



    public void getFuliUrlSaveDB(int param){
        getHttpFuli(param).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(saveToDB);
    }

    /**
    *
    * 每页fuliNumber个，
     */
//    public Observable<List> getHttpAll(int data1,int data2)  {
//        return Observable.defer(new Func0<Observable<List>>() {
//            @Override
//            public Observable<List> call() {
//                try {
//
////                      HttpRequest httpRequest = new HttpRequest();
////                      String json = httpRequest.httpGet("http://gank.avosapps.com/api/data/福利/" + fuliNumber + "/" + integer);
//
//                    String text = new ReadAssetsText().readText("jsonall");
//                    Log.i(TAG, "run " + text);
//                    Gson gson = new Gson();
//                    EveryDayJson everyDayJson = gson.fromJson(text, EveryDayJson.class);
//                    EveryDayJson.Result result = everyDayJson.getResult();
//                    List<EveryDayJson.Result.Detil> android=result.getAndroid();
//                    List<EveryDayJson.Result.Detil> ios=result.getiOS();
//                    List<EveryDayJson.Result.Detil> expandText=result.getExpandText();
//                    List<EveryDayJson.Result.Detil> video=result.getVideo();
//
//                    return Observable.just(list);
//                } catch (Exception e) {
//                    return Observable.error(e);
//                }
//
//
//                return null;
//            }
//        });
//
//
//
//
//    }


    /**
     *
     * 每页fuliNumber个，
     * @param param 第param页
     */
    public Observable<List> getHttpFuli(final int param)  {
        return Observable.just(param)
                .flatMap(new Func1<Integer, Observable<List>>() {
                    @Override
                    public Observable<List> call(Integer integer) {
                        try {
                            HttpRequest httpRequest = new HttpRequest();
                            String json = httpRequest.httpGet("http://gank.avosapps.com/api/data/福利/" + fuliNumber + "/" + integer);

//                            String text = new ReadAssetsText().readText("json20-"+param);
//                            Log.i(TAG, "run " + text);
                            Gson gson = new Gson();
                            FuliJson fuliJson = gson.fromJson(json, FuliJson.class);
                            List list = fuliJson.getResults();
                            //List<FuliDetil>
                            return Observable.just(list);
                        } catch (Exception e) {
                            return Observable.error(e);
                        }
                    }
                });
    }

    public void saveToDB(final List list,final int param){
        new Thread(new Runnable() {
            @Override
            public void run() {
                long start = fuliDB.getNoteCount();
                for (int i = 0; i < list.size(); i++) {
                    FuliDetil fuliDetil = (FuliDetil) list.get(i);
                    if ((i + 1) < list.size()) {
                        Long id = myTime.translateTime(((FuliDetil) list.get(i + 1)).getPublishedAt());
                        fuliDB.addNote(fuliDetil, id, param);
                    } else {
                        fuliDB.addNote(fuliDetil, 0, param);
                    }
                }
            }
        }).start();

    }

    //将List<Detil>保存到数据库
    public Subscriber<List> saveToDB=new Subscriber<List>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Log.i(TAG, "onError "+e.toString());
        }

        @Override
        public void onNext(List list) {
            long start = fuliDB.getNoteCount();
            for (int i = 0; i < list.size(); i++) {
                FuliDetil fuliDetil = (FuliDetil) list.get(i);
                if ((i + 1) < list.size()) {
                    Long id = myTime.translateTime(((FuliDetil) list.get(i + 1)).getPublishedAt());

                    fuliDB.addNote(fuliDetil, id, fuliCount);
                } else {
                    fuliDB.addNote(fuliDetil, 0, fuliCount);
                }
            }
        }
    };



}
