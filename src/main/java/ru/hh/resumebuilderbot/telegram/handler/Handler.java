package ru.hh.resumebuilderbot.telegram.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.DBProcessor;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.base.QuestionNode;

public abstract class Handler {
    protected static final Logger log = LoggerFactory.getLogger(Handler.class);
    protected DBProcessor dbProcessor;
    protected Graph graph;

    protected Handler(DBProcessor dbProcessor, Graph graph) {
        // TODO not all handlers require DBProcessor
        this.dbProcessor = dbProcessor;
        this.graph = graph;
    }

    protected QuestionNode getCurrentNode(Long telegramId) {
        return graph.getNode(dbProcessor.getNodeId(telegramId));
    }
}
