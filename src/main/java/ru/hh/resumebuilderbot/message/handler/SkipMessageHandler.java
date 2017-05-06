package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.DBService;
import ru.hh.resumebuilderbot.TelegramUser;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.texts.storage.TextsStorage;

import java.util.ArrayList;
import java.util.List;

public class SkipMessageHandler extends MessageHandler {
    public SkipMessageHandler(DBService dbService, Graph graph) {
        super(dbService, graph);
    }

    @Override
    public List<Question> handle(TelegramUser telegramUser, Answer answer) {
        List<Question> questions = new ArrayList<>(2);
        Integer currentNodeId = dbService.getNodeId(telegramUser);
        if (graph.getNode(currentNodeId).isSkippable()) {
            currentNodeId = graph.getNextNodeIndex(currentNodeId, answer);
            dbService.saveNodeId(telegramUser, currentNodeId);
        } else {
            questions.add(new Question(TextsStorage.getText(TextId.CANT_SKIP)));
        }
        questions.add(graph.getNode(currentNodeId).getQuestion());
        return questions;
    }
}
