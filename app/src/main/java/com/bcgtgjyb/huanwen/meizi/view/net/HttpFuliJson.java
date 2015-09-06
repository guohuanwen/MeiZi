package com.bcgtgjyb.huanwen.meizi.view.net;

import android.content.Context;
import android.util.Log;

import com.bcgtgjyb.huanwen.meizi.view.bean.FuliDetil;
import com.bcgtgjyb.huanwen.meizi.view.bean.FuliJson;
import com.bcgtgjyb.huanwen.meizi.view.db.FuliDB;
import com.bcgtgjyb.huanwen.meizi.view.tools.MyApplication;
import com.bcgtgjyb.huanwen.meizi.view.tools.ReadAssetsText;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by huanwen on 2015/9/6.
 */
public class HttpFuliJson {
    private Context context;
    private final String TAG="HttpFuliJson";
    public HttpFuliJson() {
        this.context= MyApplication.getContext();
    }


    public void saveJson()  {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
        try {
//                    HttpRequest httpRequest = new HttpRequest();
//                    String json = httpRequest.httpGet("http://gank.avosapps.com/api/data/福利/10/1");

            String text=new ReadAssetsText().readText("json");
            Gson gson=new Gson();
            FuliJson fuliJson=gson.fromJson(text,FuliJson.class);
            Log.i(TAG, "run " + ((FuliDetil) fuliJson.getResults().get(1)).getCreatedAt());
            FuliDB fuliDB=new FuliDB();
            List<FuliDetil> list=fuliJson.getResults();
            for (FuliDetil obj:list){
                fuliDB.addNote(obj);
            }

        }catch (Exception e){
            Log.i(TAG, "json " +e.toString());
        }
//            }
//        }).start();


    }


}
