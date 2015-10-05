package com.bcgtgjyb.huanwen.meizi.view.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.bcgtgjyb.huanwen.meizi.view.MainActivity_;
import com.bcgtgjyb.huanwen.meizi.view.R;
import com.bcgtgjyb.huanwen.meizi.view.bean.FuliDetil;
import com.bcgtgjyb.huanwen.meizi.view.db.FuliDB;
import com.bcgtgjyb.huanwen.meizi.view.net.HttpFuliJson;
import com.bcgtgjyb.huanwen.meizi.view.net.NetWork;
import com.bcgtgjyb.huanwen.meizi.view.tools.MyApplication;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2015/10/1.
 */
public class MyService extends Service {
    private String TAG="MyService";
    private int lastRefreshTime=0;
    private int nowTime=0;
    //最后一个Url，判断是否需要刷新
    private String endUrl="";
    private HttpFuliJson httpFuliJson;
    private FuliDB fuliDB;
    private String nowUrl="";
    private String dbNewUrl="";
    private Handler handler=new Handler();
    private Runnable runnable;
    private BroadcastReceiver mBroadcastReceiver=new CommandReceiver();





    @Override
    public void onCreate() {
        super.onCreate();
        //初始化
        fuliDB=FuliDB.getInstance(MyApplication.getContext());
        httpFuliJson=new HttpFuliJson(MyApplication.getContext());

        //动态注册广播
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.bcgtgjyb.huanwen.meizi.view.service.MyService");
        registerReceiver(mBroadcastReceiver,intentFilter);

        delay();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);

    }

    //服务绑定使用
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }



    private List myList=null;
    private void refresh(){
        fuliDB.qureyUrlObservable(1)
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List list) {
                        dbNewUrl=(String)list.get(0);
                    }
                });
        Calendar calendar=new GregorianCalendar();
        nowTime=calendar.get(Calendar.DAY_OF_YEAR);

        if(nowTime!=lastRefreshTime){
            httpFuliJson.getHttpFuli(1)
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(new Subscriber<List>() {
                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "onCompleted() returned: " + nowUrl+"  "+dbNewUrl);
                            if(!nowUrl.equals(dbNewUrl)){
                                //保存到数据库
                                httpFuliJson.saveToDB(myList,1);
                                sendNotification();
                            }
                            NetWork.getInstance().setNet(true);
                        }

                        @Override
                        public void onError(Throwable e) {
                           NetWork.getInstance().setNet(false);
                        }

                        @Override
                        public void onNext(List list) {
                            myList=list;
                            FuliDetil fuliDetil=(FuliDetil)list.get(0);
                            nowUrl=fuliDetil.getUrl();
                        }
                    });

        }
    }

    private void delay(){
//        sendNotification();

        runnable=new Runnable() {
            @Override
            public void run() {
                refresh();
                Log.d(TAG, "run() called with: " + "");
                handler.postDelayed(this,1000*60*60*6);
            }
        };
        handler.post(runnable);
    }

    private void sendNotification(){
        NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder build=new NotificationCompat.Builder(MyService.this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("MeiZi")
                .setContentText("一大波妹子来袭");
        //第一次提示消息的时候显示在通知栏上
        build.setTicker("新消息");
        //设置通知集合的数量
//        build.setNumber(12);
        //通知产生时间
        build.setWhen(System.currentTimeMillis());
        //添加通知声音，灯光等
        build.setDefaults(Notification.DEFAULT_VIBRATE);
        //是否正在进行，如下载 则设置为ture
        build.setOngoing(false);
        Intent resultIntent =new Intent(MyService.this, MainActivity_.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,resultIntent,0);
        build.setContentIntent(pendingIntent);
        notificationManager.notify(0,build.build());
    }



    public class MyBinder extends Binder{
        MyService getService(){
            return MyService.this;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy() called with: " + "");
        unregisterReceiver(mBroadcastReceiver);
        if(runnable!=null) {
            handler.removeCallbacks(runnable);
        }
    }

//activity 通信
    private class CommandReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int run=intent.getIntExtra("run",-1);
//            if(run==0){
//                Log.d(TAG, "onReceive() called with: " + "context = [" + context + "], intent = [" + intent + "]");
//                MyService.this.onDestroy();
//            }
        }
    }
}
