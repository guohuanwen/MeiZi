package com.bcgtgjyb.huanwen.meizi.view;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bcgtgjyb.huanwen.meizi.view.adapter.PhotoRecyclerAdapter;
import com.bcgtgjyb.huanwen.meizi.view.presenter.MainPresenter;
import com.bcgtgjyb.huanwen.meizi.view.widget.MySwipeRefreshLayout;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

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

    @AfterViews
    void ininPresenter(){
        if(mainPresenter==null) mainPresenter=new MainPresenter();
        mainPresenter.takeView(this);
    }

    @AfterViews
    void  initMeiZiList(){
        photoRecyclerAdapter=new PhotoRecyclerAdapter(this,new ArrayList<String>());
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));
        recyclerView.setAdapter(photoRecyclerAdapter);
        mainPresenter.initRecycler();
    }

    @AfterViews
    void initSwipRefresh(){
        Log.i(TAG, "initSwipRefresh " + Thread.currentThread().getName());
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
                "Stop Animation (Back icon)",
                "Stop Animation (Home icon)",
                "Start Animation",
                "Change Color",
                "GitHub Page",
                "Share",
                "Rate"
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
                        mDrawerToggle.setAnimateEnabled(false);
                        drawerArrow.setProgress(1f);
                        break;
                    case 1:
                        mDrawerToggle.setAnimateEnabled(false);
                        drawerArrow.setProgress(0f);
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

    private class RefreshListener implements MySwipeRefreshLayout.OnRefreshListener{
        @Override
        public void onRefresh() {
            mainPresenter.refreshUrl();
        }
    }



    @MainThread
    public void setRecyclerViewAdapter(List list){
        Log.i(TAG, "setRecyclerViewAdapter "+Thread.currentThread().getName());
        photoRecyclerAdapter.addView(list);
    }



    @MainThread
    public void closeSwipeRefreshLayout(){
        swipeRefreshLayout.setRefreshing(false);
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
}
