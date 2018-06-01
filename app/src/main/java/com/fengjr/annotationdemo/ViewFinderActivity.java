package com.fengjr.annotationdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fengjr.router_annotation.RouteAnnotation;
import com.fengjr.router_api.Router;
import com.fengjr.spi_interface.Display;
import com.fengjr.viewfinder.ViewFinder;
import com.fengjr.viewfinder_annotation.BindView;
import com.fengjr.viewfinder_annotation.OnClick;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Created by zengyong on 2018/5/30
 */
@RouteAnnotation(name = "/viewfinder/activity")
public class ViewFinderActivity extends Activity {


    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.btn1)
    Button btn1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_finder);
        try{
            ViewFinder.inject(this);

            tv.setText("BindView");
            tv1.setText("BindView1");
        }catch (Exception e){
            Log.e("ViewFinderActivity" , e.getMessage());
        }

    }


    @OnClick(R.id.btn)
    public void onBtnClickListener(){
        Toast.makeText(this , "OnClick" , Toast.LENGTH_SHORT).show();
        ServiceLoader<Display> loader = ServiceLoader.load(Display.class);
        Iterator<Display> iterator = loader.iterator();
        StringBuffer sb = new StringBuffer();
        while(iterator.hasNext()){
            sb.append(iterator.next().display()).append("\n");
        }
        tv.setText(sb.toString());
    }

    @OnClick(R.id.btn1)
    public void onBtnClickListener1(){
        Toast.makeText(this , "OnClick1" , Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.btn_toMainActivity)
    public void toMainActivity(){
        Router.getInstance().navigation("/main/activity");
    }

    @OnClick(R.id.btn_toRouterActivity)
    public void toRouterActivity(){
        Router.getInstance().navigation("/router/activity");
    }

    @OnClick(R.id.btn_toInstantRunActivity)
    public void toInstantRunActivity(){
        Router.getInstance().navigation("/instant/run/activity");
    }

}
