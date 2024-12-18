package com.example.factorymethod.resos;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ColorDecorator extends ShapeDecorator {
    private Color newColor;

    public ColorDecorator(Shape decoratedShape, Color newColor) {
        super(decoratedShape);
        this.newColor = newColor;
    }

    @Override
    public void draw(GraphicsContext gr) {
        gr.setStroke(newColor);
        decoratedShape.draw(gr);
    }
}