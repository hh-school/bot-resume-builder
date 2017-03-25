package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.*;
import ru.hh.resumebuilderbot.question.generator.FirstQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.FixedQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.QuestionGenerator;

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
