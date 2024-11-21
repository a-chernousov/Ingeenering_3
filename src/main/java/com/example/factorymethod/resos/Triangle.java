package com.example.factorymethod.resos;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Triangle extends Shape {
    private double base = 100;
    private double height = 100;

    public Triangle() {
        super(100, 100, 2, Color.BLACK);
    }

    @Override
    public void draw(GraphicsContext gr) {
        gr.setFill(Color.ORANGE);
        gr.setStroke(Color.BLACK);
        gr.setLineWidth(2);

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