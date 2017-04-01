package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.*;
import ru.hh.resumebuilderbot.question.generator.FixedQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.QuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.QuestionGeneratorByNumber;

import java.util.Queue;

public class AnswerMessageHandler extends ProtoMessageHandler {
    @Override
    public Queue<QuestionGenerator> handle(Answer answer) {
        if (!UserDataStorage.contains(answer.getChatId())) {
            queue.add(new FixedQuestionGenerator(TextsStorage.TextId.OOPS_TRY_RESTART));
            return queue;
        }
        CurrentUserState currentUserState = UserDataStorage.registerAnswer(answer);
        int currentQuestionNumber = currentUserState.getCurrentQuestion();

        if (QuestionsStorage.finished(currentQuestionNumber)) {
            queue.add(new FixedQuestionGenerator(TextsStorage.TextId.FINISHED));
            return queue;
        }

        queue.add(new QuestionGeneratorByNumber(currentQuestionNumber));
        return queue;
    }
}
