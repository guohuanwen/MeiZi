package com.bcgtgjyb.huanwen.meizi.view.tools;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by huanwen on 2015/9/6.
 */
public class ReadAssetsText {
    private Context context;
    private final String TAG="ReadAssetsText";
//    private MyApplication myApplication=MyApplication.getInstence();
    public ReadAssetsText() {
        this.context=MyApplication.getContext();
    }

    public String readText(String name){
        String re="";
        try {
            AssetManager assetManager = context.getAssets();
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
            Log.i(TAG, "readText " + e.toString());
        }
        return re;
    }

}
