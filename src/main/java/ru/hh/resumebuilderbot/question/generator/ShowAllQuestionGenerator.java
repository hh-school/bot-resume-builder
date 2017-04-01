package ru.hh.resumebuilderbot.question.generator;

import ru.hh.resumebuilderbot.CVFormats;
import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.Question;

public class ShowAllQuestionGenerator implements QuestionGenerator {


    @Override
    public Question generateNext(ChatId chatId) {
        return new Question(chatId, CVFormats.PLAIN_TEXT.build(chatId));
    }
}
