package com.appslelo.abstractinterface;

import android.util.Log;

abstract class Shape {
    String objectName = " ";
    Shape(String name) {
        this.objectName = name;
    }
    public void moveTo(int x, int y)
    {
        Log.d("TSF_MSG",this.objectName + " "
                + "has been moved to"
                + " x = " + x + " and y = " + y);
    }
    abstract public double area();
    abstract public void draw();
}
