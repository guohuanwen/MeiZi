package com.bcgtgjyb.huanwen.meizi.view;

import android.app.Application;
import android.content.res.AssetManager;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.bcgtgjyb.huanwen.meizi.view.bean.FuliDetil;
import com.bcgtgjyb.huanwen.meizi.view.bean.FuliJson;
import com.bcgtgjyb.huanwen.meizi.view.db.FuliDB;
import com.bcgtgjyb.huanwen.meizi.view.tools.MyApplication;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    private final String TAG="ApplicationTest";
    public ApplicationTest() {
        super(Application.class);
        new Thread(new Runnable() {
            @Override
            public void run() {
                json();
            }
        }).start();
    }

    private String readText(String name){
        String re="";
        try {
            AssetManager assetManager = MyApplication.getContext().getAssets();
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

    private void json()  {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    HttpRequest httpRequest = new HttpRequest();
//                    String json = httpRequest.httpGet("http://gank.avosapps.com/api/data/福利/10/1");
                    Log.i(TAG, "json  " + readText("json"));
                    Gson gson=new Gson();
                    FuliJson fuliJson=gson.fromJson(readText("json"),FuliJson.class);
                    Log.i(TAG, "run " + ((FuliDetil) fuliJson.getResults().get(1)).getCreatedAt());
                    List<FuliDetil> list=fuliJson.getResults();
                    FuliDB fuliDB=new FuliDB();
                    for(FuliDetil obj:list){
                        fuliDB.addNote(obj);
                    }
                }catch (Exception e){
                    Log.i(TAG, "json " +e.toString());
                }
            }
        }).start();


    }
}