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

public class StartMessageHandler extends MessageHandler {
    public StartMessageHandler(DBService dbService, Graph graph) {
        super(dbService, graph);
    }

    @Override
    public List<Question> handle(TelegramUser telegramUser, Answer answer) {
        List<Question> questions = new ArrayList<>(2);
        if (dbService.contains(telegramUser)) {
            questions.add(new Question(TextsStorage.getText(TextId.ALREADY_STARTED)));
        } else {
            dbService.startNewChat(telegramUser, graph.getRootIndex());
            questions.add(new Question(TextsStorage.getText(TextId.HELLO)));
        }
        questions.add(graph.getNode(dbService.getNodeId(telegramUser)).getQuestion());
        return questions;
    }
}
