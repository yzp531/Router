package com.fengjr.annotationdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.fengjr.router_annotation.RouteAnnotation;

/**
 * Created by zengyong on 2018/6/1
 */
@RouteAnnotation(name = "/instant/run/activity")
public class InstantRunActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instant_run);
    }


}
