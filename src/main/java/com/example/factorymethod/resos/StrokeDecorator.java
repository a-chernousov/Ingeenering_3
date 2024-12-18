package com.example.factorymethod.resos;

import javafx.scene.canvas.GraphicsContext;

public class StrokeDecorator extends ShapeDecorator {
    private double newStrokeWidth;

    public StrokeDecorator(Shape decoratedShape, double newStrokeWidth) {
        super(decoratedShape);
        this.newStrokeWidth = newStrokeWidth;
    }

    @Override
    public void draw(GraphicsContext gr) {
        gr.setLineWidth(newStrokeWidth);
        decoratedShape.draw(gr);
    }
}