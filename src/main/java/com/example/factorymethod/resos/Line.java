package com.example.factorymethod.resos;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line extends Shape {

    public Line() {
        super(0, 0, 5, Color.BLACK);
    }

    @Override
    public void draw(GraphicsContext gr) {
        gr.setStroke(Color.ORANGERED);
        gr.setLineWidth(5);

        double startX = getX();
        double startY = getY();
        double endX = startX + 100; // Длина линии
        double endY = startY;

        gr.strokeLine(startX, startY, endX, endY);
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