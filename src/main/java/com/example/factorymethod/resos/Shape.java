package com.example.factorymethod.resos;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Shape {

    private double x;
    private double y;
    private double strokeWidth;
    private Color stroke;

    public Shape(double x, double y, double strokeWidth, Color strokeColor) {
        this.x = x;
        this.y = y;
        this.strokeWidth = strokeWidth;
        this.stroke = strokeColor;
    }

    public void draw(GraphicsContext gr) {
        // Базовая реализация, переопределяется в подклассах
    }

    public void relocate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getStrokeWidth() {
        return strokeWidth;
    }

    public Color getStroke() {
        return stroke;
    }

    public void setStrokeWidth(double width) {
        this.strokeWidth = width;
    }

    public void setStroke(Color color) {
        this.stroke = color;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean contains(double x, double y) {
        // Базовая реализация, переопределяется в подклассах
        return false;
    }
}