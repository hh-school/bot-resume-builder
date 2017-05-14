package ru.hh.resumebuilderbot.telegram.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.DBService;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.question.storage.graph.node.constructor.base.QuestionNode;

public abstract class Handler {
    protected static final Logger log = LoggerFactory.getLogger(Handler.class);
    protected DBService dbService;
    protected Graph graph;

    protected Handler(DBService dbService, Graph graph) {
        // TODO not all handlers require DBService
        this.dbService = dbService;
        this.graph = graph;
    }

    protected QuestionNode getCurrentNode(Long telegramId) {
        return graph.getNode(dbService.getNodeId(telegramId));
    }
}
