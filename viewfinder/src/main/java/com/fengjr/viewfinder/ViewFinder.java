package com.fengjr.viewfinder;

import android.app.Activity;
import android.view.View;

import com.fengjr.viewfinder.provider.ActivityProvider;
import com.fengjr.viewfinder.provider.Provider;
import com.fengjr.viewfinder.provider.ViewProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zengyong on 2018/5/30
 */
public class ViewFinder {


    private static final ActivityProvider ACTIVITY_PROVIDER = new ActivityProvider();
    private static final ViewProvider VIEW_PROVIDER = new ViewProvider();

    private static final Map<String , Finder> FINDER_MAP = new HashMap<>();

    public static void inject(Activity activity){
        inject(activity , activity , ACTIVITY_PROVIDER);
    }

    public static void inject(View view){
        inject(view , view);
    }

    public static void inject(Object host , View view){
        inject(host , view , VIEW_PROVIDER);
    }

    public static void inject(Object host , Object source , Provider provider){
        String className = host.getClass().getName();
        try{
            Finder finder = FINDER_MAP.get(className);
            if(finder == null){
                Class<?> findClass = Class.forName(className + "$$Finder");
                finder = (Finder) findClass.newInstance();
                FINDER_MAP.put(className , finder);
            }
            finder.inject(host , source , provider);
        }catch (Exception e){
            throw new RuntimeException("Unable to inject for " + className , e);
        }
    }

}
