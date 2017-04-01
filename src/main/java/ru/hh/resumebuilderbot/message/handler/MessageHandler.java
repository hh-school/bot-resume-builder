package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.generator.QuestionGenerator;

import java.util.ArrayDeque;
import java.util.Queue;

public abstract class MessageHandler {
    protected Queue<QuestionGenerator> queue = new ArrayDeque<>();

    public abstract Queue<QuestionGenerator> handle(Answer answer);
}
