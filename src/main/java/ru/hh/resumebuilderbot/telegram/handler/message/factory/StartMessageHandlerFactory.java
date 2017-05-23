package ru.hh.resumebuilderbot.telegram.handler.message.factory;

import com.google.inject.Singleton;
import ru.hh.resumebuilderbot.DBProcessor;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.telegram.handler.message.MessageHandler;
import ru.hh.resumebuilderbot.telegram.handler.message.StartMessageHandler;

@Singleton
public class StartMessageHandlerFactory implements MessageHandlerFactory {
    private final DBProcessor dbProcessor;
    private final Graph graph;

    public StartMessageHandlerFactory(DBProcessor dbProcessor, Graph graph) {
        this.dbProcessor = dbProcessor;
        this.graph = graph;
    }
    @Override
    public MessageHandler get() {
        return new StartMessageHandler(dbProcessor, graph);
    }
}
