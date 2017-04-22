package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.User;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.texts.storage.TextsStorage;
import ru.hh.resumebuilderbot.user.data.storage.UserDataStorage;

import java.util.ArrayList;
import java.util.List;

public class AnswerMessageHandler extends MessageHandler {
    public AnswerMessageHandler(UserDataStorage userDataStorage) {
        super(userDataStorage);
    }

    @Override
    public List<Question> handle(User user, Answer answer) {
        log.info("User {} answer {} for question {}", user.getIndex(), answer.getAnswerBody(),
                userDataStorage.getCurrentQuestion(user).getText());
        List<Question> questions = new ArrayList<>(2);
        if (userDataStorage.answerIsValid(user, answer)) {
            userDataStorage.registerAnswer(user, answer);

            userDataStorage.moveForward(user);
        } else {
            questions.add(new Question(TextsStorage.getText(TextId.INVALID_ANSWER)));
        }
        questions.add(userDataStorage.getCurrentQuestion(user));
        return questions;
    }
}
