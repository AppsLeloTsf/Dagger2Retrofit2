package com.appslelo.abstractinterface;

import android.util.Log;

public class Rectangle extends Shape{

    // Atributes of rectangle
    int length, width;

    // Constructor
    Rectangle(int length, int width, String name)
    {

        // Super keyword refers to current instance itself
        super(name);

        // this keyword refers to current instance itself
        this.length = length;
        this.width = width;
    }

    @Override
    public void draw()
    {
        Log.d("TSF_MSG","Rectangle has been drawn ");
    }

    @Override
    public double area()
    {
        return (double)(length * width);
    }
}
