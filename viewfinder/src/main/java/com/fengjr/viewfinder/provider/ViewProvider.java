package com.fengjr.viewfinder.provider;

import android.content.Context;
import android.view.View;

/**
 * Created by zengyong on 2018/5/30
 */

public class ViewProvider implements Provider {

    @Override
    public Context getContext(Object source) {
        return ((View)source).getContext();
    }

    @Override
    public View findView(Object source, int id) {
        return ((View)source).findViewById(id);
    }

}
