package com.example.factorymethod;

import com.example.factorymethod.resos.Shape;
import com.example.factorymethod.resos.ShapeFactory;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {

    @FXML
    private Canvas myCanvas;

    @FXML
    private TextField TextDraw;

    private GraphicsContext gc;


    @FXML
    public void initialize() {
        myCanvas.setWidth(280);
        myCanvas.setHeight(230);
        myCanvas.setStyle("-fx-border-color: gray; -fx-border-width: 2px;");
        gc = myCanvas.getGraphicsContext2D();
    }

    @FXML
    protected void onClickDraw() {
        String text = TextDraw.getText();
        if (text == null || text.trim().isEmpty() || isNotInteger(text)) {
            TextDraw.setText("NFE");
        } else {
            int numberOfSides = Integer.parseInt(text);
            ShapeFactory shapeFactory = new ShapeFactory();
            Shape shape1 = shapeFactory.createShape(numberOfSides);
            gc.clearRect(0, 0, 250, 485);
            shape1.draw(gc);
        }
    }
    public static boolean isNotInteger(String str) {
        try {
            int num = Integer.parseInt(str);
            return false;
        } catch (NumberFormatException e) {
            //NumberFormatException
            return true;
        }
    }
}