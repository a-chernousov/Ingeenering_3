package com.example.factorymethod.resos.figure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.scene.paint.Color;
import static org.junit.jupiter.api.Assertions.*;

class LineTest {

    private Line line;

    @BeforeEach
    void setUp() {
        // Инициализация объекта перед каждым тестом
        line = new Line();
        line.setLength(100);
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(2);
        line.relocate(50, 50);
    }

    @Test
    void testGetLength() {
        assertEquals(100, line.getLength(), "Длина линии должна быть 100");
    }

    @Test
    void testSetLength() {
        line.setLength(150);
        assertEquals(150, line.getLength(), "Длина линии должна измениться на 150");
    }

    @Test
    void testResize() {
        line.resize(200);
        assertEquals(200, line.getLength(), "Длина линии должна измениться на 200 после resize");
    }

    @Test
    void testContains() {
        // Точка на линии
        assertTrue(line.contains(75, 50), "Точка (75, 50) должна находиться на линии");

        // Точка за пределами линии
        assertFalse(line.contains(200, 50), "Точка (200, 50) не должна находиться на линии");
    }

    @Test
    void testCloneShape() {
        Line clone = (Line) line.cloneShape();
        assertEquals(line.getLength(), clone.getLength(), "Клонированная линия должна иметь ту же длину");
        assertEquals(line.getStroke(), clone.getStroke(), "Клонированная линия должна иметь тот же цвет обводки");
        assertEquals(line.getStrokeWidth(), clone.getStrokeWidth(), "Клонированная линия должна иметь ту же ширину обводки");
        assertEquals(line.getX(), clone.getX(), "Клонированная линия должна иметь те же координаты X");
        assertEquals(line.getY(), clone.getY(), "Клонированная линия должна иметь те же координаты Y");
    }
}