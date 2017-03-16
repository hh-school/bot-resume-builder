package ru.hh.resumebuilderbot;

public class ShowMessageHandler implements MessageHandler {
    @Override
    public NextQuestionGenerator handle(Answer answer) {
        return new ShowAllQuestionGenerator();
    }
}
