package ru.hh.resumebuilderbot.question.generator;

import ru.hh.resumebuilderbot.question.Question;

import java.util.ArrayDeque;
import java.util.Queue;

public class QuestionGeneratorsQueue {
    private Queue<QuestionGenerator> generators = new ArrayDeque<>();

    public void add(QuestionGenerator element) {
        generators.add(element);
    }

    public Queue<Question> generateQuestions() {
        Queue<Question> result = new ArrayDeque<>();
        generators.forEach((x) -> result.add(x.generate()));
        return result;
    }
}
