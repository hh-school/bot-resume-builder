package ru.hh.resumebuilderbot.question.generator;

import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.cv.builder.CVFormats;
import ru.hh.resumebuilderbot.question.Question;

public class ShowAllQuestionGenerator implements QuestionGenerator {

    private ChatId chatId;

    public ShowAllQuestionGenerator(ChatId chatId) {
        this.chatId = chatId;
    }

    @Override
    public Question generate() {
        return new Question(CVFormats.PLAIN_TEXT.getBuilder().build(chatId));
    }
}
