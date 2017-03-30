package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.*;
import ru.hh.resumebuilderbot.question.generator.FirstQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.FixedQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.QuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.QuestionGeneratorByNumber;

public class AnswerMessageHandler implements MessageHandler {
    @Override
    public QuestionGenerator handle(Answer answer) {
        if (!UserDataStorage.contains(answer.getChatId()))
        {
            return new FixedQuestionGenerator(TextsStorage.getText("OOPSTryRestart"));
        }
        CurrentUserState currentUserState = UserDataStorage.registerAnswer(answer);
        int currentQuestionNumber = currentUserState.getCurrentQuestion();

        if (QuestionsStorage.finished(currentQuestionNumber))
        {
            return new FixedQuestionGenerator(TextsStorage.getText("Finished"));
        }

        return new QuestionGeneratorByNumber(currentQuestionNumber);
    }
}
