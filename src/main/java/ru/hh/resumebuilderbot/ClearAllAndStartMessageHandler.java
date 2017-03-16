package ru.hh.resumebuilderbot;

public class ClearAllAndStartMessageHandler implements MessageHandler {
    @Override
    public NextQuestionGenerator handle(Answer answer) {
        UserDataStorage.clear(answer.getChatId());
        return new QuestionGeneratorByNumber(0);
    }
}
