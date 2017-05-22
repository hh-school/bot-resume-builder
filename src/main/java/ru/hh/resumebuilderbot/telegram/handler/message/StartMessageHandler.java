package ru.hh.resumebuilderbot.telegram.handler.message;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.DBProcessor;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.texts.storage.TextsStorage;

import java.util.ArrayList;
import java.util.List;

public class StartMessageHandler extends MessageHandler {
    public StartMessageHandler(DBProcessor dbProcessor, Graph graph) {
        super(dbProcessor, graph, StartMessageHandler.class);
    }

    @Override
    public List<Question> handle(Long telegramId, Answer answer) {
        List<Question> questions = new ArrayList<>(2);
        if (dbProcessor.contains(telegramId)) {
            questions.add(new Question(TextsStorage.getText(TextId.ALREADY_STARTED)));
        } else {
            dbProcessor.startNewChat(telegramId, graph.getRootIndex());
            questions.add(new Question(TextsStorage.getText(TextId.HELLO)));
        }
        questions.add(graph.getNode(dbProcessor.getNodeId(telegramId)).getQuestion());
        return questions;
    }
}
