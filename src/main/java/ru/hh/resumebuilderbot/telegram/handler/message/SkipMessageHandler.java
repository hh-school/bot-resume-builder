package ru.hh.resumebuilderbot.telegram.handler.message;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.DBProcessor;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.texts.storage.TextsStorage;

import java.util.ArrayList;
import java.util.List;

public class SkipMessageHandler extends MessageHandler {
    public SkipMessageHandler(DBProcessor dbProcessor, Graph graph) {
        super(dbProcessor, graph, SkipMessageHandler.class);
    }

    @Override
    public List<Question> handle(Long telegramId, Answer answer) {
        List<Question> questions = new ArrayList<>(2);
        Integer currentNodeId = dbProcessor.getNodeId(telegramId);
        if (graph.getNode(currentNodeId).isSkippable()) {
            currentNodeId = graph.getNextNodeIndex(currentNodeId, answer);
            dbProcessor.setNodeId(telegramId, currentNodeId);
        } else {
            questions.add(new Question(TextsStorage.getText(TextId.CANT_SKIP)));
        }
        questions.add(graph.getNode(currentNodeId).getQuestion());
        return questions;
    }
}
