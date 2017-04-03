package ru.hh.resumebuilderbot.question.generator;

import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.Question;

import java.util.ArrayDeque;
import java.util.Queue;

public class QuestionGeneratorsQueue {
    private Queue<QuestionGenerator> generators = new ArrayDeque<>();

    public void add(QuestionGenerator element) {
        generators.add(element);
    }

    public Queue<Question> generateQuestions(ChatId chatId) {
        Queue<Question> result = new ArrayDeque<>();
        generators.forEach((x) -> result.add(x.generateNext(chatId)));
        return result;
    }
}
