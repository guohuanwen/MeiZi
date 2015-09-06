package com.bcgtgjyb.huanwen.meizi.view.tools;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by huanwen on 2015/9/6.
 */
public class MyTime {
    private final String TAG="MyTime";

    //2015-09-02T03:57:44.180Z转换为时间点1441137464180
    public long translateTime(String time){
        long tTime=0;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date date = format.parse(time);
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(date);
            tTime=rightNow.getTimeInMillis();
        }catch (Exception e){
            Log.i(TAG, "translateTime "+e.toString());
        }
        return tTime;
    }
}
