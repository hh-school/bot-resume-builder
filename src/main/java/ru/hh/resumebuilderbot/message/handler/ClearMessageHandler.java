package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question.generator.FirstQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.QuestionGenerator;
import ru.hh.resumebuilderbot.UserDataStorage;

public class ClearMessageHandler implements MessageHandler {
    @Override
    public QuestionGenerator handle(Answer answer) {
        UserDataStorage.clear(answer.getChatId());
        return new FirstQuestionGenerator();
    }
}
