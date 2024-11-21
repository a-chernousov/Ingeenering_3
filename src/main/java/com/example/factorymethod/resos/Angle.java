package com.example.factorymethod.resos;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Angle extends Shape {

    public Angle() {
        super(25, 25, 10, Color.GRAY);
    }

    @Override
    public void draw(GraphicsContext gr) {
        gr.setStroke(Color.GRAY);
        gr.setLineWidth(10);

        double startX = getX();
        double startY = getY();
        double endX1 = startX + 225;
        double endY1 = startY;
        double endX2 = startX;
        double endY2 = startY + 225;

        gr.strokeLine(startX, startY, endX1, endY1);
        gr.strokeLine(startX, startY, endX2, endY2);
    }

    @Override
    public boolean contains(double x, double y) {
        // Простая проверка для угла
        double startX = getX();
        double startY = getY();
        double endX1 = startX + 225;
        double endY1 = startY;
        double endX2 = startX;
        double endY2 = startY + 225;

        return isPointInLine(x, y, startX, startY, endX1, endY1) || isPointInLine(x, y, startX, startY, endX2, endY2);
    }

    private boolean isPointInLine(double px, double py, double x1, double y1, double x2, double y2) {
        double d1 = dist(px, py, x1, y1);
        double d2 = dist(px, py, x2, y2);
        double lineLen = dist(x1, y1, x2, y2);
        double buffer = 0.1; // Допуск
        return d1 + d2 >= lineLen - buffer && d1 + d2 <= lineLen + buffer;
    }

    private double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
}