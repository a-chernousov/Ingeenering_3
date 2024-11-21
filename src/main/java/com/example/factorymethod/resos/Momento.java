package com.example.factorymethod.resos;

import javafx.scene.paint.Color;

public class Momento {

    private Shape shape;
    private double width;
    private Color color;

    public Momento(Shape state) {
        this.shape = state;
        width = shape.getStrokeWidth();
        color = shape.getStroke();
    }

    public Shape getState() {
        shape.setStrokeWidth(width);
        shape.setStroke(color);
        return shape;
    }

    public Shape initState() {
        shape.setStrokeWidth(2);
        shape.setStroke(Color.RED);
        return shape;
    }
}