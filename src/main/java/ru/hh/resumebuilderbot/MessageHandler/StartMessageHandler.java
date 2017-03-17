package ru.hh.resumebuilderbot.MessageHandler;

import ru.hh.resumebuilderbot.*;
import ru.hh.resumebuilderbot.QuestionGenerator.FirstQuestionGenerator;
import ru.hh.resumebuilderbot.QuestionGenerator.FixedQuestionGenerator;
import ru.hh.resumebuilderbot.QuestionGenerator.QuestionGenerator;

public class StartMessageHandler implements MessageHandler {
    @Override
    public QuestionGenerator handle(Answer answer) {
        ChatId chatId = answer.getChatId();
        if (UserDataStorage.contains(chatId))
        {
            return new FixedQuestionGenerator(TextsStorage.getText("AlreadyStarted"));
        }
        else
        {
            return new FirstQuestionGenerator();
        }
    }
}
