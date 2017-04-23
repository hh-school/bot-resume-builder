package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.User;
import ru.hh.resumebuilderbot.cv.builder.CVFormats;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.user.data.storage.UserDataStorage;

import java.util.ArrayList;
import java.util.List;

public class ShowMessageHandler extends MessageHandler {
    public ShowMessageHandler(UserDataStorage userDataStorage) {
        super(userDataStorage);
    }

    @Override
    public List<Question> handle(User user, Answer answer) {
        List<Question> questions = new ArrayList<>(1);
        questions.add(new Question(CVFormats.PLAIN_TEXT.getBuilder(userDataStorage).build(user)));
        return questions;
    }
}
