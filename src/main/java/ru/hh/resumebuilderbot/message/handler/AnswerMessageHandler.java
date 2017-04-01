package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.CurrentUserState;
import ru.hh.resumebuilderbot.QuestionsStorage;
import ru.hh.resumebuilderbot.UserDataStorage;
import ru.hh.resumebuilderbot.question.generator.FixedQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.QuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.QuestionGeneratorByNumber;
import ru.hh.resumebuilderbot.texts.storage.TextId;

import java.util.Queue;

public class AnswerMessageHandler extends MessageHandler {
    @Override
    public Queue<QuestionGenerator> handle(Answer answer) {
        if (!UserDataStorage.contains(answer.getChatId())) {
            queue.add(new FixedQuestionGenerator(TextId.OOPS_TRY_RESTART));
            return queue;
        }
        CurrentUserState currentUserState = UserDataStorage.registerAnswer(answer);
        int currentQuestionNumber = currentUserState.getCurrentQuestion();

        if (QuestionsStorage.finished(currentQuestionNumber)) {
            queue.add(new FixedQuestionGenerator(TextId.FINISHED));
            return queue;
        }

        queue.add(new QuestionGeneratorByNumber(currentQuestionNumber));
        return queue;
    }
}
