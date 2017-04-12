package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.User;
import ru.hh.resumebuilderbot.question.generator.CurrentQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.FixedQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.QuestionsGenerator;
import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.user.data.storage.UserDataStorage;

public class AnswerMessageHandler extends MessageHandler {
    @Override
    public QuestionsGenerator handle(User user, Answer answer) {

        if (UserDataStorage.answerIsValid(user, answer)) {
            UserDataStorage.registerAnswer(user, answer);

            UserDataStorage.moveForward(user);
        } else {
            questionsGenerator.addGenerator(new FixedQuestionGenerator(TextId.INVALID_ANSWER));
        }
        questionsGenerator.addGenerator(new CurrentQuestionGenerator(user));
        return questionsGenerator;
    }
}
