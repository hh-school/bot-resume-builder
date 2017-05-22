package ru.hh.resumebuilderbot.telegram.handler.message;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.DBProcessor;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.telegram.handler.Handler;

import java.util.List;

public abstract class MessageHandler extends Handler {

    protected MessageHandler(DBProcessor dbProcessor, Graph graph, Class<?> logClass) {
        super(dbProcessor, graph, logClass);
    }

    public abstract List<Question> handle(Long telegramId, Answer answer);
}
