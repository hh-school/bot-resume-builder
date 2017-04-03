package ru.hh.resumebuilderbot.cv.builder;

import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.user.data.storage.UserAnswer;
import ru.hh.resumebuilderbot.user.data.storage.UserDataStorage;

import java.util.List;

public class PlainTextCVBuilder implements CVBuilder {
    @Override
    public String build(ChatId chatId) {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("Ваше резюме:");
        List<UserAnswer> answers = UserDataStorage.getHistory(chatId);
        for (UserAnswer userAnswer : answers) {
            appendUserAnswer(resultBuilder, userAnswer);
        }
        return resultBuilder.toString();
    }

    private void appendUserAnswer(StringBuilder builder, UserAnswer answer) {
        String questionText = answer.getQuestion().getText();
        String answerText = answer.getAnswer();
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
