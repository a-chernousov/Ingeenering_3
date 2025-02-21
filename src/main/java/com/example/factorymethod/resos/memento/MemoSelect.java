package com.example.factorymethod.resos.memento;

import java.util.Stack;

public class MemoSelect {

    private Stack<Momento> mementoStack = new Stack<>();

    public void push(Momento state) {
        mementoStack.push(state);
    }

    public Momento pop() {
        if (!mementoStack.isEmpty()) {
            return mementoStack.pop();
        }
        return null;
    }
}