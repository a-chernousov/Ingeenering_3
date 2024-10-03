package com.example.factorymethod.resos;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Angle implements Shape {
    @Override
    public void draw(GraphicsContext gr) {
        gr.setStroke(Color.GRAY);
        gr.setLineWidth(10);
        gr.strokePolygon(new double[]{25, 250},
                new double[]{25,25},2
        );
        gr.strokePolygon(new double[]{30, 30},
                new double[]{25,250},2
        );
    }
}