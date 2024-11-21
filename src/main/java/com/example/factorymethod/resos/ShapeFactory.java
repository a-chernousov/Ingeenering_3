package com.example.factorymethod.resos;

public class ShapeFactory {
    public Shape createShape(int numberOfSides) {
        if (numberOfSides >= 5) {
            return new Polygon(numberOfSides);
        } else if (numberOfSides == 4) {
            return new Square(100, 100, 50, 2, javafx.scene.paint.Color.BLACK);
        } else if (numberOfSides == 3) {
            return new Triangle();
        } else if (numberOfSides == 2) {
            return new Angle();
        } else if (numberOfSides == 1) {
            return new Line();
        } else if (numberOfSides == 0) {
            return new Circle();
        }
        return new DefaultShape();
    }
}