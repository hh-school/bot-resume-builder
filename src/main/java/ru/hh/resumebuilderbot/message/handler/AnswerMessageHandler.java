package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.CurrentUserState;
import ru.hh.resumebuilderbot.QuestionsStorage;
import ru.hh.resumebuilderbot.question.generator.FixedQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.QuestionGeneratorByNumber;
import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.user.data.storage.UserDataStorage;

public class AnswerMessageHandler extends MessageHandler {
    @Override
    public QuestionGeneratorsQueue handle(Answer answer) {
        if (!UserDataStorage.contains(answer.getChatId())) {
            questionsQueue.add(new FixedQuestionGenerator(TextId.OOPS_TRY_RESTART));
            return questionsQueue;
        }
        CurrentUserState currentUserState = UserDataStorage.registerAnswer(answer);
        int currentQuestionNumber = currentUserState.getCurrentQuestion();

        if (QuestionsStorage.finished(currentQuestionNumber)) {
            questionsQueue.add(new FixedQuestionGenerator(TextId.FINISHED));
            return questionsQueue;
        }

        questionsQueue.add(new QuestionGeneratorByNumber(currentQuestionNumber));
        return questionsQueue;
    }
}
