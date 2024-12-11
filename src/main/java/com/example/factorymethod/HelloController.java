package com.example.factorymethod;

import com.example.factorymethod.resos.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HelloController {

    private Momento temp;
    private Shape selectedShape;
    private double offsetX, offsetY;
    private List<Shape> shapes = new ArrayList<>();
    private MemoSelect memoSelect = new MemoSelect();
    private boolean isDragging = false;
    private boolean isMoving = false;
    private final int COORD_DRAW = 20;

    @FXML
    private Canvas myCanvas;

    @FXML
    private ComboBox<String> shapeSelector;

    private GraphicsContext gc;

    // Переменные для отслеживания перемещения
    private double lastX, lastY;
    private int moveCounter = 0;

    @FXML
    public void initialize() {
        myCanvas.setWidth(500);
        myCanvas.setHeight(400);
        myCanvas.setStyle("-fx-border-color: gray; -fx-border-width: 2px;");
        gc = myCanvas.getGraphicsContext2D();

        // Добавляем обработчики событий мыши
        myCanvas.setOnMousePressed(this::handleMousePressed);
        myCanvas.setOnMouseDragged(this::handleMouseDragged);
        myCanvas.setOnMouseReleased(this::handleMouseReleased);

        // Инициализация ComboBox
        shapeSelector.setItems(FXCollections.observableArrayList(
                "Круг", "Треугольник", "Прямоугольник", "Линия", "Угол"
        ));
    }

    @FXML
    protected void onClickDraw() {
        String selectedShape = shapeSelector.getValue();
        if (selectedShape == null || selectedShape.trim().isEmpty()) {
            System.out.println("Выберите фигуру");
        } else {
            Shape shape = createShapeFromSelection(selectedShape);
            shapes.add(shape);
            memoSelect.push(new Momento(shape)); // Сохраняем состояние после рисования
            gc.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
            for (Shape s : shapes) {
                s.draw(gc);
            }
        }
    }

    @FXML
    protected void onUndo() {
        Momento lastMomento = memoSelect.pop();
        if (lastMomento != null) {
            Shape lastShape = lastMomento.getState();
            shapes.remove(lastShape);
            redrawCanvas();
        }
    }

    private Shape createShapeFromSelection(String selectedShape) {
        Random random = new Random();
        double x = random.nextDouble() * (myCanvas.getWidth() - 100); // случайная координата X
        double y = random.nextDouble() * (myCanvas.getHeight() - 100); // случайная координата Y

        switch (selectedShape) {
            case "Круг":
                Circle circle = new Circle();
                circle.relocate(x, y);
                return circle;
            case "Треугольник":
                Triangle triangle = new Triangle();
                triangle.relocate(x, y);
                return triangle;
            case "Прямоугольник":
                Square square = new Square(x, y, 100, 2, Color.BLACK);
                return square;
            case "Линия":
                Line line = new Line();
                line.relocate(x, y);
                return line;
            case "Угол":
                Angle angle = new Angle();
                angle.relocate(x, y);
                return angle;
            default:
                return new DefaultShape();
        }
    }

    private void handleMousePressed(MouseEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();

        // Проверяем, находится ли мышь над фигурой
        selectedShape = findShapeAtPosition(mouseX, mouseY);
        if (selectedShape != null) {
            offsetX = mouseX - selectedShape.getX();
            offsetY = mouseY - selectedShape.getY();
            // Сохраняем текущее состояние фигуры
            memoSelect.push(new Momento(selectedShape));
            lastX = mouseX;
            lastY = mouseY;
            moveCounter = 0;
            isMoving = true; // Начинаем движение
        }
    }

    private void handleMouseDragged(MouseEvent event) {
        if (selectedShape != null) {
            double newX = event.getX() - offsetX;
            double newY = event.getY() - offsetY;
            selectedShape.relocate(newX, newY);

            // Проверяем, переместилась ли фигура на 10 координат
            double deltaX = Math.abs(newX - lastX);
            double deltaY = Math.abs(newY - lastY);
            if (deltaX >= COORD_DRAW || deltaY >= COORD_DRAW) {
                // Создаем новую фигуру на месте предыдущей
                Shape newShape = createShapeFromSelection(shapeSelector.getValue());
                newShape.setX(lastX - offsetX);
                newShape.setY(lastY - offsetY);
                shapes.add(newShape);
                memoSelect.push(new Momento(newShape)); // Сохраняем состояние после перетаскивания

                // Обновляем последние координаты
                lastX = newX;
                lastY = newY;

                // Перерисовываем канвас
                redrawCanvas();
            }
        }
    }

    private void handleMouseReleased(MouseEvent event) {
        isDragging = false;
        isMoving = false; // Останавливаем движение

        // Сохраняем состояние после отпускания мыши
        if (selectedShape != null) {
            memoSelect.push(new Momento(selectedShape));
        }
    }

    private Shape findShapeAtPosition(double x, double y) {
        for (Shape shape : shapes) {
            if (shape.contains(x, y)) {
                return shape;
            }
        }
        return null;
    }

    private void redrawCanvas() {
        gc.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
        for (Shape shape : shapes) {
            shape.draw(gc);
        }
    }
}