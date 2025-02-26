package com.example.factorymethod.manager;

import com.example.factorymethod.resos.figure.Shape;
import com.example.factorymethod.resos.memento.Momento;
import com.example.factorymethod.resos.memento.MemoSelect;

public class ShapeHistoryManager {
    private MemoSelect memoSelect = new MemoSelect();

    public void saveState(Shape shape) {
        memoSelect.push(new Momento(shape));
    }

    public Shape undo() {
        Momento lastMomento = memoSelect.pop();
        if (lastMomento != null) {
            return lastMomento.getState();
        }
        return null;
    }
}