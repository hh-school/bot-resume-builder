package ru.hh.resumebuilderbot.question.generator;

import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.Question;

public class FixedQuestionGenerator implements QuestionGenerator {

    private final String text;
    private String prefix = "";

    public FixedQuestionGenerator(String text) {
        this.text = text;
    }

    @Override
    public Question generateNext(ChatId chatId) {
        return new Question(chatId, prefix + text);
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix + System.lineSeparator();
    }
}
