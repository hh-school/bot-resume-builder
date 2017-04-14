package ru.hh.resumebuilderbot.message.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.User;
import ru.hh.resumebuilderbot.question.generator.QuestionsGenerator;

public abstract class MessageHandler {
    protected final static Logger log = LoggerFactory.getLogger(MessageHandler.class);

    QuestionsGenerator questionsGenerator = new QuestionsGenerator();

    public abstract QuestionsGenerator handle(User user, Answer answer);
}
