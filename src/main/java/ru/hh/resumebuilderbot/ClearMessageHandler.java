package ru.hh.resumebuilderbot;

public class ClearMessageHandler implements MessageHandler {
    @Override
    public QuestionGenerator handle(Answer answer) {
        UserDataStorage.clear(answer.getChatId());
        return new QuestionGeneratorByNumber(0);
    }
}
