package ru.hh.resumebuilderbot.telegram.handler.message;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.DBProcessor;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.texts.storage.TextsStorage;

import java.util.ArrayList;
import java.util.List;

public class ClearMessageHandler extends MessageHandler {
    public ClearMessageHandler(DBProcessor dbProcessor, Graph graph) {
        super(dbProcessor, graph);
    }

    @Override
    public List<Question> handle(Long telegramId, Answer answer) {
        dbProcessor.startNewChat(telegramId, graph.getRootIndex());
        List<Question> questions = new ArrayList<>(2);
        questions.add(new Question(TextsStorage.getText(TextId.CLEARED)));
        questions.add(graph.getNode(dbProcessor.getNodeId(telegramId)).getQuestion());
        return questions;
    }
}
