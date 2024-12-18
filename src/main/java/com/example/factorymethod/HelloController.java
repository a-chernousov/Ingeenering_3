package com.example.factorymethod;

import com.example.factorymethod.resos.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.control.ColorPicker;
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

    @FXML
    private Button changeColorButton;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Button changeSizeButton;

    @FXML
    private Button changeStrokeButton;

    // Переменные для отслеживания перемещения
    private double lastX, lastY;
    private int moveCounter = 0;

    private long lastDrawTime = 0;
    private static final long DRAW_DELAY = 20; // Задержка в миллисекундах (например, 200 мс)


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
        colorPicker.setOnAction(event -> changeShapeColor());

        // Инициализация кнопок
        changeSizeButton.setOnAction(event -> changeShapeSize());
        changeStrokeButton.setOnAction(event -> changeShapeStroke());
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
            // Получаем цвет из ColorPicker
            Color fillColor = colorPicker.getValue();

            // Создаём фигуру с выбранным цветом
            Shape shape = createShapeFromSelection(selectedShape, fillColor);
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

    private Shape createShapeFromSelection(String selectedShape, Color fillColor) {
        Random random = new Random();
        double x = random.nextDouble() * (myCanvas.getWidth() - 100); // случайная координата X
        double y = random.nextDouble() * (myCanvas.getHeight() - 100); // случайная координата Y

        switch (selectedShape) {
            case "Круг":
                Circle circle = new Circle();
                circle.relocate(x, y);
                circle.setFillColor(fillColor); // Устанавливаем цвет заливки
                return circle;
            case "Треугольник":
                Triangle triangle = new Triangle();
                triangle.relocate(x, y);
                triangle.setFillColor(fillColor); // Устанавливаем цвет заливки
                return triangle;
            case "Прямоугольник":
                Square square = new Square(x, y, 100, 2, Color.BLACK);
                square.setFillColor(fillColor); // Устанавливаем цвет заливки
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
        if (selectedShape != null && selectedShape.isDraggable()) { // Проверяем, можно ли перетаскивать фигуру
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


    @FXML
    public void onChangeColor() {
        if (selectedShape != null) {
            Color newColor = Color.RED; // или любой другой цвет
            selectedShape = new ColorDecorator(selectedShape, newColor);
            redrawCanvas();
        }
    }


    private void handleMouseDragged(MouseEvent event) {
        if (selectedShape != null && selectedShape.isDraggable()) { // Проверяем, можно ли перетаскивать фигуру
            double newX = event.getX() - offsetX;
            double newY = event.getY() - offsetY;

            // Проверяем задержку перед созданием копии
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastDrawTime > DRAW_DELAY) {
                // Создаём копию фигуры с текущими свойствами
                Shape clonedShape = selectedShape.cloneShape();
                clonedShape.relocate(lastX - offsetX, lastY - offsetY); // Устанавливаем координаты копии
                clonedShape.setDraggable(false); // Устанавливаем флаг, что копия не перетаскиваема
                shapes.add(clonedShape); // Добавляем копию в список фигур
                memoSelect.push(new Momento(clonedShape)); // Сохраняем состояние копии

                lastDrawTime = currentTime; // Обновляем время последнего рисования
            }

            // Обновляем координаты выбранной фигуры
            selectedShape.relocate(newX, newY);

            // Обновляем последние координаты
            lastX = newX;
            lastY = newY;

            // Перерисовываем холст
            redrawCanvas();
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
        gc.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight()); // Очищаем холст
        for (Shape shape : shapes) {
            shape.draw(gc); // Перерисовываем каждую фигуру
        }
    }

    @FXML
    public void changeShapeColor() {
        if (selectedShape != null) {
            Color newColor = colorPicker.getValue(); // Получаем выбранный цвет
            selectedShape.setFillColor(newColor); // Устанавливаем новый цвет заливки
            redrawCanvas(); // Перерисовываем холст
        }
    }

    @FXML
    public void changeShapeSize() {
        if (selectedShape != null) {
            double newSize = 150; // Увеличим размер фигуры (можно сделать динамическим)
            selectedShape.resize(newSize); // Сохраняем новый размер в фигуре
            redrawCanvas(); // Перерисовываем холст
        }
    }

    @FXML
    public void changeShapeStroke() {
        if (selectedShape != null) {
            double newStrokeWidth = 5; // Увеличим толщину контура (можно сделать динамическим)
            selectedShape.setStrokeWidth(newStrokeWidth); // Сохраняем новую толщину контура в фигуре
            redrawCanvas(); // Перерисовываем холст
        }
    }


}