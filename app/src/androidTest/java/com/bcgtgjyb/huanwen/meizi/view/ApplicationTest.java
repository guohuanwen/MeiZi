package com.bcgtgjyb.huanwen.meizi.view;

import android.app.Application;
import android.content.res.AssetManager;
import android.os.Handler;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.bcgtgjyb.huanwen.meizi.view.presenter.MainPresenter;
import com.bcgtgjyb.huanwen.meizi.view.tools.MyApplication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.HandlerScheduler;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    private final String TAG="ApplicationTest";
    private Handler handler=new Handler();
//    private HttpFuliJson httpFuliJson=new HttpFuliJson()

//    @Override
//    public void setContext(Context context) {
//        super.setContext(context);
//        long endTime = SystemClock.elapsedRealtime() + TimeUnit.SECONDS.toMillis(2);
//        while (null == context.getApplicationContext()) {
//            if (SystemClock.elapsedRealtime() >= endTime) {
//                fail();
//            }
//            SystemClock.sleep(16);
//        }
//    }

    public ApplicationTest() {
        super(Application.class);
//        setContext(MyApplication.getContext());
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                json();
//            }
//        }).start();

//        testRefresh();
        textDB();
    }

    public void textDB(){
        Log.i(TAG, "textDB "+getContext());
//        FuliDB.getInstance(getContext());

    }

    private String readText(String name){
        String re="";
        try {
            AssetManager assetManager =MyApplication.getContext().getAssets();
            InputStream in = assetManager.open(name);
            InputStreamReader inRead = new InputStreamReader(in, "UTF-8");
            BufferedReader buffRead = new BufferedReader(inRead);
            StringBuffer sBuff = new StringBuffer();
            String data = "";
            while ((data = buffRead.readLine()) != null) {
                sBuff.append(data);
            }
            re=sBuff.toString();
        }catch (Exception e){
            Log.i(TAG, "readText "+e.toString());
        }
        return re;
    }



    public void testRefresh(){
        Log.i(TAG, "testRefresh "+getContext()+"   "+getApplication());
        MainPresenter mainPresenter=new MainPresenter(getApplication());
        mainPresenter.fuliUrlObservable()
                .observeOn(HandlerScheduler.from(mainPresenter.backgroundHandler))
                .subscribeOn(HandlerScheduler.from(handler))
                .subscribe(new Subscriber<List>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError ");
                    }

                    @Override
                    public void onNext(List list) {
                        for(int i=0;i<list.size();i++){
                            Log.i(TAG, "onNext "+list.get(i));
                        }
                    }
                });
    }
}