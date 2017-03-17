package ru.hh.resumebuilderbot;

public class FixedQuestionGenerator implements QuestionGenerator{

    private final String text;

    public FixedQuestionGenerator(String text) {
        this.text = text;
    }

    @Override
    public Question generate(ChatId chatId) {
        return new Question(chatId, text);
    }
}
