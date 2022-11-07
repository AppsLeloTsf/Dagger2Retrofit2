package com.appslelo.abstractinterface;

public class RectangleInterface implements ShapeInterface{

    int length, width;

    RectangleInterface(int length, int width)
    {
        this.length = length;
        this.width = width;
    }

    @Override public void draw()
    {
        System.out.println("Rectangle has been drawn ");
    }

    @Override public double area()
    {
        return (double)(length * width);
    }


}
