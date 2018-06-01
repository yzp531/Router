package com.fengjr.viewfinder_compiler;

import com.squareup.javapoet.ClassName;

/**
 * Created by zengyong on 2018/5/30
 */

public class TypeUtil {


    public static final ClassName ANDROID_VIEW = ClassName.get("android.view" , "View");
    public static final ClassName ANDROID_ON_CLICK_LISTENER = ClassName.get("android.view" , "View" , "OnClickListener");
    public static final ClassName FINDER = ClassName.get("com.fengjr.viewfinder" , "Finder");
    public static final ClassName PROVIDER = ClassName.get("com.fengjr.viewfinder.provider" , "Provider");

}
