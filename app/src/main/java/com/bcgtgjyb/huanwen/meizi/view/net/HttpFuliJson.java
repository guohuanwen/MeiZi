package com.bcgtgjyb.huanwen.meizi.view.net;

import android.content.Context;
import android.util.Log;

import com.bcgtgjyb.huanwen.meizi.view.bean.FuliDetil;
import com.bcgtgjyb.huanwen.meizi.view.bean.FuliJson;
import com.bcgtgjyb.huanwen.meizi.view.db.FuliDB;
import com.bcgtgjyb.huanwen.meizi.view.tools.MyApplication;
import com.bcgtgjyb.huanwen.meizi.view.tools.MyTime;
import com.bcgtgjyb.huanwen.meizi.view.tools.ReadAssetsText;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by huanwen on 2015/9/6.
 */
public class HttpFuliJson {
    private Context context;
    private final String TAG="HttpFuliJson";
    private int fuliCount=1;
    private final int fuliNumber=20;
    public HttpFuliJson() {
        this.context= MyApplication.getContext();
    }

    public long getFuliNumber(){
        return this.fuliNumber;
    }


    public void saveJson()  {
        try {
            HttpRequest httpRequest = new HttpRequest();
            String json = httpRequest.httpGet("http://gank.avosapps.com/api/data/福利/"+fuliNumber+"/"+fuliCount);

            String text=new ReadAssetsText().readText("json1");
            Log.i(TAG, "run "+text);
            Gson gson=new Gson();
            FuliJson fuliJson=gson.fromJson(text,FuliJson.class);
//            Log.i(TAG, "run "+fuliJson.toString());
            Log.i(TAG, "run " + ((FuliDetil) fuliJson.getResults().get(1)).getCreatedAt());
            FuliDB fuliDB=FuliDB.getInstence();
            List<FuliDetil> list=fuliJson.getResults();
//            fuliDB.deleteNote();
            long start=fuliDB.getNoteCount();
            MyTime myTime=new MyTime();
            for(int i=0;i<list.size();i++){
                FuliDetil fuliDetil=(FuliDetil)list.get(i);
                if((i+1)<list.size()){
                    Long id=myTime.translateTime(((FuliDetil)list.get(i + 1)).getPublishedAt());
                    fuliDB.addNote(fuliDetil, id);
                }else {
                    fuliDB.addNote(fuliDetil,0);
                }
            }

            long end=fuliDB.getNoteCount();
            if((end-start)<fuliNumber){
            }else{
                fuliCount++;
            }

        }catch (Exception e){
            Log.i(TAG, "json " +e.toString());
        }




    }


}
