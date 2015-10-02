package com.bcgtgjyb.huanwen.meizi.view;

import android.test.AndroidTestCase;
import android.util.Log;

import com.bcgtgjyb.huanwen.meizi.view.db.FuliDB;
import com.bcgtgjyb.huanwen.meizi.view.net.HttpFuliJson;

import rx.Subscriber;

/**
 * Created by huanwen on 2015/9/8.
 */
public class TestRefresh extends AndroidTestCase{
    private final static String TAG="TestRefresh";
    private HttpFuliJson httpFuliJson;
    private FuliDB fuliDB;

    public void test(){
        Log.i(TAG, "test ");
        httpFuliJson=new HttpFuliJson(getContext());
        fuliDB=FuliDB.getInstance(getContext());
//        httpFuliJson.getFuliUrlSaveDB(1);

    }

//    public void testHttpFuliJson(){
//        HttpFuliJson httpFuliJson=new HttpFuliJson(getContext());
//        httpFuliJson.getHttpFuli(1).subscribe(httpFuliJson.saveFuliSubscriber);
//    }

//    public void testFuliDBGteURL(){
//        Log.i(TAG, "testFuliDBGteURL ");
//        FuliDB filiDB = FuliDB.getInstance(getContext());
//        List list=filiDB.getUrl(0,0);
//        for(Object onj:list){
//            Log.i(TAG, "testFuliDBGteURL "+onj.toString());
//        }
//
//    }

//    public void testDB(){
//        DaoSession daoSession = MyApplication.getDaoSession(getContext());
//        FuliDao fuliDao = daoSession.getFuliDao();
//        QueryBuilder.LOG_SQL = true; QueryBuilder.LOG_VALUES = true;
////        QueryBuilder query=fuliDao.queryBuilder();
////
////        query.limit(20);
////        query.where(FuliDao.Properties.Who.isNotNull());
////        Query query = fuliDao.queryBuilder().where(
////                new WhereCondition.StringCondition("SELECT T.'_id',T.'WHO',T.'PUBLISHED_AT',T.'DESC',T.'TYPE',T.'URL',T.'USED',T.'OBJECT_ID',T.'CREATED_AT',T.'UPDATED_AT',T.'NEXT_ID' FROM FULI T  LIMIT 1,3")).build();
////        Query query = fuliDao.queryRawCreate("FROM FULI");
//        Query query=fuliDao.queryBuilder().build();
//        query.
////        query.where(new WhereCondition.StringCondition("_ID IN " + "(SELECT * FROM FULI LIMIT " + 2 + "," + 5 + ")"));
//        List list=query.list();
//        for (Object obj:list){
//            Log.i(TAG, "testDB "+obj.toString());
//        }
//    }
//

//    public void testRefresh(){
//        MainPresenter mainPresenter=new MainPresenter(getContext());
//        mainPresenter.fuliUrlObservable()
//                .subscribe(new Subscriber<List>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.i(TAG, "onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.i(TAG, "onError ");
//                    }
//
//                    @Override
//                    public void onNext(List list) {
//                        Log.i(TAG, "onNext " + list.size());
//                        for (int i = 0; i < list.size(); i++) {
//                            Log.i(TAG, "onNext " + list.get(i));
//                        }
//                    }
//                });
//
//        mainPresenter.fuliUrlObservable()
//                .subscribe(new Subscriber<List>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.i(TAG, "onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.i(TAG, "onError ");
//                    }
//
//                    @Override
//                    public void onNext(List list) {
//                        Log.i(TAG, "onNext " + list.size());
//                        for (int i = 0; i < list.size(); i++) {
//                            Log.i(TAG, "onNext " + list.get(i));
//                        }
//                    }
//                });
//
//
//    }

}
