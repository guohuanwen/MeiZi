package com.bcgtgjyb.huanwen.meizi.view;

import android.app.Activity;
import android.content.Intent;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by huanwen on 2015/9/17.
 */

@EActivity(R.layout.photo_activity)
public class PhotoActivity extends Activity{
    @ViewById
    PhotoView photoView;
    private ImageLoader imageLoader=ImageLoader.getInstance();

    @AfterViews
    void inti(){
        photoView.enable();
        Intent intent=getIntent();
        if(intent!=null){
            String url=intent.getStringExtra("url");
            Glide.with(this).load(url).into(photoView);
//            imageLoader.loadImage(url,new SimpleImageLoadingListener(){
//                @Override
//                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                    photoView.setImageBitmap(loadedImage);
//                }
//            });
        }
    }


}
