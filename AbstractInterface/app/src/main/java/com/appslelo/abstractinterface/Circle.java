package com.appslelo.abstractinterface;

import android.util.Log;

public class Circle extends Shape{

    // Attributes of a Circle
    double pi = 3.14;
    int radius;

    // Constructor
    Circle(int radius, String name)
    {
        super(name);
        this.radius = radius;
    }

    @Override
    public double area() {
        return (double)((pi * radius * radius));

    }

    @Override public void draw()
    {
        Log.d("TSF_MSG","Circle has been drawn ");
    }

}
