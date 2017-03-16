package ru.hh.resumebuilderbot;


public class UnknownMessageHandler implements MessageHandler {
    @Override
    public NextQuestionGenerator handle(Answer answer) {
        return new FixedQuestionGenerator(TextsStorage.getText("Unknown"));
    }
}
