package com.bcgtgjyb.huanwen.meizi.view.tools;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import me.itangqi.greendao.DaoMaster;
import me.itangqi.greendao.DaoSession;

/**
 * Created by huanwen on 2015/9/5.
 */
public class MyApplication extends Application{
    private static Context context;
    private DaoMaster daoMaster;
    private static DaoSession daoSession;
    private static SQLiteDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        setupDatabase();
        initImageLoad();
    }

    public static Context getContext(){
        return context;
    }

    public static DaoSession getDaoSession(){
        return daoSession;
    }

    public static SQLiteDatabase getDb(){
        return db;
    }



    private void initImageLoad(){
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .diskCacheSize(1024*1024*60)
                .memoryCacheSize(1024*1024*60)
                .diskCacheFileCount(100)
                // 添加你的配置需求
                .build();
        ImageLoader.getInstance().init(configuration);
    }

    private void setupDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = helper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    private void addTable(){

    }
}
