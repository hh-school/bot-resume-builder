package ru.hh.resumebuilderbot.MessageHandler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.QuestionGenerator;
import ru.hh.resumebuilderbot.QuestionGeneratorByNumber;
import ru.hh.resumebuilderbot.UserDataStorage;

public class ClearMessageHandler implements MessageHandler {
    @Override
    public QuestionGenerator handle(Answer answer) {
        UserDataStorage.clear(answer.getChatId());
        return new QuestionGeneratorByNumber(0);
    }
}
