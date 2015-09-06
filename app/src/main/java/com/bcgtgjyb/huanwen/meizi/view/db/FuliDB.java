package com.bcgtgjyb.huanwen.meizi.view.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bcgtgjyb.huanwen.meizi.view.bean.FuliDetil;
import com.bcgtgjyb.huanwen.meizi.view.tools.MyApplication;
import com.bcgtgjyb.huanwen.meizi.view.tools.MyTime;

import me.itangqi.greendao.DaoSession;
import me.itangqi.greendao.Fuli;
import me.itangqi.greendao.FuliDao;

/**
 * Created by huanwen on 2015/9/5.
 */
public class FuliDB {
    private Context context;
    private SQLiteDatabase db;
    private DaoSession daoSession;
    private FuliDao fuliDao;

    public FuliDB() {
        this.context= MyApplication.getContext();
        this.daoSession=MyApplication.getDaoSession();
        this.db=MyApplication.getDb();
        this.fuliDao=daoSession.getFuliDao();
    }


    public void addNote(FuliDetil fuliDetil){
        long id=new MyTime().translateTime(fuliDetil.getCreatedAt());
        Fuli fuli=new Fuli(id,fuliDetil.getWho(),fuliDetil.getPublishedAt(),fuliDetil.getDesc(),//
        fuliDetil.getType(),fuliDetil.getUrl(),fuliDetil.getUsed(),fuliDetil.getObjectId(),//
         fuliDetil.getCreatedAt(),fuliDetil.getUpdatedAt());
        fuliDao.insert(fuli);
    }


}
