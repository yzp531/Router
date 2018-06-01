package com.fengjr.annotationdemo.display;

import com.fengjr.spi_interface.Display;

/**
 * Created by zengyong on 2018/5/31
 */

public class AppDisplay implements Display {

    @Override
    public String display() {
        return "this display is in app moudle !";
    }

}
