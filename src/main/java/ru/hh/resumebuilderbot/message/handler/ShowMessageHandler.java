package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.DBService;
import ru.hh.resumebuilderbot.TelegramUser;
import ru.hh.resumebuilderbot.cv.builder.CVFormats;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;

import java.util.ArrayList;
import java.util.List;

public class ShowMessageHandler extends MessageHandler {
    public ShowMessageHandler(DBService dbService, Graph graph) {
        super(dbService, graph);
    }

    @Override
    public List<Question> handle(TelegramUser telegramUser, Answer answer) {
        List<Question> questions = new ArrayList<>(1);
        questions.add(new Question(CVFormats.PLAIN_TEXT.getBuilder(dbService).build(telegramUser)));
        return questions;
    }
}
