package com.example.factorymethod.resos.decorator;

import com.example.factorymethod.resos.figure.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ColorDecorator extends ShapeDecorator {
    private Color newColor;

    /**
     * Конструктор класса ColorDecorator.
     * Инициализирует декоратор для фигуры с новым цветом.
     *
     * @param decoratedShape фигура, которую нужно декорировать
     * @param newColor новый цвет, который будет применен к фигуре
     */
    public ColorDecorator(Shape decoratedShape, Color newColor) {
        super(decoratedShape); // Вызов конструктора родительского класса
        this.newColor = newColor; // Инициализация нового цвета
    }

    /**
     * Метод для отрисовки декорированной фигуры.
     * Устанавливает новый цвет для обводки и вызывает метод draw у декорируемой фигуры.
     *
     * @param gr графический контекст, используемый для отрисовки
     */
    @Override
    public void draw(GraphicsContext gr) {
        gr.setStroke(newColor); // Установка нового цвета обводки
        decoratedShape.draw(gr); // Отрисовка декорируемой фигуры
    }
}