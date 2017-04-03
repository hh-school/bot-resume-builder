package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;

public abstract class MessageHandler {
    protected QuestionGeneratorsQueue questionGeneratorsQueue = new QuestionGeneratorsQueue();

    public abstract QuestionGeneratorsQueue handle(Answer answer);
}
