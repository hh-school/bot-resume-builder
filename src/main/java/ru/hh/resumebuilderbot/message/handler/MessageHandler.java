package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.generator.QuestionGenerator;

public abstract class MessageHandler {
    protected QuestionGeneratorsQueue<QuestionGenerator> questionsQueue = new QuestionGeneratorsQueue<>();

    public abstract QuestionGeneratorsQueue<QuestionGenerator> handle(Answer answer);
}
