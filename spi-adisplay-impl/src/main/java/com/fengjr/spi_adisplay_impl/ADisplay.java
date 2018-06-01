package com.fengjr.spi_adisplay_impl;

import com.fengjr.spi_interface.Display;

public class ADisplay implements Display {

    @Override
    public String display() {
        return "this is display in module adisplay !";
    }

}
