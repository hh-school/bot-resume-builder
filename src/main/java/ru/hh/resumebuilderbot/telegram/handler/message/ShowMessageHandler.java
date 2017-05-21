package ru.hh.resumebuilderbot.telegram.handler.message;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.DBProcessor;
import ru.hh.resumebuilderbot.cv.builder.CVFormats;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.graph.Graph;

import java.util.ArrayList;
import java.util.List;

public class ShowMessageHandler extends MessageHandler {
    public ShowMessageHandler(DBProcessor dbProcessor, Graph graph) {
        super(dbProcessor, graph);
    }

    @Override
    public List<Question> handle(Long telegramId, Answer answer) {
        List<Question> questions = new ArrayList<>(1);
        questions.add(new Question(CVFormats.PLAIN_TEXT.getBuilder(dbProcessor).build(telegramId)));
        return questions;
    }
}
