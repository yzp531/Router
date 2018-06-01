package com.fengjr.annotationdemo;

import android.app.Application;

import com.fengjr.router_api.Router;

/**
 * Created by zengyong on 2018/5/31
 */

public class App extends Application{


    @Override
    public void onCreate() {
        super.onCreate();
        Router.init(this);
    }


}
