package com.fengjr.viewfinder.provider;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * Created by zengyong on 2018/5/30
 */

public class ActivityProvider implements Provider {


    @Override
    public Context getContext(Object source) {
        return (Context) source;
    }

    @Override
    public View findView(Object source, int id) {
        return ((Activity)source).findViewById(id);
    }


}
