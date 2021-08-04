package com.theprogrammer.qrcoins;

/**
 * Created by markmamdouh on 7/16/2016.
 */

public class Buttons {

    private String name;
    private int iconID;

    public Buttons(String name, int iconID) {
        super();
        this.name = name;
        this.iconID = iconID;
    }

    public String getName() {
        return name;
    }

    public int getIconID() {
        return iconID;
    }
}
