package com.example.factorymethod.manager;

import com.example.factorymethod.resos.figure.Shape;
import javafx.scene.input.MouseEvent;

public class MouseEventHandler {
    private ShapeManager shapeManager;
    private ShapeHistoryManager shapeHistoryManager;
    private Shape selectedShape;
    private double offsetX, offsetY;
    private long lastDrawTime = 0;
    private static final long DRAW_DELAY = 30; // Задержка в миллисекундах

    public MouseEventHandler(ShapeManager shapeManager, ShapeHistoryManager shapeHistoryManager) {
        this.shapeManager = shapeManager;
        this.shapeHistoryManager = shapeHistoryManager;
    }

    public void handleMousePressed(MouseEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();
        selectedShape = shapeManager.findShapeAtPosition(mouseX, mouseY);
        if (selectedShape != null && selectedShape.isDraggable()) {
            offsetX = mouseX - selectedShape.getX();
            offsetY = mouseY - selectedShape.getY();
            shapeHistoryManager.saveState(selectedShape);
        }
    }

    public void handleMouseDragged(MouseEvent event) {
        if (selectedShape != null && selectedShape.isDraggable()) {
            double newX = event.getX() - offsetX;
            double newY = event.getY() - offsetY;

            // Проверяем задержку перед созданием копии
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastDrawTime > DRAW_DELAY) {
                // Создаём копию фигуры
                Shape clonedShape = selectedShape.cloneShape();
                clonedShape.relocate(selectedShape.getX(), selectedShape.getY()); // Копия остаётся на старом месте
                clonedShape.setDraggable(false); // Копия не перетаскиваема
                shapeManager.addShape(clonedShape); // Добавляем копию в список фигур
                shapeHistoryManager.saveState(clonedShape); // Сохраняем состояние копии
                lastDrawTime = currentTime; // Обновляем время последнего создания копии
            }

            // Перемещаем выбранную фигуру
            selectedShape.relocate(newX, newY);
            shapeManager.redrawCanvas(); // Перерисовываем холст
        }
    }

    public void handleMouseReleased(MouseEvent event) {
        if (selectedShape != null) {
            // Сбрасываем состояние фигуры после перемещения
            selectedShape.setDraggable(true); // Разблокируем фигуру
            shapeHistoryManager.saveState(selectedShape); // Сохраняем состояние после отпускания мыши
        }
    }

    public Shape getSelectedShape() {
        return selectedShape;
    }
}