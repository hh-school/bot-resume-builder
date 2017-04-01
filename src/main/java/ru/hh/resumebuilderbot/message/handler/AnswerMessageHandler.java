package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.CurrentUserState;
import ru.hh.resumebuilderbot.*;
import ru.hh.resumebuilderbot.question.generator.FixedQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.QuestionGeneratorByNumber;
import ru.hh.resumebuilderbot.question.generator.QuestionGeneratorsQueue;
import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.user.data.storage.UserDataStorage;
import ru.hh.resumebuilderbot.questions.storage.QuestionsStorage;

public class AnswerMessageHandler extends MessageHandler {
    @Override
    public QuestionGeneratorsQueue handle(ChatId chatId, Answer answer) {
        if (!UserDataStorage.contains(chatId)) {
            questionGeneratorsQueue.add(new FixedQuestionGenerator(TextId.OOPS_TRY_RESTART));
            return questionGeneratorsQueue;
        }
        CurrentUserState currentUserState = UserDataStorage.registerAnswer(chatId, answer);
        int currentQuestionNumber = currentUserState.getCurrentQuestion();

        if (QuestionsStorage.finished(currentQuestionNumber)) {
            questionGeneratorsQueue.add(new FixedQuestionGenerator(TextId.FINISHED));
            return questionGeneratorsQueue;
        }

        questionGeneratorsQueue.add(new QuestionGeneratorByNumber(currentQuestionNumber));
        return questionGeneratorsQueue;
    }
}
