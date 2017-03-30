package ru.hh.resumebuilderbot.message.handler;

import ru.hh.resumebuilderbot.Answer;
import ru.hh.resumebuilderbot.ChatId;
import ru.hh.resumebuilderbot.TextsStorage;
import ru.hh.resumebuilderbot.UserDataStorage;
import ru.hh.resumebuilderbot.question.generator.FirstQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.FixedQuestionGenerator;
import ru.hh.resumebuilderbot.question.generator.QuestionGenerator;

public class StartMessageHandler implements MessageHandler {
    @Override
    public QuestionGenerator handle(Answer answer) {
        ChatId chatId = answer.getChatId();
        if (UserDataStorage.contains(chatId)) {
            return new FixedQuestionGenerator(TextsStorage.getText("AlreadyStarted"));
        } else {
            UserDataStorage.startNewChat(chatId);
            QuestionGenerator result = new FirstQuestionGenerator();
            result.setPrefix(TextsStorage.getText("Hello"));
            return result;
        }
    }
}
