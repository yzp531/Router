package com.fengjr.annotationdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.fengjr.apt.annotation.Code;
import com.fengjr.apt.annotation.Print;
import com.fengjr.router_annotation.RouteAnnotation;

@RouteAnnotation(name = "/main/activity")
public class MainActivity extends Activity {


    @Override
    @Print
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process();
            }
        });
    }

    @Code(author = "MainActivity" , date = "2016-05-29")
    private void process(){
        new MainActivityAutogenerate().message();
    }


}
