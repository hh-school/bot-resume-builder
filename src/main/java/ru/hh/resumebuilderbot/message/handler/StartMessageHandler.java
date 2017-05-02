package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.TelegramUser;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.texts.storage.TextsStorage;
import ru.hh.resumebuilderbot.user.data.storage.UserDataStorage;

import java.util.ArrayList;
import java.util.List;

public class StartMessageHandler extends MessageHandler {
    public StartMessageHandler(UserDataStorage userDataStorage, Graph graph) {
        super(userDataStorage, graph);
    }

    @Override
    public List<Question> handle(TelegramUser telegramUser, Answer answer) {
        List<Question> questions = new ArrayList<>(2);
        if (userDataStorage.contains(telegramUser)) {
            questions.add(new Question(TextsStorage.getText(TextId.ALREADY_STARTED)));
        } else {
            userDataStorage.startNewChat(telegramUser, graph.getRootIndex());
            questions.add(new Question(TextsStorage.getText(TextId.HELLO)));
        }
        questions.add(graph.getNode(userDataStorage.getNodeId(telegramUser)).getQuestion());
        return questions;
    }
}
