package com.example.factorymethod.resos.figure;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ShapeFactory {
    private static final Map<Integer, Supplier<Shape>> shapeCreators = new HashMap<>();
    private static final Map<String, Integer> shapeNameToIdMap = new HashMap<>();

    static {
        // Инициализация фабрики
        shapeCreators.put(5, () -> new Polygon(5));
        shapeCreators.put(4, Square::new);
        shapeCreators.put(3, Triangle::new);
        shapeCreators.put(2, Angle::new);
        shapeCreators.put(1, Line::new);
        shapeCreators.put(0, Circle::new);

        // Маппинг названий фигур на их идентификаторы
        shapeNameToIdMap.put("Круг", 0);
        shapeNameToIdMap.put("Линия", 1);
        shapeNameToIdMap.put("Угол", 2);
        shapeNameToIdMap.put("Треугольник", 3);
        shapeNameToIdMap.put("Квадрат", 4);
        shapeNameToIdMap.put("Многоугольник", 5);
    }

    public Shape createShape(int numberOfSides) {
        Supplier<Shape> shapeCreator = shapeCreators.getOrDefault(numberOfSides, DefaultShape::new);
        return shapeCreator.get();
    }

    public Shape createShapeByName(String shapeName) {
        // Получаем идентификатор фигуры по её названию
        Integer shapeId = shapeNameToIdMap.getOrDefault(shapeName, -1);
        if (shapeId == -1) {
            return new DefaultShape(); // Если фигура не найдена, возвращаем фигуру по умолчанию
        }
        return createShape(shapeId); // Создаём фигуру через фабрику
    }
}