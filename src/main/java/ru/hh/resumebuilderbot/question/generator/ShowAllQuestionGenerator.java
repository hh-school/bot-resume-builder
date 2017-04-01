package ru.hh.resumebuilderbot.question.generator;

import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.Question;
import ru.hh.resumebuilderbot.cv.builder.CVFormats;

public class ShowAllQuestionGenerator implements QuestionGenerator {

    private ChatId chatId;

    public ShowAllQuestionGenerator(ChatId chatId) {
        this.chatId = chatId;
    }

    @Override
    public Question generateNext() {
        return new Question(CVFormats.PLAIN_TEXT.getBuilder().build(chatId));
    }
}
