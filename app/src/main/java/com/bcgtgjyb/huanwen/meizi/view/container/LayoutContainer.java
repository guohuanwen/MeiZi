package com.bcgtgjyb.huanwen.meizi.view.container;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.bcgtgjyb.huanwen.meizi.view.container.Container;

/**
 * Created by huanwen on 2015/9/4.
 */
public class LayoutContainer extends LinearLayout implements Container{

    public LayoutContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void showItem() {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }


}
