package com.example.factorymethod;

import com.example.factorymethod.resos.decorator.ColorDecorator;
import com.example.factorymethod.resos.figure.*;
import com.example.factorymethod.resos.memento.MemoSelect;
import com.example.factorymethod.resos.memento.Momento;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
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

    @FXML
    private Slider sizeSlider;

    @FXML
    private Slider strokeSlider;


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

        // Инициализация ComboBox
        shapeSelector.setItems(FXCollections.observableArrayList(
                "Круг", "Треугольник", "Квадрат", "Линия", "Угол"
        ));

        shapeSelector.setValue("Круг");
        colorPicker.setOnAction(event -> changeShapeColor());

        // Инициализация слайдеров
        sizeSlider.valueProperty().addListener((obs, oldVal, newVal) -> changeShapeSize(newVal.doubleValue()));
        strokeSlider.valueProperty().addListener((obs, oldVal, newVal) -> changeShapeStroke(newVal.doubleValue()));
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

            // Обновляем ссылку на выбранную фигуру
            this.selectedShape = shape; // Устанавливаем новую фигуру как выбранную
            this.selectedShape.setDraggable(true); // Устанавливаем флаг, что фигура перетаскиваема
        }
    }


    @FXML
    protected void onUndo() {
        Momento lastMomento = memoSelect.pop();
        if (lastMomento != null) {
            Shape lastShape = lastMomento.getState();

            // Проверяем, существует ли фигура в списке shapes
            if (shapes.contains(lastShape)) {
                // Если фигура существует, удаляем её
                shapes.remove(lastShape);
            }

            redrawCanvas(); // Перерисовываем холст

            // Активируем последний экземпляр фигуры для перемещения
            if (!shapes.isEmpty()) {
                selectedShape = shapes.get(shapes.size() - 1); // Последний элемент в списке
                selectedShape.setDraggable(true); // Устанавливаем флаг, что фигура перетаскиваема
            }
        }
    }

    private Shape createShapeFromSelection(String selectedShape, Color fillColor) {
        Random random = new Random();
        double x = random.nextDouble() * (myCanvas.getWidth() - 100); // случайная координата X
        double y = random.nextDouble() * (myCanvas.getHeight() - 100); // случайная координата Y

        Shape shape = null;
        switch (selectedShape) {
            case "Круг":
                shape = new Circle();
                break;
            case "Треугольник":
                shape = new Triangle();
                break;
            case "Квадрат":
                shape = new Square();
                break;
            case "Линия":
                shape = new Line();
                break;
            case "Угол":
                shape = new Angle();
                break;
            default:
                shape = new DefaultShape();
        }

        shape.relocate(x, y);
        shape.setFillColor(fillColor); // Устанавливаем цвет заливки
        shape.setDraggable(true); // Устанавливаем флаг, что фигура перетаскиваема
        return shape;
    }

    private void handleMousePressed(MouseEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();

        // Проверяем, находится ли мышь над фигурой
        Shape shapeUnderMouse = findShapeAtPosition(mouseX, mouseY);
        if (shapeUnderMouse != null && shapeUnderMouse.isDraggable()) {
            // Если фигура под мышью уже выбрана, то обновляем смещение
            if (shapeUnderMouse == selectedShape) {
                offsetX = mouseX - selectedShape.getX();
                offsetY = mouseY - selectedShape.getY();
            } else {
                // Если фигура под мышью новая, то обновляем selectedShape
                selectedShape = shapeUnderMouse;
                offsetX = mouseX - selectedShape.getX();
                offsetY = mouseY - selectedShape.getY();
            }

            // Сохраняем состояние фигуры
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
        if (selectedShape != null && selectedShape.isDraggable()) {
            double newX = event.getX() - offsetX; // Новое положение X
            double newY = event.getY() - offsetY; // Новое положение Y

            // Проверяем задержку перед созданием копии
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastDrawTime > DRAW_DELAY) {
                // Создаём копию фигуры с текущими свойствами
                Shape clonedShape = selectedShape.cloneShape();

                // Устанавливаем координаты копии, учитывая смещение
                clonedShape.relocate(newX, newY);

                clonedShape.setDraggable(false); // Устанавливаем флаг, что копия не перетаскиваема
                shapes.add(clonedShape); // Добавляем копию в список фигур
                memoSelect.push(new Momento(clonedShape)); // Сохраняем состояние копии

                lastDrawTime = currentTime; // Обновляем время последнего рисования
            }

            // Обновляем координаты выбранной фигуры
            selectedShape.relocate(newX, newY);

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
    public void changeShapeSize(double newSize) {
        if (selectedShape != null) {
            selectedShape.resize(newSize); // Устанавливаем новый размер
            redrawCanvas(); // Перерисовываем холст
        }
    }

    @FXML
    public void changeShapeStroke(double newStrokeWidth) {
        if (selectedShape != null) {
            selectedShape.setStrokeWidth(newStrokeWidth); // Устанавливаем новую толщину контура
            redrawCanvas(); // Перерисовываем холст
        }
    }

}