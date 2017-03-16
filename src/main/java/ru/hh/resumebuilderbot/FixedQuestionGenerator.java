package ru.hh.resumebuilderbot;

public class FixedQuestionGenerator implements NextQuestionGenerator{

    @Override
    public Question generate() {
        return new Question();
    }
}
