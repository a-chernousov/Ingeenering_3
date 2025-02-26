package com.example.factorymethod.resos.figure;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line extends Shape {
    private double length = 100;

//    public Line() {
//        super(250, 200, 5, Color.BLACK, Color.TEAL);
//    }

    public Line() {
        super();
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    @Override
    public void resize(double newSize) {
        this.length = newSize; // Изменяем длину линии
    }

    @Override
    public void draw(GraphicsContext gr) {
        gr.setStroke(getFillColor()); // Устанавливаем цвет контура
        gr.setLineWidth(getStrokeWidth());

        double startX = getX();
        double startY = getY();
        double endX = startX + length;
        double endY = startY;

        gr.strokeLine(startX, startY, endX, endY);
    }

    @Override
    public Shape cloneShape() {
        Line clone = new Line();
        clone.relocate(getX(), getY());
        clone.setStroke(getFillColor());
        clone.setStrokeWidth(getStrokeWidth());
        clone.setLength(getLength()); // Устанавливаем длину
        return clone;
    }

    @Override
    public boolean contains(double x, double y) {
        // Простая проверка для линии
        double startX = getX();
        double startY = getY();
        double endX = startX + 100;
        double endY = startY;

        return x >= startX && x <= endX && y >= startY - 5 && y <= startY + 5;
    }
}