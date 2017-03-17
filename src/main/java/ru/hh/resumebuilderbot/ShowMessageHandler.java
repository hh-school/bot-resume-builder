package ru.hh.resumebuilderbot;

public class ShowMessageHandler implements MessageHandler {
    @Override
    public QuestionGenerator handle(Answer answer) {
        return new ShowAllQuestionGenerator();
    }
}
