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
        for (UserAnswer entry : answers) {
            resultBuilder.append("вопрос: ");
            resultBuilder.append(entry.getQuestion());
            resultBuilder.append(System.lineSeparator());
            resultBuilder.append("ответ: ");
            resultBuilder.append(entry.getAnswer());
            resultBuilder.append(System.lineSeparator());
            resultBuilder.append("-----------------");
            resultBuilder.append(System.lineSeparator());
        }
        return resultBuilder.toString();
    }
}
