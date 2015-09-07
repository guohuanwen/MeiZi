package com.bcgtgjyb.huanwen.meizi.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bcgtgjyb.huanwen.meizi.view.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

/**
 * Created by huanwen on 2015/9/4.
 */
public class PhotoRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final String TAG="PhotoRecyclerAdapter";
    private Context context;
    private final LayoutInflater layoutInflater;
    private ImageLoader imageLoader;
    private List list;
    DisplayImageOptions options;
    public PhotoRecyclerAdapter(Context context,List list) {
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
        this.list=list;
         options=new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        imageLoader=ImageLoader.getInstance();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoHolder(layoutInflater.inflate(R.layout.meizi_photo,parent,false),this);
    }

//    "http://site.com/image.png" // from Web
//            "file:///mnt/sdcard/image.png" // from SD card
//            "file:///mnt/sdcard/video.mp4" // from SD card (video thumbnail)
//            "content://media/external/images/media/13" // from content provider
//            "content://media/external/video/media/13" // from content provider (video thumbnail)
//            "assets://image.png" // from assets
//            "drawable://" + R.drawable.img // from drawables (non-9patch images)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ImageView imageView=((PhotoHolder) holder).imageView;
        Log.i(TAG, "onBindViewHolder " + (String) list.get(position));
        imageLoader.displayImage((String) list.get(position), imageView,options);
        imageLoader.loadImage((String) list.get(position), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                // Do whatever you want with Bitmap
//                imageView.setImageBitmap(loadedImage);

            }
        });
    }

    public void deleteView(){

    }

    public void addView(List url){
        list.clear();
        list=url;
        notifyItemInserted(0);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PhotoHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        public PhotoHolder(View itemView,PhotoRecyclerAdapter photoRecyclerAdapter) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.imageView);
        }
    }
}
