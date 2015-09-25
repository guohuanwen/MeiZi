package com.bcgtgjyb.huanwen.meizi.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bcgtgjyb.huanwen.meizi.view.R;
import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by huanwen on 2015/9/4.
 */
public class PhotoRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private boolean bottom=false;
    private final String TAG="PhotoRecyclerAdapter";
    private Context context;
    private final LayoutInflater layoutInflater;
    private ImageLoader imageLoader;
    private List list;
    DisplayImageOptions options;
    private AdaptherListener adaptherListener;



    public enum Type{
        Foot,Item
    }

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




    public boolean isBottom() {
        return bottom;
    }

    public void setBottom(boolean bottom) {
        this.bottom = bottom;
    }

    public void setAdaptherListener(AdaptherListener adaptherListener){
        this.adaptherListener=adaptherListener;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==list.size()){
            bottom=true;
            return Type.Foot.ordinal();
        }
        else {
            return Type.Item.ordinal();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==Type.Foot.ordinal()){
            if(list.size()!=0) {
                return new FootHolder(layoutInflater.inflate(R.layout.recycle_foot, parent, false));
            }
        }
        if(viewType==Type.Item.ordinal()) {
            return new PhotoHolder(layoutInflater.inflate(R.layout.meizi_photo, parent, false), this);
        }
        return null;
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

        if(holder instanceof PhotoHolder) {
            final ImageView imageView = ((PhotoHolder) holder).imageView;
            final int count=position;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adaptherListener.photoIntent((String) list.get(count));
                }
            });
//            DiskLruCache diskLruCache=;
            Glide.with(context).load((String) list.get(position))
//                    .fitCenter()
//                    .placeholder(R.drawable.place60)
//                    .crossFade()
                    .into(imageView);

//            imageLoader.displayImage((String) list.get(position), imageView, options);
//            String url=imageLoader.getLoadingUriForView(imageView);
//            imageLoader.loadImage((String) list.get(position),imageView,new SimpleImageLoadingListener() {
//                @Override
//                  public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                    // Do whatever you want with Bitmap
//                    ((ImageView)view).setImageBitmap(loadedImage);
////                    Log.i(TAG, "onLoadingComplete "+imageUri);
//
//                }


//            });
            bottom=false;
        }
        if(holder instanceof FootHolder){
            bottom=true;
        }
    }

    public void deleteView(){

    }



    public void replaceView(List list){
        this.list.clear();
        this.list=list;
        notifyDataSetChanged();
    }

    public void addBottomView(List url){
        for(Object obj:url){
            addBottomView((String)obj);
        }
    }

    public void addBottomView(String url){
        Log.i(TAG, "addBottomView "+url);
        list.add(list.size()-1,url);
        notifyItemChanged(list.size()-1);
    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    public class PhotoHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        public PhotoHolder(final View itemView,PhotoRecyclerAdapter photoRecyclerAdapter) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.imageView);

        }
    }

    private class FootHolder extends RecyclerView.ViewHolder{
        public FootHolder(View itemView) {
            super(itemView);
        }
    }

    public interface AdaptherListener{
        void loading(List list);
        void photoIntent(String photo);
    }
}
