package com.bcgtgjyb.huanwen.meizi.view.custom_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.bcgtgjyb.huanwen.meizi.view.presenter.PhototRecyclerPresenter;

/**
 * Created by huanwen on 2015/9/4.
 */
public class PhotoRecycler extends RecyclerView {
    private PhototRecyclerPresenter phototRecyclerPresenter;
    public PhotoRecycler(Context context, AttributeSet attrs) {
        super(context, attrs);
        phototRecyclerPresenter=new PhototRecyclerPresenter();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        phototRecyclerPresenter.setView(this);
    }
}
