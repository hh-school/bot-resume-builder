package ru.hh.resumebuilderbot.question.generator;

import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.Question;
import ru.hh.resumebuilderbot.UserDataStorage;

public class ShowAllQuestionGenerator implements QuestionGenerator {
    @Override
    public Question generateNext(ChatId chatId) {
        return new Question(chatId, UserDataStorage.showAll(chatId));
    }
}
