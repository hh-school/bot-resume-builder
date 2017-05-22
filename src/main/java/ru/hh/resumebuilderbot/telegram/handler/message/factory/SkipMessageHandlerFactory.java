package ru.hh.resumebuilderbot.telegram.handler.message.factory;

import com.google.inject.Singleton;
import ru.hh.resumebuilderbot.DBProcessor;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.telegram.handler.message.MessageHandler;
import ru.hh.resumebuilderbot.telegram.handler.message.SkipMessageHandler;

@Singleton
public class SkipMessageHandlerFactory implements MessageHandlerFactory {
    private final DBProcessor dbProcessor;
    private final Graph graph;

    public SkipMessageHandlerFactory(DBProcessor dbProcessor, Graph graph) {
        this.dbProcessor = dbProcessor;
        this.graph = graph;
    }
    @Override
    public MessageHandler get() {
        return new SkipMessageHandler(dbProcessor, graph);
    }
}
