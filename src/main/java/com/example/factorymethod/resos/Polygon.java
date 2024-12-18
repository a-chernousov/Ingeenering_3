package com.example.factorymethod.resos;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Polygon extends Shape {
    private final int count;

    public Polygon(int count) {
        super(125, 125, 2, Color.BLACK, Color.BISQUE);
        this.count = count;
    }

    @Override
    public void draw(GraphicsContext gr) {
        double centerX = getX();
        double centerY = getY();
        double radius = 100;

        drawPolygon(gr, count, centerX, centerY, radius);
    }

    public void drawPolygon(GraphicsContext gr, int numberOfSides, double centerX, double centerY, double radius) {
        gr.setFill(Color.GREEN);

        double[] xPoints = new double[numberOfSides];
        double[] yPoints = new double[numberOfSides];

        // Вычисляем угол между соседними вершинами
        double angle = 2 * Math.PI / numberOfSides;

        // Вычисляем координаты каждой вершины
        for (int i = 0; i < numberOfSides; i++) {
            xPoints[i] = centerX + radius * Math.cos(i * angle);
            yPoints[i] = centerY + radius * Math.sin(i * angle);
        }

        // Создаем путь для многоугольника
        gr.beginPath();
        gr.moveTo(xPoints[0], yPoints[0]);
        for (int i = 1; i < numberOfSides; i++) {
            gr.lineTo(xPoints[i], yPoints[i]);
        }
        gr.closePath();

        // Заполняем многоугольник
        gr.fill();
    }

    @Override
    public boolean contains(double x, double y) {
        // Простая проверка для многоугольника
        double centerX = getX();
        double centerY = getY();
        double radius = 100;

        double angle = 2 * Math.PI / count;
        for (int i = 0; i < count; i++) {
            double x1 = centerX + radius * Math.cos(i * angle);
            double y1 = centerY + radius * Math.sin(i * angle);
            double x2 = centerX + radius * Math.cos((i + 1) * angle);
            double y2 = centerY + radius * Math.sin((i + 1) * angle);

            if (isPointInLine(x, y, x1, y1, x2, y2)) {
                return true;
            }
        }
        return false;
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