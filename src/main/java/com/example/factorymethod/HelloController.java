package com.example.factorymethod;

import com.example.factorymethod.manager.MouseEventHandler;
import com.example.factorymethod.manager.ShapeHistoryManager;
import com.example.factorymethod.manager.ShapeManager;
import com.example.factorymethod.resos.figure.*;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.collections.FXCollections;

public class HelloController {
    private ShapeManager shapeManager;
    private ShapeHistoryManager shapeHistoryManager;
    private MouseEventHandler mouseEventHandler;

    @FXML
    private Canvas myCanvas;

    @FXML
    private ComboBox<String> shapeSelector;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Slider sizeSlider;

    @FXML
    private Slider strokeSlider;

    private ShapeFactory shapeFactory = new ShapeFactory();

    @FXML
    public void initialize() {
        shapeManager = new ShapeManager(myCanvas.getGraphicsContext2D());
        shapeHistoryManager = new ShapeHistoryManager();
        mouseEventHandler = new MouseEventHandler(shapeManager, shapeHistoryManager);

        // Настройка событий мыши
        myCanvas.setOnMousePressed(mouseEventHandler::handleMousePressed);
        myCanvas.setOnMouseDragged(mouseEventHandler::handleMouseDragged);
        myCanvas.setOnMouseReleased(mouseEventHandler::handleMouseReleased);

        // Инициализация ComboBox
        shapeSelector.setItems(FXCollections.observableArrayList(
                "Круг", "Треугольник", "Квадрат", "Линия", "Угол"
        ));
        shapeSelector.setValue("Круг");

        // Настройка слайдеров
        sizeSlider.valueProperty().addListener((obs, oldVal, newVal) -> changeShapeSize(newVal.doubleValue()));
        strokeSlider.valueProperty().addListener((obs, oldVal, newVal) -> changeShapeStroke(newVal.doubleValue()));

        // Настройка ColorPicker
        colorPicker.setOnAction(event -> changeShapeColor());
    }

    @FXML
    protected void onClickDraw() {
        String selectedShapeType = shapeSelector.getValue();
        if (selectedShapeType != null && !selectedShapeType.trim().isEmpty()) {
            Color fillColor = colorPicker.getValue();
            Shape shape = createShapeFromSelection(selectedShapeType, fillColor);
            shapeManager.addShape(shape);
            shapeHistoryManager.saveState(shape);
            shapeManager.redrawCanvas();
        }
    }

    @FXML
    protected void onUndo() {
        Shape lastShape = shapeHistoryManager.undo();
        if (lastShape != null) {
            shapeManager.removeShape(lastShape);
            shapeManager.redrawCanvas();
        }
    }

    private Shape createShapeFromSelection(String selectedShapeType, Color fillColor) {
        double x = Math.random() * (myCanvas.getWidth() - 100);
        double y = Math.random() * (myCanvas.getHeight() - 100);

        // Используем фабрику для создания фигуры
        Shape shape = shapeFactory.createShapeByName(selectedShapeType);

        // Устанавливаем свойства фигуры
        shape.relocate(x, y);
        shape.setFillColor(fillColor);
        shape.setDraggable(true);

        return shape;
    }

    @FXML
    public void changeShapeColor() {
        Shape selectedShape = mouseEventHandler.getSelectedShape();
        if (selectedShape != null) {
            Color newColor = colorPicker.getValue();
            selectedShape.setFillColor(newColor);
            shapeManager.redrawCanvas();
        }
    }

    @FXML
    public void changeShapeSize(double newSize) {
        Shape selectedShape = mouseEventHandler.getSelectedShape();
        if (selectedShape != null) {
            selectedShape.resize(newSize);
            shapeManager.redrawCanvas();
        }
    }

    @FXML
    public void changeShapeStroke(double newStrokeWidth) {
        Shape selectedShape = mouseEventHandler.getSelectedShape();
        if (selectedShape != null) {
            selectedShape.setStrokeWidth(newStrokeWidth);
            shapeManager.redrawCanvas();
        }
    }
}