package com.example.factorymethod.resos.decorator;

import com.example.factorymethod.resos.figure.Shape;
import javafx.scene.canvas.GraphicsContext;

public abstract class ShapeDecorator extends Shape {
    /**
     * Защищенное поле, хранящее ссылку на декорируемую фигуру.
     */
    protected Shape decoratedShape;

    /**
     * Конструктор класса ShapeDecorator.
     * Инициализирует декоратор для фигуры, передавая параметры базовой фигуры в родительский класс.
     *
     * @param decoratedShape фигура, которую нужно декорировать
     */
    public ShapeDecorator(Shape decoratedShape) {
        // Вызов конструктора родительского класса с параметрами декорируемой фигуры
        super(decoratedShape.getX(), decoratedShape.getY(), decoratedShape.getStrokeWidth(), decoratedShape.getStroke(), decoratedShape.getFillColor());
        // Инициализация поля decoratedShape переданной фигурой
        this.decoratedShape = decoratedShape;
    }

    /**
     * Метод для отрисовки декорированной фигуры.
     * Делегирует вызов метода draw декорируемой фигуре.
     *
     * @param gr графический контекст, используемый для отрисовки
     */
    @Override
    public void draw(GraphicsContext gr) {
        decoratedShape.draw(gr); // Отрисовка декорируемой фигуры
    }

    /**
     * Метод для проверки, содержит ли фигура точку с координатами (x, y).
     * Делегирует вызов метода contains декорируемой фигуре.
     *
     * @param x координата X точки
     * @param y координата Y точки
     * @return true, если фигура содержит точку (x, y), иначе false
     */
    @Override
    public boolean contains(double x, double y) {
        return decoratedShape.contains(x, y); // Проверка, содержит ли фигура точку
    }
}