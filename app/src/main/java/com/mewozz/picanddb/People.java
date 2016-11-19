package com.mewozz.picanddb;

import android.graphics.Bitmap;

/**
 * Created by jjzzz on 11/19/2016.
 */

public class People {
    private int id;
    private String name;
    private String path;
    private Bitmap bmp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Bitmap getBmp() {
        return bmp;
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }
}
