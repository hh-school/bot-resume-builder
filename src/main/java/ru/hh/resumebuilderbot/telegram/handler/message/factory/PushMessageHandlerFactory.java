package ru.hh.resumebuilderbot.telegram.handler.message.factory;

import com.google.inject.Singleton;
import ru.hh.resumebuilderbot.DBProcessor;
import ru.hh.resumebuilderbot.http.HHHTTPService;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.telegram.handler.message.MessageHandler;
import ru.hh.resumebuilderbot.telegram.handler.message.PushMessageHandler;

@Singleton
public class PushMessageHandlerFactory implements MessageHandlerFactory {
    private final DBProcessor dbProcessor;
    private final Graph graph;
    private final HHHTTPService hhHTTPService;

    public PushMessageHandlerFactory(DBProcessor dbProcessor, Graph graph, HHHTTPService hhHTTPService) {
        this.dbProcessor = dbProcessor;
        this.graph = graph;
        this.hhHTTPService = hhHTTPService;
    }

    @Override
    public MessageHandler get() {
        return new PushMessageHandler(dbProcessor, graph, hhHTTPService);
    }
}
