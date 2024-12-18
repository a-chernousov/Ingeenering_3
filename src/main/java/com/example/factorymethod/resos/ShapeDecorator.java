package com.example.factorymethod.resos;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class ShapeDecorator extends Shape {
    protected Shape decoratedShape;

    public ShapeDecorator(Shape decoratedShape) {
        super(decoratedShape.getX(), decoratedShape.getY(), decoratedShape.getStrokeWidth(), decoratedShape.getStroke(), decoratedShape.getFillColor());
        this.decoratedShape = decoratedShape;
    }

    @Override
    public void draw(GraphicsContext gr) {
        decoratedShape.draw(gr);
    }

    @Override
    public boolean contains(double x, double y) {
        return decoratedShape.contains(x, y);
    }
}