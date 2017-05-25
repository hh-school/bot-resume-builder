package ru.hh.resumebuilderbot.telegram.handler.message.factory;

import com.google.inject.Singleton;
import ru.hh.resumebuilderbot.DBProcessor;
import ru.hh.resumebuilderbot.cv.builder.PlainTextCVBuilder;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.telegram.handler.message.MessageHandler;
import ru.hh.resumebuilderbot.telegram.handler.message.ShowMessageHandler;

@Singleton
public class ShowMessageHandlerFactory implements MessageHandlerFactory {
    private final DBProcessor dbProcessor;
    private final Graph graph;
    private final PlainTextCVBuilder plainTextCVBuilder;

    public ShowMessageHandlerFactory(DBProcessor dbProcessor, Graph graph, PlainTextCVBuilder plainTextCVBuilder) {
        this.dbProcessor = dbProcessor;
        this.graph = graph;
        this.plainTextCVBuilder = plainTextCVBuilder;
    }

    @Override
    public MessageHandler get() {
        return new ShowMessageHandler(dbProcessor, graph, plainTextCVBuilder);
    }
}
