package ru.hh.resumebuilderbot.question.generator;

import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.question.Question;
import ru.hh.resumebuilderbot.user.data.storage.UserDataStorage;

public class CurrentQuestionGenerator implements QuestionGenerator {

    private final ChatId chatId;

    public CurrentQuestionGenerator(ChatId chatId) {
        this.chatId = chatId;
    }

    @Override
    public Question generate() {
        return UserDataStorage.getCurrentQuestion(chatId);
    }
}
