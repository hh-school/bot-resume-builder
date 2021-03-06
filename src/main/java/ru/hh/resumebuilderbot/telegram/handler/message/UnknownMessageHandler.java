package ru.hh.resumebuilderbot.telegram.handler.message;


import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.DBProcessor;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;
import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.texts.storage.TextsStorage;

import java.util.ArrayList;
import java.util.List;

public class UnknownMessageHandler extends MessageHandler {
    public UnknownMessageHandler(DBProcessor dbProcessor, Graph graph) {
        super(dbProcessor, graph, UnknownMessageHandler.class);
    }

    @Override
    public List<Question> handle(Long telegramId, Answer answer) {
        List<Question> questions = new ArrayList<>(1);
        questions.add(new Question(TextsStorage.getText(TextId.UNKNOWN)));
        return questions;
    }
}
