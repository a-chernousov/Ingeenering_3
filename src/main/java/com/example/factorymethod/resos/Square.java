package com.example.factorymethod.resos;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Square implements Shape{

    @Override
    public void draw(GraphicsContext gr) {
        gr.setFill(Color.RED);
        gr.setStroke(Color.BLACK);
        gr.setLineWidth(5);

        double centerX = 140;
        double centerY = 115;
        double radius = 50;

        double startX = centerX - radius;
        double startY = centerY - radius;

        gr.strokeRect(startX, startY, 2 * radius, 2 * radius);

    }
}
