package com.bcgtgjyb.huanwen.meizi.view.db;

import android.content.Context;
import android.util.Log;

import com.bcgtgjyb.huanwen.meizi.view.bean.FuliDetil;
import com.bcgtgjyb.huanwen.meizi.view.tools.MyApplication;
import com.bcgtgjyb.huanwen.meizi.view.tools.MyTime;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import me.itangqi.greendao.DaoSession;
import me.itangqi.greendao.Fuli;
import me.itangqi.greendao.FuliDao;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by huanwen on 2015/9/5.
 */
public class FuliDB {
    private final String TAG="FuliDB";
    private  Context context;
    private static DaoSession daoSession;
    private static FuliDao fuliDao;
    private static FuliDB fuliDB;
//    private MyApplication myApplication=MyApplication.getInstence();

    private FuliDB( ) {
//        this.daoSession= MyApplication.getDaoSession();
//        this.fuliDao=daoSession.getFuliDao();
    }

    public static FuliDB getInstance(Context context) {
        if (fuliDB == null) {
            fuliDB = new FuliDB();
            fuliDB.daoSession = MyApplication.getDaoSession(context);
            fuliDB.fuliDao = fuliDB.daoSession.getFuliDao();
        }
        return fuliDB;
    }

//    public synchronized static FuliDB getInstence(){
//        if(fuliDB==null) fuliDB=new FuliDB();
//        return fuliDB;
//    }


//添加条目，若存在则覆盖
    public void addNote(FuliDetil fuliDetil,long nextId,long count){
        long id=new MyTime().translateTime(fuliDetil.getPublishedAt());
        Fuli fuli=new Fuli(id,fuliDetil.getWho(),fuliDetil.getPublishedAt(),fuliDetil.getDesc(),//
        fuliDetil.getType(),fuliDetil.getUrl(),fuliDetil.getUsed(),fuliDetil.getObjectId(),//
         fuliDetil.getCreatedAt(),fuliDetil.getUpdatedAt(),nextId,count);
//        fuliDao.insert(fuli);
//        fuliDao.update(fuli);
        fuliDao.insertOrReplace(fuli);
//        Log.i(TAG, "addNote  "+fuliDao.count());
    }

    public void deleteNote(){
        fuliDao.deleteAll();
    }

    public long getNoteCount(){
        return fuliDao.count();
    }

    //返回所有Url
    public List getUrl(){
        List list=new ArrayList<String>();
        QueryBuilder qb=fuliDao.queryBuilder();
        qb.orderDesc(FuliDao.Properties.Id);
        List fuliList=qb.list();
        for (int i=(fuliList.size()-1);i>=0;i--){
            Fuli fuli=(Fuli)fuliList.get(i);
            list.add(fuli.getUrl());
        }
        return list;
    }

    //效率低
    private List getUrl(long start,long end){
        List list=new ArrayList<String>();
        QueryBuilder qb=fuliDao.queryBuilder();
        qb.orderDesc(FuliDao.Properties.Id);
//        qb.where(new WhereCondition.StringCondition("select * from table where .... order by XX desc limit 0,20"));
//        qb.where(new WhereCondition.StringCondition("_ID IN " + "(SELECT * FROM FULI LIMIT " + start + "," + end + ")"));
//        qb.limit((int)param);
        List fuliList=qb.list();
        for(int i=0;i<fuliList.size();i++){
            if(i>=start&&i<=end){
                list.add(((Fuli)fuliList.get(i)).getUrl());
            }
        }

        return list;
    }

    public List query(int count){
        List list = new ArrayList();
        QueryBuilder qb = fuliDao.queryBuilder();
        qb.orderDesc(FuliDao.Properties.Id);
        qb.where(FuliDao.Properties.Count.eq(count));
//                        qb.limit(HttpFuliJson.fuliNumber);
        List fuliList = qb.list();
        Log.i(TAG, "call " + count + "   " + fuliList.size());
        for (int i = 0; i < fuliList.size(); i++) {
            Log.i(TAG, "call " + ((Fuli) fuliList.get(i)).getUrl());
            list.add(((Fuli) fuliList.get(i)).getUrl());
        }
        return list;
    }
//根据第几页查询数据库
    public Observable<List> qureyUrlObservable(final int count){
        return Observable
                .create(new Observable.OnSubscribe<List>() {
                    @Override
                    public void call(Subscriber<? super List> subscriber) {
                        List list = new ArrayList();
                        QueryBuilder qb = fuliDao.queryBuilder();
                        qb.orderDesc(FuliDao.Properties.Id);
                        qb.where(FuliDao.Properties.Count.eq(count));
//                        qb.limit(HttpFuliJson.fuliNumber);
                        List fuliList = qb.list();
                        Log.i(TAG, "call " + count + "   " + fuliList.size());
                        for (int i = 0; i < fuliList.size(); i++) {
                            Log.i(TAG, "call " + ((Fuli) fuliList.get(i)).getUrl());
                            list.add(((Fuli) fuliList.get(i)).getUrl());
                        }
                        subscriber.onNext(list);
                        subscriber.onCompleted();
                    }
                });
//                .defer(new Func0<Observable<List>>() {
//                    @Override
//                    public Observable<List> call() {
//                        List list = new ArrayList();
//                        QueryBuilder qb = fuliDao.queryBuilder();
//                        qb.orderDesc(FuliDao.Properties.Id);
//                        qb.where(FuliDao.Properties.Count.eq(count));
////                        qb.limit(HttpFuliJson.fuliNumber);
//                        List fuliList = qb.list();
//                        Log.i(TAG, "call " + count + "   " + fuliList.size());
//                        for (int i = 0; i < fuliList.size(); i++) {
//                            Log.i(TAG, "call " + ((Fuli) fuliList.get(i)).getUrl());
//                            list.add(((Fuli) fuliList.get(i)).getUrl());
//                        }
//                        return Observable.just(list);
//                    }
//                });

    }






}
