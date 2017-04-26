package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.TelegramUser;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.texts.storage.TextsStorage;
import ru.hh.resumebuilderbot.user.data.storage.UserDataStorage;

import java.util.ArrayList;
import java.util.List;

public class SkipMessageHandler extends MessageHandler {
    public SkipMessageHandler(UserDataStorage userDataStorage) {
        super(userDataStorage);
    }

    @Override
    public List<Question> handle(TelegramUser telegramUser, Answer answer) {
        List<Question> questions = new ArrayList<>(2);
        if (userDataStorage.currentNodeIsSkippable(telegramUser)) {
            userDataStorage.moveForward(telegramUser);
        } else {
            questions.add(new Question(TextsStorage.getText(TextId.CANT_SKIP)));
        }
        questions.add(userDataStorage.getCurrentQuestion(telegramUser));
        return questions;
    }
}
