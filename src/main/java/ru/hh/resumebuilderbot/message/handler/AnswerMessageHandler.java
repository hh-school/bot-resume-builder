package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.TelegramUser;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.question.storage.graph.node.QuestionNode;
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
    public List<Question> handle(TelegramUser telegramUser, Answer answer) {
        log.info("User {} answer {} for question {}", telegramUser.getId(), answer.getAnswerBody(),
                userDataStorage.getCurrentQuestion(telegramUser).getText());
        List<Question> questions = new ArrayList<>(2);
        QuestionNode currentQuestionNode = userDataStorage.getCurrentQuestionNode(telegramUser);
        if (currentQuestionNode.answerIsValid(answer)) {
            currentQuestionNode.registerAnswer(answer);

            userDataStorage.moveForward(telegramUser);
        } else {
            questions.add(new Question(TextsStorage.getText(TextId.INVALID_ANSWER)));
        }
        questions.add(userDataStorage.getCurrentQuestion(telegramUser));
        return questions;
    }
}
