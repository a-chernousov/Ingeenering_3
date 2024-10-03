package com.example.factorymethod.resos;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line implements Shape{

    @Override
    public void draw(GraphicsContext gr) {
        gr.setFill(Color.ORANGERED);
//        gr.setStroke(Color.BLACK);
        gr.setLineWidth(5);

        double centerX = 140;
        double centerY = 115;
        double radius = 50;


        double startX = centerX - radius;
        double endX = centerX + radius;

        gr.strokeLine(startX, centerY, endX, centerY);
    }
}
