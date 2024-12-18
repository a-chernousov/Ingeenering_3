package com.example.factorymethod.resos;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Square extends Shape {

    public double getSideLength() {
        return sideLength;
    }

    private double sideLength;

    public Square(double x, double y, double sideLength, double strokeWidth, Color strokeColor) {
        super(x, y, strokeWidth, strokeColor, Color.BLUE);
        this.sideLength = sideLength;
    }

    @Override
    public Shape cloneShape() {
        Square clone = new Square(getX(), getY(), getSideLength(), getStrokeWidth(), getStroke());
        clone.setFillColor(getFillColor()); // Устанавливаем цвет заливки
        return clone;
    }

    @Override
    public void resize(double newSize) {
        this.sideLength = newSize; // Изменяем длину стороны
    }
    @Override
    public void draw(GraphicsContext gr) {
        gr.setFill(getFillColor()); // Устанавливаем цвет заливки
        gr.setStroke(getStroke()); // Устанавливаем цвет контура
        gr.setLineWidth(getStrokeWidth());

        double startX = getX();
        double startY = getY();

        gr.fillRect(startX, startY, sideLength, sideLength);
        gr.strokeRect(startX, startY, sideLength, sideLength);
    }

    @Override
    public boolean contains(double x, double y) {
        return x >= getX() && x <= getX() + sideLength &&
                y >= getY() && y <= getY() + sideLength;
    }
}