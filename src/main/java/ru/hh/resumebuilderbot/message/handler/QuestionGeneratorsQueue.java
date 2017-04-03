package ru.hh.resumebuilderbot.message.handler;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.function.Consumer;

public class QuestionGeneratorsQueue<ElementType> {
    private Queue<ElementType> queue = new ArrayDeque<>();

    public void add(ElementType element) {
        queue.add(element);
    }

    public void forEach(Consumer<ElementType> action) {
        queue.forEach(action);
    }
}
