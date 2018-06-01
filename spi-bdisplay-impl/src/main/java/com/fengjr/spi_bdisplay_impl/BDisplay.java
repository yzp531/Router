package com.fengjr.spi_bdisplay_impl;

import com.fengjr.spi_interface.Display;

public class BDisplay implements Display{


    @Override
    public String display() {
        return "this is display in mouble bdisplay !";
    }


}
