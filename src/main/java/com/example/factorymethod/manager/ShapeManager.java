package com.example.factorymethod.manager;

import com.example.factorymethod.resos.figure.Shape;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class ShapeManager {
    private List<Shape> shapes = new ArrayList<>();
    private GraphicsContext gc;

    public ShapeManager(GraphicsContext gc) {
        this.gc = gc;
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public void removeShape(Shape shape) {
        shapes.remove(shape);
    }

    public void redrawCanvas() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        for (Shape shape : shapes) {
            shape.draw(gc);
        }
    }

    public Shape findShapeAtPosition(double x, double y) {
        for (Shape shape : shapes) {
            if (shape.contains(x, y)) {
                return shape;
            }
        }
        return null;
    }
}