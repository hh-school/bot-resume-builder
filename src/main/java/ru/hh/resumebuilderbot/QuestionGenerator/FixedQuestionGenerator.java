package ru.hh.resumebuilderbot.QuestionGenerator;

import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.Question;
import ru.hh.resumebuilderbot.QuestionGenerator.QuestionGenerator;

public class FixedQuestionGenerator implements QuestionGenerator {

    private final String text;

    public FixedQuestionGenerator(String text) {
        this.text = text;
    }

    @Override
    public Question generate(ChatId chatId) {
        return new Question(chatId, text);
    }
}
