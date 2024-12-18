package com.example.factorymethod.resos;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Triangle extends Shape {
    public double getBase() {
        return base;
    }

    public double getHeight() {
        return height;
    }

    public void setBase(double base) {
        this.base = base;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    private double base = 100;
    private double height = 100;

    public Triangle() {
        super(100, 100, 2, Color.BLACK, Color.GREEN);
    }
    @Override
    public void resize(double newSize) {
        this.base = newSize;
        this.height = newSize;
    }
    @Override
    public Shape cloneShape() {
        Triangle clone = new Triangle();
        clone.relocate(getX(), getY());
        clone.setStroke(getStroke());
        clone.setStrokeWidth(getStrokeWidth());
        clone.setFillColor(getFillColor()); // Устанавливаем цвет заливки
        clone.setBase(getBase()); // Устанавливаем основание
        clone.setHeight(getHeight()); // Устанавливаем высоту
        return clone;
    }
    @Override
    public void draw(GraphicsContext gr) {
        gr.setFill(getFillColor()); // Устанавливаем цвет заливки
        gr.setStroke(getStroke()); // Устанавливаем цвет контура
        gr.setLineWidth(getStrokeWidth());

        double centerX = getX();
        double centerY = getY();

        double[] xPoints = {centerX, centerX + base / 2, centerX - base / 2};
        double[] yPoints = {centerY - height / 2, centerY + height / 2, centerY + height / 2};

        gr.fillPolygon(xPoints, yPoints, 3);
        gr.strokePolygon(xPoints, yPoints, 3);
    }

    @Override
    public boolean contains(double x, double y) {
        double centerX = getX();
        double centerY = getY();

        double[] xPoints = {centerX, centerX + base / 2, centerX - base / 2};
        double[] yPoints = {centerY - height / 2, centerY + height / 2, centerY + height / 2};

        return isPointInTriangle(x, y, xPoints, yPoints);
    }

    private boolean isPointInTriangle(double px, double py, double[] xPoints, double[] yPoints) {
        double d1 = sign(px, py, xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
        double d2 = sign(px, py, xPoints[1], yPoints[1], xPoints[2], yPoints[2]);
        double d3 = sign(px, py, xPoints[2], yPoints[2], xPoints[0], yPoints[0]);

        boolean hasNeg = (d1 < 0) || (d2 < 0) || (d3 < 0);
        boolean hasPos = (d1 > 0) || (d2 > 0) || (d3 > 0);

        return !(hasNeg && hasPos);
    }

    private double sign(double px, double py, double x1, double y1, double x2, double y2) {
        return (px - x2) * (y1 - y2) - (x1 - x2) * (py - y2);
    }
}