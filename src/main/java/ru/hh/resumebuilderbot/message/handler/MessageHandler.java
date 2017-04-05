package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.User;
import ru.hh.resumebuilderbot.question.generator.QuestionsGenerator;

public abstract class MessageHandler {
    QuestionsGenerator questionsGenerator = new QuestionsGenerator();

    public abstract QuestionsGenerator handle(User user, Answer answer);
}
