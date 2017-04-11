package ru.hh.resumebuilderbot.question.generator;

import ru.hh.resumebuilderbot.User;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.user.data.storage.UserDataStorage;

public class CurrentQuestionGenerator implements QuestionGenerator {

    private final User user;

    public CurrentQuestionGenerator(User user) {
        this.user = user;
    }

    @Override
    public Question generate() {
        return UserDataStorage.getCurrentQuestion(user);
    }
}
