package com.example.factorymethod.resos;

import javafx.scene.canvas.GraphicsContext;

public class SizeDecorator extends ShapeDecorator {
    private double newSize;

    public SizeDecorator(Shape decoratedShape, double newSize) {
        super(decoratedShape);
        this.newSize = newSize;
    }

    @Override
    public void draw(GraphicsContext gr) {
        decoratedShape.resize(newSize); // Изменяем размер фигуры
        decoratedShape.draw(gr);
    }
}