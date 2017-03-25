package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.*;
import ru.hh.resumebuilderbot.question.generator.QuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.QuestionGeneratorByNumber;

public class AnswerMessageHandler implements MessageHandler {
    @Override
    public QuestionGenerator handle(Answer answer) {
        CurrentUserState currentUserState = UserDataStorage.registerAnswer(answer);
        int currentQuestionNumber = currentUserState.getCurrentQuestion();
        return new QuestionGeneratorByNumber(currentQuestionNumber);
    }
}
