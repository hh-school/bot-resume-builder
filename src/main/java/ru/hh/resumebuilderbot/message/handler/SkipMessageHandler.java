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

public class SkipMessageHandler extends MessageHandler {
    public SkipMessageHandler(UserDataStorage userDataStorage, Graph graph) {
        super(userDataStorage, graph);
    }

    @Override
    public List<Question> handle(TelegramUser telegramUser, Answer answer) {
        List<Question> questions = new ArrayList<>(2);
        Integer currentNodeId = userDataStorage.getNodeId(telegramUser);
        if (graph.getNode(currentNodeId).isSkippable()) {
            currentNodeId = graph.getNextNodeIndex(currentNodeId, answer);
            userDataStorage.saveNodeId(telegramUser, currentNodeId);
        } else {
            questions.add(new Question(TextsStorage.getText(TextId.CANT_SKIP)));
        }
        questions.add(graph.getNode(currentNodeId).getQuestion());
        return questions;
    }
}
