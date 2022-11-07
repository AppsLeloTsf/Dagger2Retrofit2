package com.appslelo.abstractinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        {
            // Creating the Object of Rectangle class
            // and using shape class reference.
            Shape rect = new Rectangle(2, 3, "Rectangle");

            Log.d("TSF_MSG","Area of rectangle: "
                    + rect.area());

            rect.moveTo(1, 2);

            System.out.println(" ");

            // Creating the Objects of circle class
            Shape circle = new Circle(2, "Circle");

           Log.d("TSF_MSG","Area of circle: " + circle.area());

            circle.moveTo(2, 4);


            ShapeInterface shapeRect = new RectangleInterface(5, 8);
            Log.d("TSF_MSG","Area of rectangleInterface: "
                    + shapeRect.area());

            ShapeInterface shapeCircle = new CircleInterface(8);
            Log.d("TSF_MSG","Area of circleInterface: "
                    + shapeCircle.area());
        }

    }
}