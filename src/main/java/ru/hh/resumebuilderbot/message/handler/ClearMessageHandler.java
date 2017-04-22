package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.User;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.texts.storage.TextId;
import ru.hh.resumebuilderbot.texts.storage.TextsStorage;
import ru.hh.resumebuilderbot.user.data.storage.UserDataStorage;

import java.util.ArrayList;
import java.util.List;

public class ClearMessageHandler extends MessageHandler {
    public ClearMessageHandler(UserDataStorage userDataStorage) {
        super(userDataStorage);
    }

    @Override
    public List<Question> handle(User user, Answer answer) {
        userDataStorage.clear(user);
        List<Question> questions = new ArrayList<>(2);
        questions.add(new Question(TextsStorage.getText(TextId.CLEARED)));
        questions.add(userDataStorage.getCurrentQuestion(user));
        return questions;
    }
}
