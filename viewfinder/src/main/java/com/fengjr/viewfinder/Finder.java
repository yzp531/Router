package com.fengjr.viewfinder;

import com.fengjr.viewfinder.provider.Provider;

/**
 * Created by zengyong on 2018/5/30
 */

public interface Finder<T> {

    void inject(T host , Object source , Provider provider);

}
