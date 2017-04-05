package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.question.generator.QuestionsGenerator;

public abstract class MessageHandler {
    protected QuestionsGenerator questionsGenerator = new QuestionsGenerator();

    public abstract QuestionsGenerator handle(ChatId chatId, Answer answer);
}
