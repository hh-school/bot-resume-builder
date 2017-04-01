package ru.hh.resumebuilderbot.question.generator;

import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.Question;
import ru.hh.resumebuilderbot.cv.builder.CVFormats;

public class ShowAllQuestionGenerator implements QuestionGenerator {


    @Override
    public Question generateNext(ChatId chatId) {
        return new Question(CVFormats.PLAIN_TEXT.getBuilder().build(chatId));
    }
}
