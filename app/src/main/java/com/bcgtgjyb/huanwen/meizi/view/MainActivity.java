package com.bcgtgjyb.huanwen.meizi.view;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.bcgtgjyb.huanwen.meizi.view.adapter.PhotoRecyclerAdapter;
import com.bcgtgjyb.huanwen.meizi.view.presenter.MainPresenter;
import com.bcgtgjyb.huanwen.meizi.view.service.MyService;
import com.bcgtgjyb.huanwen.meizi.view.tools.MyApplication;
import com.bcgtgjyb.huanwen.meizi.view.widget.MySwipeRefreshLayout;
import com.bumptech.glide.Glide;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.functions.Action1;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

    @ViewById
    MySwipeRefreshLayout swipeRefreshLayout;
    @ViewById
    DrawerLayout mDrawerLayout;
    @ViewById
    ListView mDrawerList;
    @ViewById
    RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerArrowDrawable drawerArrow;
    private boolean drawerArrowColor;
    private final String TAG="MainActivity";
    private PhotoRecyclerAdapter photoRecyclerAdapter;
    private MainPresenter mainPresenter;
    private Context context;
    private List url;
    private boolean isMoreLoad=false;


    public boolean isMoreLoad() {
        return isMoreLoad;
    }

    public void setIsMoreLoad(boolean isMoreLoad) {
        this.isMoreLoad = isMoreLoad;
    }


    @AfterViews
    void startService(){
        Intent intent=new Intent(MainActivity.this, MyService.class);
        startService(intent);
    }
    @AfterViews
    void init(){
        context=this;

    }

    @AfterViews
    void ininPresenter(){
        if(mainPresenter==null) mainPresenter=new MainPresenter(this);
        mainPresenter.takeView(this);
        mainPresenter.init();
    }

    @AfterViews
    void  initMeiZiList(){
        url=new ArrayList();
        url.add("assets://default.jpg");
        photoRecyclerAdapter=new PhotoRecyclerAdapter(this,url);
        photoRecyclerAdapter.setAdaptherListener(new PhotoRecyclerAdapter.AdaptherListener(){
            @Override
            public void loading(List list) {

            }

            @Override
            public void photoIntent(String photo) {
                Intent intent=new Intent(MainActivity.this,PhotoActivity_.class);
                intent.putExtra("url",photo);
                startActivity(intent);
            }
        });


        final StaggeredGridLayoutManager gridLayoutManager=new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(photoRecyclerAdapter);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                Log.i(TAG, "onScrolled " + isMoreLoad);
                if (!isMoreLoad) {
//                    Log.i(TAG, "onScrolled " + dx + "  " + dy);
                    int[] visibleItems = gridLayoutManager.findLastVisibleItemPositions(null);
                    int lastItem = Math.max(visibleItems[0], visibleItems[1]);
                    if (dy > 0 && lastItem > photoRecyclerAdapter.getItemCount() - 5) {
                        isMoreLoad = true;
                        mainPresenter.refreshMore();
                        Log.i(TAG, "onScrolled " + "到底");
                    }

                }

//                if (((PhotoRecyclerAdapter) recyclerView.getAdapter()).isBottom()) {
//                    Log.i(TAG, "onScrolled ");
//                    //加载更多
//                    if(!isMore) {
//                        Log.i(TAG, "onScrolled "+isMore);
//                        isMore=true;
//                        mainPresenter.refreshMore();
//                    }
////                    ((PhotoRecyclerAdapter) recyclerView.getAdapter()).setBottom(false);
//                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                Log.i(TAG, "onScrollStateChanged " + newState);
                if(newState==0){
                    Glide.with(MyApplication.getContext()).resumeRequests();
                }else{
                    Glide.with(MyApplication.getContext()).pauseRequests();
                }
            }
        });

    }

    @AfterViews
    void initSwipRefresh(){
//        Log.i(TAG, "initSwipRefresh " + Thread.currentThread().getName());
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.actionbar_color));
        swipeRefreshLayout.setOnRefreshListener(new RefreshListener());
    }

    @AfterViews
    void initDrawer() {
        ActionBar ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);
        drawerArrow = new DrawerArrowDrawable(this) {
            @Override
            public boolean isLayoutRtl() {
                return false;
            }
        };
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                drawerArrow, R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        String[] values = new String[]{
                "清除缓存","停止后台服务"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        Glide.get(getContext()).clearDiskCache();
                        Glide.get(getContext()).clearMemory();
                        mDrawerToggle.setAnimateEnabled(false);
                        Toast.makeText(MainActivity.this,"缓存已清理",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        stopService();
                        Toast.makeText(MainActivity.this,"刷新服务已停止",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        mDrawerToggle.setAnimateEnabled(true);
                        mDrawerToggle.syncState();
                        break;
                    case 3:
                        if (drawerArrowColor) {
                            drawerArrowColor = false;
                            drawerArrow.setColor(R.color.ldrawer_color);
                        } else {
                            drawerArrowColor = true;
                            drawerArrow.setColor(R.color.drawer_arrow_second_color);
                        }
                        mDrawerToggle.syncState();
                        break;
                    case 4:
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/IkiMuhendis/LDrawer"));
                        startActivity(browserIntent);
                        break;
                    case 5:
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("text/plain");
                        share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        share.putExtra(Intent.EXTRA_SUBJECT,
                                getString(R.string.app_name));
                        share.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_description) + "\n" +
                                "GitHub Page :  https://github.com/IkiMuhendis/LDrawer\n" +
                                "Sample App : https://play.google.com/store/apps/details?id=" +
                                getPackageName());
                        startActivity(Intent.createChooser(share,
                                getString(R.string.app_name)));
                        break;
                    case 6:
                        String appUrl = "https://play.google.com/store/apps/details?id=" + getPackageName();
                        Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(appUrl));
                        startActivity(rateIntent);
                        break;

                }

            }
        });
//        json();
//

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "dispatchTouchEvent ");
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Glide.with(this).pauseRequests();
                break;
            case MotionEvent.ACTION_UP:
                Glide.with(this).resumeRequests();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent ");
        return super.onTouchEvent(event);
    }

    public Context getContext(){
        return this;
    }



    public long getUrlCount() {
        return photoRecyclerAdapter.getItemCount();
    }


    //下拉刷新监听
    private class RefreshListener implements MySwipeRefreshLayout.OnRefreshListener{
        @Override
        public void onRefresh() {
            mainPresenter.pullToRefresh();
        }
    }


    //替换adapter中的url list
    public Subscriber<List> replaceRecycleSubscriber=new Subscriber<List>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Log.i(TAG, "onError "+e.toString());
        }

        @Override
        public void onNext(List list) {
            photoRecyclerAdapter.replaceView(list);
        }
    };

    //向adapter添加item
    public Subscriber<String> addRecyclerItemSubscriber=new Subscriber<String>() {
        @Override
        public void onCompleted() {
            isMoreLoad=false;
        }

        @Override
        public void onError(Throwable e) {
            Log.i(TAG, "onError "+e.toString());
        }

        @Override
        public void onNext(String s) {
            Log.i(TAG, "onNext " + s);
            photoRecyclerAdapter.addBottomView(s);
        }
    };

    //向Adapter添加List
   public Subscriber<List> addRecyclerListSubscriber=new Subscriber<List>(){
       @Override
       public void onCompleted() {
           isMoreLoad=false;
           Log.i(TAG, "onCompleted "+"addRecyclerListSubscriber");
       }

       @Override
       public void onError(Throwable e) {
           Log.i(TAG, "onError "+e.toString());
//           Toast.makeText(context, "加载失败", Toast.LENGTH_SHORT);
       }

       @Override
       public void onNext(List list) {
           photoRecyclerAdapter.addBottomView(list);

       }
   };


    public void setBottom(boolean param){
        photoRecyclerAdapter.setBottom(false);
    }
    public Action1 closeSwipRefershLayout =new Action1(){
        @Override
        public void call(Object o) {
            swipeRefreshLayout.setRefreshing(false);
        }
    };

    public Action1<String> mainToast=new Action1<String>() {
        @Override
        public void call(String s) {
            Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
        }
    };



    private void setRecyclerViewAdapter(List list){
        Log.i(TAG, "setRecyclerViewAdapter " + Thread.currentThread().getName());
        photoRecyclerAdapter.addBottomView(list);
    }




    private void closeSwipeRefreshLayout(){
        swipeRefreshLayout.setRefreshing(false);
    }


    MyService.MyBinder binder=null;
    public class MyServiceConn implements ServiceConnection{
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder=(MyService.MyBinder)service;
        }

        //服务被关闭回收
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }


    private void stopService(){
        Intent intent=new Intent(MainActivity.this,MyService.class);
        stopService(intent);
    }







    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                mDrawerLayout.closeDrawer(mDrawerList);
            } else {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if(binder!=null){
//            unbindService();
//        }
    }
}
