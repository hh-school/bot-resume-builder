package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.question.generator.QuestionGenerator;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.function.Consumer;

public class QuestionGeneratorsQueue {
    private Queue<QuestionGenerator> queue = new ArrayDeque<>();

    public void add(QuestionGenerator element) {
        queue.add(element);
    }

    public void forEach(Consumer<QuestionGenerator> action) {
        queue.forEach(action);
    }
}
