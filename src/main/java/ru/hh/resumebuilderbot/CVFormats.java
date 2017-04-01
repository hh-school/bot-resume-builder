package ru.hh.resumebuilderbot;

import java.util.List;

public enum CVFormats {
    PLAIN_TEXT {
        @Override
        public String build(ChatId chatId) {
            StringBuilder resultBuilder = new StringBuilder();
            resultBuilder.append("Ваше резюме:");
            List<UserData.Entry> answers = UserDataStorage.getHistory(chatId);
            for (UserData.Entry entry : answers) {
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
    };

    public abstract String build(ChatId chatId);
}
