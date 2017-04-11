package ru.hh.resumebuilderbot.question.generator;

import ru.hh.resumebuilderbot.User;
import ru.hh.resumebuilderbot.cv.builder.CVFormats;
import ru.hh.resumebuilderbot.question.Question;

public class ShowAllQuestionGenerator implements QuestionGenerator {

    private User user;

    public ShowAllQuestionGenerator(User user) {
        this.user = user;
    }

    @Override
    public Question generate() {
        return new Question(CVFormats.PLAIN_TEXT.getBuilder().build(user));
    }
}
