package com.bcgtgjyb.huanwen.meizi.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bcgtgjyb.huanwen.meizi.view.R;
import com.bcgtgjyb.huanwen.meizi.view.tools.MyApplication;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.List;

/**
 * Created by huanwen on 2015/9/4.
 */
public class PhotoRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private boolean bottom=false;
    private final String TAG="PhotoRecyclerAdapter";
    private Context context;
    private final LayoutInflater layoutInflater;
//    private ImageLoader imageLoader;
    private List list;
//    DisplayImageOptions options;
    private AdaptherListener adaptherListener;
//    private ImageView imageView;



    public enum Type{
        Foot,Item
    }

    public PhotoRecyclerAdapter(Context context,List list) {
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
        this.list=list;
//         options=new DisplayImageOptions.Builder()
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .build();
//        imageLoader=ImageLoader.getInstance();
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

    private int nowPosition=0;
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {


        if(holder instanceof PhotoHolder) {
            nowPosition=position;
//            imageView = ((PhotoHolder) holder).imageView;
//            ((PhotoHolder) holder).imageView.setImageResource(R.drawable.place60);
//            final int count=position;
//            ((PhotoHolder) holder).imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });
//            DiskLruCache diskLruCache=;
            if(null!=((PhotoHolder) holder).imageView.getTag())
            if(!((PhotoHolder) holder).imageView.getTag().equals((String)list.get(position))) ((PhotoHolder) holder).imageView.setImageResource(R.drawable.place60);
            Glide.with(MyApplication.getContext()).load((String) list.get(position))
//                    .centerCrop()
//                    .fitCenter()
                    .placeholder(R.drawable.place60)
                    .crossFade()
//                    .into(((PhotoHolder) holder).imageView)
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            int h = ((PhotoHolder) holder).card.getWidth();
                            Bitmap bitmap = drawableToBitmap(resource, h);
                            ((PhotoHolder) holder).imageView.setImageBitmap(bitmap);
                            ((PhotoHolder) holder).imageView.setTag(R.id.image_tag, (String) list.get(nowPosition));
//                            Log.d(TAG, "onResourceReady() returned: " + resource.getIntrinsicWidth()+"   "+bitmap.getWidth());
                        }
                    })
//                    .getSize(new SizeReadyCallback() {
//                        @Override
//                        public void onSizeReady(int width, int height) {
//                            if (!((PhotoHolder) holder).card.isShown())
//                                ((PhotoHolder) holder).card.setVisibility(View.VISIBLE);
//                        }
//                    })
            ;

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

    public Bitmap drawableToBitmap(Drawable drawable,int wodth){
        int w=drawable.getIntrinsicWidth();
        int h=drawable.getIntrinsicHeight();

        //取drawable的颜色格式
        Bitmap.Config config=drawable.getOpacity()!= PixelFormat.OPAQUE?Bitmap.Config.ARGB_8888:Bitmap.Config.RGB_565;
        //建立对应的bitmap
        Bitmap bitmap=Bitmap.createBitmap(w,h,config);
        //建立对应的画布
        Canvas canvas=new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        //吧drawable内容画到画布上
        drawable.draw(canvas);

        //缩放部分
        Matrix matrix=new Matrix();
        //计算缩放比例
        float sx=(float)wodth/w;
        //设置缩放比例
        matrix.postScale(sx,sx);
        Bitmap newBit=Bitmap.createBitmap(bitmap,0,0,w,h,matrix,true);
        return  newBit;
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

    private void prestrainLoad(String url){
        Glide.with(MyApplication.getContext()).load(url).downloadOnly(0,0);
    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    public class PhotoHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imageView;
        private View card;
        public PhotoHolder(final View itemView,PhotoRecyclerAdapter photoRecyclerAdapter) {
            super(itemView);
            card=itemView;
            card.setOnClickListener(this);
            imageView=(ImageView)itemView.findViewById(R.id.imageView);


        }

        @Override
        public void onClick(View v) {
            adaptherListener.photoIntent((String) list.get(getPosition()));
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
