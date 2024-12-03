package com.example.factorymethod.resos;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ShapeFactory {
    private static final Map<Integer, Supplier<Shape>> shapeCreators = new HashMap<>();

    static {
        shapeCreators.put(5, () -> new Polygon(5));
        shapeCreators.put(4, () -> new Square(100, 100, 50, 2, javafx.scene.paint.Color.BLACK));
        shapeCreators.put(3, Triangle::new);
        shapeCreators.put(2, Angle::new);
        shapeCreators.put(1, Line::new);
        shapeCreators.put(0, Circle::new);
    }

    public Shape createShape(int numberOfSides) {
        Supplier<Shape> shapeCreator = shapeCreators.getOrDefault(numberOfSides, DefaultShape::new);
        return shapeCreator.get();
    }
}