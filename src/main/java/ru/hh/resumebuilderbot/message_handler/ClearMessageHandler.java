package ru.hh.resumebuilderbot.message_handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.question_generator.FirstQuestionGenerator;
import ru.hh.resumebuilderbot.question_generator.QuestionGenerator;
import ru.hh.resumebuilderbot.UserDataStorage;

public class ClearMessageHandler implements MessageHandler {
    @Override
    public QuestionGenerator handle(Answer answer) {
        UserDataStorage.clear(answer.getChatId());
        return new FirstQuestionGenerator();
    }
}
