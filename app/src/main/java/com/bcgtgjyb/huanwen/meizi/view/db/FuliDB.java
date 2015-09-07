package com.bcgtgjyb.huanwen.meizi.view.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bcgtgjyb.huanwen.meizi.view.bean.FuliDetil;
import com.bcgtgjyb.huanwen.meizi.view.tools.MyApplication;
import com.bcgtgjyb.huanwen.meizi.view.tools.MyTime;

import java.util.ArrayList;
import java.util.List;

import me.itangqi.greendao.DaoSession;
import me.itangqi.greendao.Fuli;
import me.itangqi.greendao.FuliDao;

/**
 * Created by huanwen on 2015/9/5.
 */
public class FuliDB {
    private final String TAG="FuliDB";
    private Context context;
    private SQLiteDatabase db;
    private DaoSession daoSession;
    private FuliDao fuliDao;
    private static FuliDB fuliDB;

    private FuliDB() {
        this.context= MyApplication.getContext();
        this.daoSession=MyApplication.getDaoSession();
        this.db=MyApplication.getDb();
        this.fuliDao=daoSession.getFuliDao();
    }

    public synchronized static FuliDB getInstence(){
        if(fuliDB==null) fuliDB=new FuliDB();
        return fuliDB;
    }


    public void addNote(FuliDetil fuliDetil,long nextId){
        long id=new MyTime().translateTime(fuliDetil.getPublishedAt());
        Fuli fuli=new Fuli(id,fuliDetil.getWho(),fuliDetil.getPublishedAt(),fuliDetil.getDesc(),//
        fuliDetil.getType(),fuliDetil.getUrl(),fuliDetil.getUsed(),fuliDetil.getObjectId(),//
         fuliDetil.getCreatedAt(),fuliDetil.getUpdatedAt(),nextId);
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

    public List getUrl(){
        List list=new ArrayList<String>();
        List fuliList=fuliDao.queryBuilder().list();
        for (int i=(fuliList.size()-1);i>=0;i--){
            Fuli fuli=(Fuli)fuliList.get(i);
            list.add(fuli.getUrl());
        }
        return list;
    }


}
