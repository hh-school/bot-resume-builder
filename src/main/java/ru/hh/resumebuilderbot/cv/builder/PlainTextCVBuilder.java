package ru.hh.resumebuilderbot.cv.builder;

import ru.hh.resumebuilderbot.User;
import ru.hh.resumebuilderbot.user.data.storage.UserAnswer;

public class PlainTextCVBuilder implements CVBuilder {
    @Override
    public String build(User user) {

        return "not implemented yet";
    }

    private void appendUserAnswer(StringBuilder builder, UserAnswer answer) {
        String questionText = answer.getQuestion().getText();
        String answerText = (String) (answer.getAnswer().getAnswerBody());
        builder.append("вопрос: ");
        builder.append(questionText);
        builder.append(System.lineSeparator());
        builder.append("ответ: ");
        builder.append(answerText);
        builder.append(System.lineSeparator());
        builder.append("-----------------");
        builder.append(System.lineSeparator());
    }
}
