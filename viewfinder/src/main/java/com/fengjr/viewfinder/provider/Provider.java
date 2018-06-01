package com.fengjr.viewfinder.provider;

import android.content.Context;
import android.view.View;

/**
 * Created by zengyong on 2018/5/30
 */

public interface Provider {


    Context getContext(Object source);

    View findView(Object source , int id);

}
