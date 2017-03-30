package ru.hh.resumebuilderbot.question.generator;

import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.Question;
import ru.hh.resumebuilderbot.UserData;
import ru.hh.resumebuilderbot.UserDataStorage;

import java.util.List;

public class ShowAllQuestionGenerator implements QuestionGenerator {


    private String prefix = "";

    @Override
    public Question generateNext(ChatId chatId) {
        StringBuilder resultBuilder = new StringBuilder();
        List<UserData.Entry> answers = UserDataStorage.getHistory(chatId);
        for (UserData.Entry entry : answers)
        {
            resultBuilder.append("question: ");
            resultBuilder.append(entry.getQuestion());
            resultBuilder.append(System.lineSeparator());
            resultBuilder.append("answer: ");
            resultBuilder.append(entry.getAnswer());
            resultBuilder.append(System.lineSeparator());
            resultBuilder.append("-----------------");
            resultBuilder.append(System.lineSeparator());
        }
        return new Question(chatId, resultBuilder.toString());
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
