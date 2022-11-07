package com.appslelo.abstractinterface;

public class CircleInterface implements ShapeInterface{
    double pi = 3.14;
    int radius;

    // constructor
    CircleInterface(int radius) { this.radius = radius; }

    @Override public void draw()
    {
        System.out.println("Circle has been drawn ");
    }

    @Override public double area()
    {

        return (double)((pi * radius * radius));
    }
}
