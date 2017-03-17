package ru.hh.resumebuilderbot.MessageHandler;

import ru.hh.resumebuilderbot.*;
import ru.hh.resumebuilderbot.QuestionGenerator.QuestionGenerator;
import ru.hh.resumebuilderbot.QuestionGenerator.QuestionGeneratorByNumber;

public class AnswerMessageHandler implements MessageHandler {
    @Override
    public QuestionGenerator handle(Answer answer) {
        UserDataStorage.registerAnswer(answer);
        ChatId chatId = answer.getChatId();
        CurrentUserState currentUserState = UserDataStorage.getCurrentState(chatId);
        int currentQuestionNumber = currentUserState.getCurrentQuestion();
        return new QuestionGeneratorByNumber(currentQuestionNumber);
    }
}
