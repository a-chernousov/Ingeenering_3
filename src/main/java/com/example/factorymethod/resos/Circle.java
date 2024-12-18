package com.example.factorymethod.resos;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle extends Shape {


    private double radius = 50;
    public Circle() {
        super(140, 115, 2, Color.BLACK, Color.RED);
    }
    @Override
    public void resize(double newSize) {
        this.radius = newSize; // Изменяем радиус
    }
    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
    @Override
    public void draw(GraphicsContext gr) {
        gr.setFill(getFillColor()); // Устанавливаем цвет заливки
        gr.setStroke(getStroke()); // Устанавливаем цвет контура
        gr.setLineWidth(getStrokeWidth());

        double centerX = getX();
        double centerY = getY();
        double radius = 50;

        gr.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        gr.strokeOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
    }

    @Override
    public Shape cloneShape() {
        Circle clone = new Circle();
        clone.relocate(getX(), getY());
        clone.setStroke(getStroke());
        clone.setStrokeWidth(getStrokeWidth());
        clone.setFillColor(getFillColor()); // Устанавливаем цвет заливки
        clone.setRadius(getRadius()); // Устанавливаем радиус
        return clone;
    }

    @Override
    public boolean contains(double x, double y) {
        double centerX = getX();
        double centerY = getY();
        double radius = 50;

        return Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY)) <= radius;
    }
}