package com.example.factorymethod;

import com.example.factorymethod.resos.*;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

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
    private TextField TextDraw;

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
    }

    private int numberOfSides;
    @FXML
    protected void onClickDraw() {
        String text = TextDraw.getText();
        if (text == null || text.trim().isEmpty() || isNotInteger(text)) {
            TextDraw.setText("NFE");
        } else {
            numberOfSides = Integer.parseInt(text);
            ShapeFactory shapeFactory = new ShapeFactory();
            Shape shape1 = shapeFactory.createShape(numberOfSides);
            shapes.add(shape1);
            memoSelect.push(new Momento(shape1)); // Сохраняем состояние после рисования
            gc.clearRect(0, 0, 500, 400);
            for (Shape shape : shapes) {
                shape.draw(gc);
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

    public static boolean isNotInteger(String str) {
        try {
            int num = Integer.parseInt(str);
            return false;
        } catch (NumberFormatException e) {
            // NumberFormatException
            return true;
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
                ShapeFactory shapeFactory = new ShapeFactory();
                Shape newShape = shapeFactory.createShape(numberOfSides);
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