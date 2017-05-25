package ru.hh.resumebuilderbot.telegram.handler.message;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.DBProcessor;
import ru.hh.resumebuilderbot.cv.builder.PlainTextCVBuilder;
import ru.hh.resumebuilderbot.database.model.User;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;

import java.util.ArrayList;
import java.util.List;

public class ShowMessageHandler extends MessageHandler {
    private PlainTextCVBuilder plainTextCVBuilder;

    public ShowMessageHandler(DBProcessor dbProcessor, Graph graph, PlainTextCVBuilder plainTextCVBuilder) {
        super(dbProcessor, graph, ShowMessageHandler.class);
        this.plainTextCVBuilder = plainTextCVBuilder;
    }

    @Override
    public List<Question> handle(Long telegramId, Answer answer) {
        List<Question> questions = new ArrayList<>(1);
        User user = dbProcessor.getUser(telegramId);
        questions.add(new Question(plainTextCVBuilder.build(user)));
        return questions;
    }
}
