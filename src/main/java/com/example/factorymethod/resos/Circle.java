package com.example.factorymethod.resos;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle extends Shape {

    public Circle() {
        super(140, 115, 2, Color.BLACK);
    }

    @Override
    public void draw(GraphicsContext gr) {
        gr.setFill(Color.BLUE);
        gr.setStroke(Color.BLACK);
        gr.setLineWidth(2);

        double centerX = getX();
        double centerY = getY();
        double radius = 50;

        gr.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        gr.strokeOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
    }

    @Override
    public boolean contains(double x, double y) {
        double centerX = getX();
        double centerY = getY();
        double radius = 50;

        return Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY)) <= radius;
    }
}