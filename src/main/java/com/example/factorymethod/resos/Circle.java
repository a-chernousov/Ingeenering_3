package com.example.factorymethod.resos;
import javafx.scene.paint.Color;

import javafx.scene.canvas.GraphicsContext;

public class Circle implements Shape {
    @Override
    public void draw(GraphicsContext gr) {

        gr.setFill(Color.BLUE);
        gr.setStroke(Color.BLACK);
        gr.setLineWidth(2);

        double centerX = 140; // x координата центра круга
        double centerY = 115; // y координата центра круга
        double radius = 50;   // радиус круга

        gr.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        gr.strokeOval(centerX - radius, centerY - radius, radius * 2, radius * 2);

    }
}