package ru.hh.resumebuilderbot;

public class AnswerMessageHandler implements MessageHandler {
    @Override
    public NextQuestionGenerator handle(Answer answer) {
        UserDataStorage.registerAnswer(answer.getChatId(), answer.getAnswerBody());
        return new QuestionGeneratorByNumber(UserDataStorage.getCurrentState(answer.getChatId()).getCurrentQuestion());
    }
}
