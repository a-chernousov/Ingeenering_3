package com.example.factorymethod.resos;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Triangle implements Shape{
    private double base = 100;
    private double height = 100;
    @Override
    public void draw(GraphicsContext gr) {
        gr.setFill(Color.ORANGE);
        gr.setStroke(Color.BLACK);
        gr.setLineWidth(2);

        double centerX = 100; // x координата центра треугольника
        double centerY = 100; // y координата центра треугольника

        double[] xPoints = {centerX, centerX + base / 2, centerX - base / 2};
        double[] yPoints = {centerY - height / 2, centerY + height / 2, centerY + height / 2};

        gr.fillPolygon(xPoints, yPoints, 3);
        gr.strokePolygon(xPoints, yPoints, 3);
    }
}
