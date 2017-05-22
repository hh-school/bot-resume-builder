package ru.hh.resumebuilderbot.telegram.handler.message.factory;

import com.google.inject.Singleton;
import ru.hh.resumebuilderbot.DBProcessor;
import ru.hh.resumebuilderbot.http.HHHTTPService;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.telegram.handler.message.AnswerMessageHandler;
import ru.hh.resumebuilderbot.telegram.handler.message.MessageHandler;

@Singleton
public class AnswerMessageHandlerFactory implements MessageHandlerFactory {
    private final DBProcessor dbProcessor;
    private final Graph graph;
    private final HHHTTPService hhHTTPService;

    public AnswerMessageHandlerFactory(DBProcessor dbProcessor, Graph graph, HHHTTPService hhHTTPService) {
        this.dbProcessor = dbProcessor;
        this.graph = graph;
        this.hhHTTPService = hhHTTPService;
    }

    @Override
    public MessageHandler get() {
        return new AnswerMessageHandler(dbProcessor, graph, hhHTTPService);
    }
}
