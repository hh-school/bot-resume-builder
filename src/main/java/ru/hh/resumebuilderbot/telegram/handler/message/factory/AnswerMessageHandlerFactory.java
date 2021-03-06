package ru.hh.resumebuilderbot.telegram.handler.message.factory;

import com.google.inject.Singleton;
import ru.hh.resumebuilderbot.DBProcessor;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.telegram.handler.message.AnswerMessageHandler;
import ru.hh.resumebuilderbot.telegram.handler.message.MessageHandler;

@Singleton
public class AnswerMessageHandlerFactory implements MessageHandlerFactory {
    private final DBProcessor dbProcessor;
    private final Graph graph;

    public AnswerMessageHandlerFactory(DBProcessor dbProcessor, Graph graph) {
        this.dbProcessor = dbProcessor;
        this.graph = graph;
    }

    @Override
    public MessageHandler get() {
        return new AnswerMessageHandler(dbProcessor, graph);
    }
}
