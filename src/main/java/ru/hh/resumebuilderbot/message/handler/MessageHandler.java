package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.question.generator.QuestionGeneratorsQueue;

public abstract class MessageHandler {
    protected QuestionGeneratorsQueue questionGeneratorsQueue = new QuestionGeneratorsQueue();

    public abstract QuestionGeneratorsQueue handle(ChatId chatId, Answer answer);
}
