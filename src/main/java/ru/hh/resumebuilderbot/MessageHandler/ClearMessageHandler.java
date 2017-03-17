package ru.hh.resumebuilderbot.MessageHandler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.QuestionGenerator.FirstQuestionGenerator;
import ru.hh.resumebuilderbot.QuestionGenerator.QuestionGenerator;
import ru.hh.resumebuilderbot.UserDataStorage;

public class ClearMessageHandler implements MessageHandler {
    @Override
    public QuestionGenerator handle(Answer answer) {
        UserDataStorage.clear(answer.getChatId());
        return new FirstQuestionGenerator();
    }
}
