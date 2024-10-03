package com.example.factorymethod.resos;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Polygon implements Shape{
    private final int count;
    Polygon(int count){
        this.count = count;
    }
    @Override
    public void draw(GraphicsContext gr) {
        double centerX = 125;
        double centerY = 125;
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

    public String toString(){
        System.out.println("ПРИВЕТBH!");
        return null;
    }
}
