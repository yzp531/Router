package com.fengjr.router_api;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
/**
 * Created by zengyong on 2018/5/31
 */
public class Router {


    private static Router instance;

    private Context mContext;
    private IProvider iProvider;

    private Router(Context context){
        this.mContext = context;
    }

    public static void init(Context context){
        if(instance == null){
            synchronized (Router.class){
                if(instance == null){
                    instance = new Router(context);
                }
            }
        }
    }

    public static Router getInstance(){
        return instance;
    }


    public void navigation(String path){
        if(iProvider == null){
            try {
                iProvider = (IProvider) Class.forName("com.fengjr.router.AnnotationRouter$Finder").newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

        if(iProvider == null){
            return;
        }
        String activityName = iProvider.getActivityName(path);
        if(!TextUtils.isEmpty(activityName)){
            Intent intent = new Intent();
            intent.setClassName(mContext , activityName);
            mContext.startActivity(intent);
        }
    }

}
